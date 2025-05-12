package ro.mpp2024.network.rpcprotocol;

import ro.mpp2024.model.*;
import ro.mpp2024.services.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ServicesRpcProxy implements IServices {
    private String host;
    private int port;

    private IObserver client;

    private ObjectInputStream input;
    private ObjectOutputStream output;
    private Socket connection;

    private BlockingQueue<Response> qresponses;
    private volatile boolean finished;
    public ServicesRpcProxy(String host, int port) {
        this.host = host;
        this.port = port;
        qresponses=new LinkedBlockingQueue<Response>();
    }

    private void initializeConnection() throws Exception {
        try {
            connection = new Socket(host, port);
            output = new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input = new ObjectInputStream(connection.getInputStream());
            finished = false;
            startReader();
        } catch (IOException e) {
            throw new Exception("Error connecting to server: " + e.getMessage());
        }
    }

    private void closeConnection() {
        finished = true;
        try {
            input.close();
            output.close();
            connection.close();
            client = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendRequest(Request request) throws Exception {
        try {
            output.writeObject(request);
            output.flush();
        } catch (IOException e) {
            throw new Exception("Error sending object: " + e);
        }
    }

    private Response readResponse() throws Exception {
        try {
            return qresponses.take();
        } catch (InterruptedException e) {
            throw new Exception("Error reading response: " + e);
        }
    }

    private void startReader() {
        Thread tw = new Thread(new ReaderThread());
        tw.start();
    }

    private boolean isUpdate(Response response) {
        return response.type() == ResponseType.NEW_PARTICIPANT;
    }

    private void handleUpdate(Response response) {
        if (response.type() == ResponseType.NEW_PARTICIPANT) {
            ParticipantCursa pc = (ParticipantCursa) response.data();
            try {
                client.adauga(pc.getParticipant(),pc.getCursa());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private class ReaderThread implements Runnable {
        public void run() {
            while (!finished) {
                try {
                    Object response = input.readObject();
                    if (isUpdate((Response) response)) {
                        handleUpdate((Response) response);
                    } else {
                        qresponses.put((Response) response);
                    }
                } catch (SocketException e) {
                    System.out.println("Socket closed, stopping reader.");
                    break;
                } catch (IOException | ClassNotFoundException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public Utilizator login(String nume, String parola,IObserver client) throws Exception {
        initializeConnection();
        this.client=client;
        Utilizator utilizator = new Utilizator(nume, parola);
        Request req = new Request.Builder().type(RequestType.LOGIN).data(utilizator).build();
        sendRequest(req);
        Response response = readResponse();
        if (response.type() == ResponseType.OK) {
            return (Utilizator) response.data();
        }
        closeConnection();
        throw new Exception(response.data().toString());
    }

    @Override
    public void inscriereParticipant(Participant participant) throws Exception {
        Request req = new Request.Builder().type(RequestType.INSCRIERE_PARTICIPANT).data(participant).build();
        sendRequest(req);
        Response response = readResponse();
        if (response.type() == ResponseType.ERROR) throw new Exception(response.data().toString());
    }

    @Override
    public List<Participant> findAllParticipanti() throws Exception {
        Request req = new Request.Builder().type(RequestType.GET_ALL_PARTICIPANTI).build();
        sendRequest(req);
        Response response = readResponse();
        if (response.type() == ResponseType.ERROR) throw new Exception(response.data().toString());
        return (List<Participant>) response.data();
    }

    @Override
    public List<Participant> cautaParticipantiDupaEchipa(String numeEchipa) throws Exception {
        Request request = new Request.Builder()
                .type(RequestType.CAUTA_PARTICIPANTI_ECHIPA)
                .data(numeEchipa)
                .build();
        sendRequest(request);
        Response response = readResponse();

        if (response.type() == ResponseType.ERROR) {
            throw new Exception((String) response.data());
        }
        return (List<Participant>) response.data();
    }

    @Override
    public Echipa findEchipaByNume(String nume) throws Exception {
        Request req = new Request.Builder().type(RequestType.FIND_ECHIPA_BY_NUME).data(nume).build();
        sendRequest(req);
        Response response = readResponse();
        if (response.type() == ResponseType.ERROR) throw new Exception(response.data().toString());
        return (Echipa) response.data();
    }

    @Override
    public void adaugaEchipa(Echipa echipa) throws Exception {
        Request req = new Request.Builder().type(RequestType.ADAUGA_ECHIPA).data(echipa).build();
        sendRequest(req);
        Response response = readResponse();
        if (response.type() == ResponseType.ERROR) throw new Exception(response.data().toString());
    }

    @Override
    public List<Cursa> findAllCurse() throws Exception {
        Request req = new Request.Builder().type(RequestType.GET_ALL_CURSE).build();
        sendRequest(req);
        Response response = readResponse();
        if (response.type() == ResponseType.ERROR) throw new Exception(response.data().toString());
        return (List<Cursa>) response.data();
    }

    @Override
    public List<CursaDTO> listDtoCurse() throws Exception {
        Request req = new Request.Builder().type(RequestType.LIST_DTO_CURSE).build();
        sendRequest(req);
        Response response = readResponse();
        if (response.type() == ResponseType.ERROR) throw new Exception(response.data().toString());
        return (List<CursaDTO>) response.data();
    }

    @Override
    public int findNrPartByCursa(Cursa cursa) throws Exception {
        Request req = new Request.Builder().type(RequestType.FIND_NR_PART_BY_CURSA).data(cursa).build();
        sendRequest(req);
        Response response = readResponse();
        if (response.type() == ResponseType.ERROR) throw new Exception(response.data().toString());
        return (int) response.data();
    }

    @Override
    public void adaugaParticipantLaCursa(Participant participant, Cursa cursa) throws Exception {
        Object[] data = new Object[]{participant, cursa};
        Request req = new Request.Builder().type(RequestType.ADAUGA_PARTICIPANT_LA_CURSA).data(data).build();
        sendRequest(req);
        Response response = readResponse();
        if (response.type() == ResponseType.ERROR) throw new Exception(response.data().toString());
    }

    @Override
    public List<ParticipantCursa> findAllParticipantCursa() throws Exception {
        Request req = new Request.Builder().type(RequestType.GET_ALL_PARTICIPANT_CURSA).build();
        sendRequest(req);
        Response response = readResponse();
        if (response.type() == ResponseType.ERROR) throw new Exception(response.data().toString());
        return (List<ParticipantCursa>) response.data();
    }
}

