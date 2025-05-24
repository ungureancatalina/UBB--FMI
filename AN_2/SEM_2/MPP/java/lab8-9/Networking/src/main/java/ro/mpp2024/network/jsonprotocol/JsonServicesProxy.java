package ro.mpp2024.network.jsonprotocol;

import com.google.gson.*;
import ro.mpp2024.model.*;
import ro.mpp2024.services.IObserver;
import ro.mpp2024.services.IServices;

import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class JsonServicesProxy implements IServices {
    private final String host;
    private final int port;
    private IObserver client;

    private BufferedReader input;
    private BufferedWriter output;
    private Socket connection;
    private final Gson gson;

    private final Queue<Response> qresponses = new LinkedBlockingQueue<>();
    private volatile boolean finished;
    private final Object lock = new Object();

    public JsonServicesProxy(String host, int port) {
        this.host = host;
        this.port = port;
        this.gson = new GsonBuilder().create();
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
            //System.out.println("Am trimis: " + gson.toJson(request));
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
                    Response response = gson.fromJson(responseLine, Response.class);
                    //System.out.println("Am primit: " + response);
                    if (response != null && response.getType() == ResponseType.NEW_PARTICIPANT) {
                        handleUpdate(response);
                    } else {
                        synchronized (qresponses) {
                            qresponses.add(response);
                        }
                    }
                } catch (IOException e) {
                    System.err.println("Error reading response: " + e.getMessage());
                }
            }
        });
        tw.start();
    }

    private void handleUpdate(Response response) {
        ParticipantCursa pc = response.getParticipantCursa();
        if (pc != null && client != null) {
            try {
                client.adauga(pc.getParticipant(), pc.getCursa());
            } catch (Exception e) {
                System.err.println("Error notifying client: " + e.getMessage());
            }
        }
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
            throw new Exception(response.getErrorMessage());
        }
    }

    @Override
    public List<Participant> findAllParticipanti() throws Exception {
        sendRequest(JsonProtocolUtils.createGetAllParticipantiRequest());
        Response response = readResponse();
        if (response.getType() == ResponseType.ERROR) {
            throw new Exception(response.getErrorMessage());
        }
        return response.getParticipanti();
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
        return response.getCurseDTO();
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
        sendRequest(JsonProtocolUtils.createAdaugaParticipantLaCursaRequest(participantCursa));
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