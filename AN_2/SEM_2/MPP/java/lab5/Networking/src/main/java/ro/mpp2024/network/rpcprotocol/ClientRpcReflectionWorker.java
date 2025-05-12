package ro.mpp2024.network.rpcprotocol;


import ro.mpp2024.model.*;
import ro.mpp2024.services.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.List;

public class ClientRpcReflectionWorker implements Runnable, IObserver {
    private IServices server;
    private Socket connection;

    private ObjectInputStream input;
    private ObjectOutputStream output;
    private volatile boolean connected;
    public ClientRpcReflectionWorker(IServices server, Socket connection) {
        this.server = server;
        this.connection = connection;
        try{
            output=new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input=new ObjectInputStream(connection.getInputStream());
            connected=true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        while(connected){
            try {
                Object request=input.readObject();
                Response response=handleRequest((Request)request);
                if (response!=null){
                    sendResponse(response);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            input.close();
            output.close();
            connection.close();
        } catch (IOException e) {
            System.out.println("Error "+e);
        }
    }

    @Override
    public void adauga(Participant participant, Cursa cursa) throws Exception {
        ParticipantCursa participantCursa = new ParticipantCursa(participant, cursa);
        Response resp = new Response.Builder()
                .type(ResponseType.NEW_PARTICIPANT)
                .data(participantCursa)
                .build();
        System.out.println("Participant adaugat: " + participantCursa);
        try {
            sendResponse(resp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Response okResponse=new Response.Builder().type(ResponseType.OK).build();

    private Response handleRequest(Request request){
        Response response=null;
        String handlerName="handle"+(request).type();
        System.out.println("HandlerName "+handlerName);
        try {
            Method method=this.getClass().getDeclaredMethod(handlerName, Request.class);
            response=(Response)method.invoke(this,request);
            System.out.println("Method "+handlerName+ " invoked");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return response;
    }

    private Response handleLOGIN(Request request){
        System.out.println("Login Request ... " + request.type());
        Utilizator utilizatorDTO = (Utilizator) request.data();
        try {
            Utilizator utilizator = server.login(utilizatorDTO.getNume(), utilizatorDTO.getParola(),this);
            return new Response.Builder().type(ResponseType.OK).data(utilizator).build();
        } catch (Exception e) {
            connected = false;
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }

    private Response handleGET_ALL_CURSE(Request request) {
        System.out.println("Get All Curse Request ...");
        try {
            List<Cursa> curse = server.findAllCurse();
            return new Response.Builder().type(ResponseType.GET_ALL_CURSE).data(curse).build();
        } catch (Exception e) {
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }

    private Response handleLIST_DTO_CURSE(Request request) {
        System.out.println("List DTO Curse Request ...");
        try {
            List<CursaDTO> dtoList = server.listDtoCurse();
            return new Response.Builder().type(ResponseType.LIST_DTO_CURSE).data(dtoList).build();
        } catch (Exception e) {
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }

    private Response handleINSCRIERE_PARTICIPANT(Request request) {
        System.out.println("Inscriere Participant Request ...");
        Participant participant = (Participant) request.data();
        try {
            server.inscriereParticipant(participant);
            return new Response.Builder().type(ResponseType.OK).build();
        } catch (Exception e) {
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }

    private Response handleGET_ALL_PARTICIPANTI(Request request) {
        try {
            List<Participant> participanti = server.findAllParticipanti();
            return new Response.Builder().type(ResponseType.GET_ALL_PARTICIPANTI).data(participanti).build();
        } catch (Exception e) {
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }

    private Response handleCAUTA_PARTICIPANTI_ECHIPA(Request request) {
        try {
            String echipa = (String) request.data();
            List<Participant> participanti = server.cautaParticipantiDupaEchipa(echipa);
            return new Response.Builder().type(ResponseType.CAUTA_PARTICIPANTI_ECHIPA).data(participanti).build();
        } catch (Exception e) {
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }


    private Response handleFIND_ECHIPA_BY_NUME(Request request) {
        System.out.println("Find Echipa By Nume Request ...");
        String nume = (String) request.data();
        try {
            Echipa echipa = server.findEchipaByNume(nume);
            return new Response.Builder().type(ResponseType.FIND_ECHIPA_BY_NUME).data(echipa).build();
        } catch (Exception e) {
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }

    private Response handleADAUGA_ECHIPA(Request request) {
        System.out.println("Adauga Echipa Request ...");
        Echipa echipa = (Echipa) request.data();
        try {
            server.adaugaEchipa(echipa);
            return new Response.Builder().type(ResponseType.OK).build();
        } catch (Exception e) {
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }

    private Response handleFIND_NR_PART_BY_CURSA(Request request) {
        System.out.println("Find Nr Participanti By Cursa Request ...");
        Cursa cursa = (Cursa) request.data();
        try {
            int nr = server.findNrPartByCursa(cursa);
            return new Response.Builder().type(ResponseType.FIND_NR_PART_BY_CURSA).data(nr).build();
        } catch (Exception e) {
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }

    private Response handleGET_ALL_PARTICIPANT_CURSA(Request request) {
        System.out.println("Get All ParticipantCursa Request ...");
        try {
            List<ParticipantCursa> list = server.findAllParticipantCursa();
            return new Response.Builder().type(ResponseType.GET_ALL_PARTICIPANT_CURSA).data(list).build();
        } catch (Exception e) {
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }

    private Response handleADAUGA_PARTICIPANT_LA_CURSA(Request request) {
        System.out.println("Adauga Participant La Cursa Request ...");
        Object[] data = (Object[]) request.data();
        Participant participant = (Participant) data[0];
        Cursa cursa = (Cursa) data[1];
        try {
            server.adaugaParticipantLaCursa(participant, cursa);
            return new Response.Builder().type(ResponseType.OK).build();
        } catch (Exception e) {
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }

    private void sendResponse(Response response) throws IOException{
        System.out.println("sending response "+response);
        synchronized (output) {
            output.writeObject(response);
            output.flush();
        }
    }

}
