package ro.mpp2024.network.jsonprotocol;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import ro.mpp2024.model.*;
import ro.mpp2024.services.IObserver;
import ro.mpp2024.services.IServices;

import java.io.*;
import java.lang.reflect.Type;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Collectors;

public class JsonServicesProxy implements IServices {
    private final String host;
    private final int port;
    private IObserver client;

    private BufferedReader input;
    private BufferedWriter output;
    private Socket connection;
    private List<ParticipantDTO> cachedParticipantiDTO;

    Gson gson = new GsonBuilder()
            .registerTypeAdapter(RequestType.class, new JsonSerializer<RequestType>() {
                @Override
                public JsonElement serialize(RequestType src, Type typeOfSrc, JsonSerializationContext context) {
                    return new JsonPrimitive(src.name());
                }
            })
            .registerTypeAdapter(ResponseType.class, new JsonSerializer<ResponseType>() {
                @Override
                public JsonElement serialize(ResponseType src, Type typeOfSrc, JsonSerializationContext context) {
                    return new JsonPrimitive(src.name());
                }
            })
            .registerTypeAdapter(LocalDateTime.class, (JsonSerializer<LocalDateTime>) (src, typeOfSrc, context) -> new JsonPrimitive(src.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)))
            .registerTypeAdapter(LocalDateTime.class, (JsonDeserializer<LocalDateTime>) (json, typeOfT, context) -> LocalDateTime.parse(json.getAsString(), DateTimeFormatter.ISO_LOCAL_DATE_TIME))
            .create();

    private final Queue<Response> qresponses = new LinkedBlockingQueue<>();
    private volatile boolean finished;
    private final Object lock = new Object();

    public JsonServicesProxy(String host, int port) {
        this.host = host;
        this.port = port;
    }

    private void initializeConnection() throws IOException {
        connection = new Socket(host, port);
        input = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        output = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
        finished = false;
        startReader();
    }

    private void sendRequest(Request request) throws IOException {
        synchronized (lock) {
            String reqJson = gson.toJson(request);
            System.out.println("Am trimis: " + gson.toJson(request));
            output.write(reqJson);
            output.newLine();
            output.flush();
        }
    }

    private Response readResponse() throws InterruptedException {
        while (qresponses.isEmpty()) {
            Thread.sleep(50);
        }
        synchronized (qresponses) {
            return qresponses.poll();
        }
    }

    private void startReader() {
        Thread tw = new Thread(() -> {
            while (!finished) {
                try {
                    String responseLine = input.readLine();
                    if (responseLine == null || responseLine.isEmpty()) continue;

                    JsonObject fullResponse = JsonParser.parseString(responseLine).getAsJsonObject();
                    String responseType = fullResponse.get("type").getAsString();

                    if (responseType.equals("LIST_DTO_CURSE")) {
                        JsonArray curseArray = fullResponse.getAsJsonArray("curseDTO");

                        List<CursaDTOData> curseData = gson.fromJson(curseArray, new TypeToken<List<CursaDTOData>>(){}.getType());

                        List<CursaDTO> curseDTO = curseData.stream()
                                .map(data -> new CursaDTO(
                                        data.getNume(),
                                        data.getCapacitateMinima(),
                                        data.getCapacitateMaxima(),
                                        data.getNrParticipanti()
                                ))
                                .collect(Collectors.toList());

                        Response dummy = new Response();
                        dummy.setType(ResponseType.LIST_DTO_CURSE);
                        dummy.setCurseDTO(curseDTO);

                        synchronized (qresponses) {
                            qresponses.add(dummy);
                        }
                    }
                    else if (responseType.equals("GET_ALL_PARTICIPANTI")) {
                        JsonArray participantiArray = fullResponse.getAsJsonArray("participanti");

                        List<ParticipantDTOData> participantiData = gson.fromJson(participantiArray, new TypeToken<List<ParticipantDTOData>>(){}.getType());

                        List<ParticipantDTO> participantiDTO = participantiData.stream()
                                .map(data -> new ParticipantDTO(
                                        data.getNume(),
                                        data.getCapacitateMoto(),
                                        data.getEchipa() != null ? data.getEchipa().getNume() : "N/A"
                                ))
                                .collect(Collectors.toList());

                        Response dummy = new Response();
                        dummy.setType(ResponseType.GET_ALL_PARTICIPANTI);
                        cachedParticipantiDTO = participantiDTO;
                        synchronized (qresponses) {
                            qresponses.add(dummy);
                        }
                    }
                    else if (responseType.equals("GET_ALL_PARTICIPANT_CURSA")) {
                        JsonArray participantCurseArray = fullResponse.getAsJsonArray("participantiCurse");
                        List<ParticipantCursa> participantCurse = new ArrayList<>();
                        for (JsonElement elem : participantCurseArray) {
                            JsonObject obj = elem.getAsJsonObject();
                            Participant participant = gson.fromJson(obj.get("participant"), Participant.class);
                            Cursa cursa = gson.fromJson(obj.get("cursa"), Cursa.class);
                            ParticipantCursa pc = new ParticipantCursa(participant, cursa);
                            participantCurse.add(pc);
                        }

                        Response dummy = new Response();
                        dummy.setType(ResponseType.GET_ALL_PARTICIPANT_CURSA);
                        dummy.setParticipantiCurse(participantCurse);

                        synchronized (qresponses) {
                            qresponses.add(dummy);
                        }
                    }
                    else if (responseType.equals("NEW_PARTICIPANT")) {
                        JsonObject participantCursaObj = fullResponse.getAsJsonObject("participantCursa");

                        JsonObject participantObj = participantCursaObj.getAsJsonObject("participant");
                        JsonObject cursaObj = participantCursaObj.getAsJsonObject("cursa");

                        if (!participantObj.isJsonNull() && !cursaObj.isJsonNull()) {
                            Participant participant = gson.fromJson(participantObj, Participant.class);
                            Cursa cursa = gson.fromJson(cursaObj, Cursa.class);

                            ParticipantCursa pc = new ParticipantCursa(participant, cursa);

                            if (client != null && pc != null) {
                                client.adauga(pc);
                            }
                        } else {
                            System.out.println("Participant sau Cursa NULL in NEW_PARTICIPANT!");
                        }
                    }
                    else {
                        Response response = gson.fromJson(responseLine, Response.class);
                        synchronized (qresponses) {
                            qresponses.add(response);
                        }
                    }
                } catch (IOException e) {
                    System.err.println("Error reading response: " + e.getMessage());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
        tw.start();
    }


    public void closeConnection() {
        finished = true;
        try {
            input.close();
            output.close();
            connection.close();
        } catch (IOException e) {
            System.err.println("Error closing connection: " + e.getMessage());
        }
        client = null;
    }

    @Override
    public void login(Utilizator utilizator, IObserver client) throws Exception {
        initializeConnection();
        this.client = client;
        sendRequest(JsonProtocolUtils.createLoginRequest(utilizator));
        Response response = readResponse();
        if (response.getType() != ResponseType.OK) {
            closeConnection();
            throw new Exception(response.getErrorMessage());
        }
    }

    @Override
    public List<ParticipantDTO> findAllParticipanti() throws Exception {
        sendRequest(JsonProtocolUtils.createGetAllParticipantiRequest());
        Response response = readResponse();
        if (response.getType() == ResponseType.ERROR) {
            throw new Exception(response.getErrorMessage());
        }
        return cachedParticipantiDTO != null ? cachedParticipantiDTO : List.of();
    }

    @Override
    public List<Participant> cautaParticipantiDupaEchipa(String numeEchipa) throws Exception {
        sendRequest(JsonProtocolUtils.createCautaParticipantiDupaEchipaRequest(numeEchipa));
        Response response = readResponse();
        if (response.getType() == ResponseType.ERROR) {
            return List.of();
        }
        return response.getParticipanti();
    }

    @Override
    public Echipa findEchipaByNume(String nume) throws Exception {
        sendRequest(JsonProtocolUtils.createFindEchipaByNumeRequest(nume));
        Response response = readResponse();
        if (response.getType() == ResponseType.ERROR) {
            throw new Exception(response.getErrorMessage());
        }
        return response.getEchipa();
    }

    @Override
    public void adaugaEchipa(Echipa echipa) throws Exception {
        sendRequest(JsonProtocolUtils.createAdaugaEchipaRequest(echipa));
        Response response = readResponse();
        if (response.getType() == ResponseType.ERROR) {
            throw new Exception(response.getErrorMessage());
        }
    }

    @Override
    public List<Cursa> findAllCurse() throws Exception {
        sendRequest(JsonProtocolUtils.createGetAllCurseRequest());
        Response response = readResponse();
        if (response.getType() == ResponseType.ERROR) {
            throw new Exception(response.getErrorMessage());
        }
        return response.getCurse();
    }
    @Override
    public List<CursaDTO> listDtoCurse() throws Exception {
        sendRequest(JsonProtocolUtils.createListDtoCurseRequest());
        Response response = readResponse();

        if (response.getType() == ResponseType.ERROR) {
            throw new Exception(response.getErrorMessage());
        }
        if (response.getCurseDTO() != null) {
            return response.getCurseDTO();
        } else {
            return List.of();
        }
    }


    @Override
    public int findNrPartByCursa(Cursa cursa) throws Exception {
        sendRequest(JsonProtocolUtils.createFindNrPartByCursaRequest(cursa));
        Response response = readResponse();
        if (response.getType() == ResponseType.ERROR) {
            throw new Exception(response.getErrorMessage());
        }
        return response.getNrparticipant();
    }

    @Override
    public void adaugaParticipantLaCursa(ParticipantCursa participantCursa) throws Exception {
        Request request = JsonProtocolUtils.createAdaugaParticipantLaCursaRequest(participantCursa);
        sendRequest(request);
        Response response = readResponse();
        if (response.getType() == ResponseType.ERROR) {
            throw new Exception(response.getErrorMessage());
        }
    }

    @Override
    public Participant findByNume(String nume) throws Exception {
        sendRequest(JsonProtocolUtils.createFindParticipantByNumeRequest(nume));
        Response response = readResponse();
        if (response.getType() == ResponseType.ERROR) {
            throw new Exception(response.getErrorMessage());
        }
        return response.getParticipant();
    }

    @Override
    public List<ParticipantCursa> findAllParticipantCursa() throws Exception {
        sendRequest(JsonProtocolUtils.createGetAllParticipantCurseRequest());
        Response response = readResponse();
        if (response.getType() == ResponseType.ERROR) {
            throw new Exception(response.getErrorMessage());
        }
        return response.getParticipantiCurse();
    }

    @Override
    public void logout(Utilizator utilizator, IObserver client) throws Exception {
        Request req = new Request();
        req.setType(RequestType.LOGOUT);
        req.setUtilizator(utilizator);
        sendRequest(req);
    }

    @Override
    public void inscriereParticipant(Participant participant) throws Exception {
        sendRequest(JsonProtocolUtils.createInscriereParticipantRequest(participant));
        Response response = readResponse();
        if (response.getType() == ResponseType.ERROR) {
            throw new Exception(response.getErrorMessage());
        }
    }
}