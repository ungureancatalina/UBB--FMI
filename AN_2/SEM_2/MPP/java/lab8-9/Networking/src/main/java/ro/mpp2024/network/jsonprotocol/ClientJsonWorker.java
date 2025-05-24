package ro.mpp2024.network.jsonprotocol;

import com.google.gson.*;
import ro.mpp2024.model.*;
import ro.mpp2024.services.IObserver;
import ro.mpp2024.services.IServices;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class ClientJsonWorker implements Runnable, IObserver {
    private final IServices server;
    private final Socket connection;
    private BufferedReader reader;
    private BufferedWriter writer;
    private final Gson gson = new GsonBuilder().create();
    private volatile boolean connected;

    public ClientJsonWorker(IServices server, Socket connection) {
        this.server = server;
        this.connection = connection;
        try {
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
            connected = true;
        } catch (IOException e) {
            System.err.println("Error initializing streams: " + e.getMessage());
        }
    }

    @Override
    public void run() {
        while (connected) {
            try {
                String requestLine = reader.readLine();
                if (requestLine == null || requestLine.isEmpty()) continue;
                Request request = gson.fromJson(requestLine, Request.class);
                //System.out.println("Am primit: " + request);
                Response response = handleRequest(request);
                //System.out.println("Am trimis: " + response);
                if (response != null) {
                    sendResponse(response);
                }
            } catch (IOException e) {
                System.err.println("IOException: " + e.getMessage());
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.err.println("Interrupted: " + e.getMessage());
            }
        }
        try {
            reader.close();
            writer.close();
            connection.close();
        } catch (IOException e) {
            System.err.println("Error closing resources: " + e.getMessage());
        }
    }

    private static final Response okResponse = JsonProtocolUtils.createOkResponse();

    private void sendResponse(Response response) throws IOException {
        String responseJson = gson.toJson(response);
        synchronized (writer) {
            writer.write(responseJson);
            writer.newLine();
            writer.flush();
        }
    }

    private Response handleRequest(Request request) {
        if (request == null || request.getType() == null) {
            return JsonProtocolUtils.createErrorResponse("Invalid request");
        }
        switch (request.getType()) {
            case LOGIN:
                return handleLogin(request);
            case LOGOUT:
                return handleLogout(request);
            case GET_ALL_CURSE:
                return handleGetAllCurse();
            case LIST_DTO_CURSE:
                return handleListDtoCurse();
            case GET_ALL_PARTICIPANTI:
                return handleGetAllParticipanti();
            case CAUTA_PARTICIPANTI_ECHIPA:
                return handleCautaParticipantiEchipa(request);
            case FIND_ECHIPA_BY_NUME:
                return handleFindEchipaByNume(request);
            case ADAUGA_ECHIPA:
                return handleAdaugaEchipa(request);
            case FIND_NR_PART_BY_CURSA:
                return handleFindNrPartByCursa(request);
            case GET_ALL_PARTICIPANT_CURSA:
                return handleGetAllParticipantCursa();
            case INSCRIERE_PARTICIPANT:
                return handleInscriereParticipant(request);
            case ADAUGA_PARTICIPANT_LA_CURSA:
                return handleAdaugaParticipantLaCursa(request);
            case FIND_PARTICIPANT_BY_NUME:
                return handleFindParticipantByNume(request);
            default:
                return JsonProtocolUtils.createErrorResponse("Unknown request type");
        }
    }

    private Response handleLogin(Request request) {
        Utilizator utilizator = request.getUtilizator();
        try {
            server.login(utilizator, this);
            return okResponse;
        } catch (Exception e) {
            return JsonProtocolUtils.createErrorResponse("Login failed: " + e.getMessage());
        }
    }

    private Response handleLogout(Request request) {
        try {
            server.logout(request.getUtilizator(), this);
            connected = false;
            return okResponse;
        } catch (Exception e) {
            return JsonProtocolUtils.createErrorResponse("Logout failed: " + e.getMessage());
        }
    }

    private Response handleGetAllCurse() {
        try {
            List<Cursa> curse = server.findAllCurse();
            return JsonProtocolUtils.createGetAllCurseResponse(curse);
        } catch (Exception e) {
            return JsonProtocolUtils.createErrorResponse(e.getMessage());
        }
    }

    private Response handleListDtoCurse() {
        try {
            List<CursaDTO> dtoList = server.listDtoCurse();
            return JsonProtocolUtils.createListDtoCurseResponse(dtoList);
        } catch (Exception e) {
            return JsonProtocolUtils.createErrorResponse(e.getMessage());
        }
    }

    private Response handleGetAllParticipanti() {
        try {
            List<Participant> participanti = server.findAllParticipanti();
            return JsonProtocolUtils.createGetAllParticipantiResponse(participanti);
        } catch (Exception e) {
            return JsonProtocolUtils.createErrorResponse(e.getMessage());
        }
    }

    private Response handleCautaParticipantiEchipa(Request request) {
        String echipa = request.getNumeEchipa();
        try {
            List<Participant> participanti = server.cautaParticipantiDupaEchipa(echipa);
            return JsonProtocolUtils.createCautaParticipantiEchipaResponse(participanti);
        } catch (Exception e) {
            return JsonProtocolUtils.createErrorResponse(e.getMessage());
        }
    }

    private Response handleFindEchipaByNume(Request request) {
        try {
            Echipa echipa = server.findEchipaByNume(request.getNumeEchipa());
            return JsonProtocolUtils.createFindEchipaByNumeResponse(echipa);
        } catch (Exception e) {
            return JsonProtocolUtils.createErrorResponse(e.getMessage());
        }
    }

    private Response handleAdaugaEchipa(Request request) {
        try {
            server.adaugaEchipa(request.getEchipa());
            return okResponse;
        } catch (Exception e) {
            return JsonProtocolUtils.createErrorResponse(e.getMessage());
        }
    }

    private Response handleFindNrPartByCursa(Request request) {
        try {
            int nr = server.findNrPartByCursa(request.getCursa());
            return JsonProtocolUtils.createFindNrPartByCursaResponse(nr);
        } catch (Exception e) {
            return JsonProtocolUtils.createErrorResponse(e.getMessage());
        }
    }

    private Response handleGetAllParticipantCursa() {
        try {
            List<ParticipantCursa> list = server.findAllParticipantCursa();
            return JsonProtocolUtils.createGetAllParticipantCursaResponse(list);
        } catch (Exception e) {
            return JsonProtocolUtils.createErrorResponse(e.getMessage());
        }
    }

    private Response handleInscriereParticipant(Request request) {
        try {
            server.inscriereParticipant(request.getParticipant());
            return okResponse;
        } catch (Exception e) {
            return JsonProtocolUtils.createErrorResponse(e.getMessage());
        }
    }

    private Response handleAdaugaParticipantLaCursa(Request request) {
        try {
            server.adaugaParticipantLaCursa(request.getParticipantCursa());
            return okResponse;
        } catch (Exception e) {
            return JsonProtocolUtils.createErrorResponse(e.getMessage());
        }
    }

    private Response handleFindParticipantByNume(Request request) {
        try {
            Participant participant = server.findByNume(request.getNumeParticipant());
            if (participant == null)
                return JsonProtocolUtils.createErrorResponse("Participantul nu a fost găsit.");
            return JsonProtocolUtils.createFindParticipantByNumeResponse(participant);
        } catch (Exception e) {
            return JsonProtocolUtils.createErrorResponse(e.getMessage());
        }
    }

    @Override
    public void adauga(Participant participant, Cursa cursa) throws Exception {
        ParticipantCursa pc = new ParticipantCursa(participant, cursa);
        Response response = JsonProtocolUtils.createNewParticipantCursaResponse(pc);
        sendResponse(response);
    }
}
