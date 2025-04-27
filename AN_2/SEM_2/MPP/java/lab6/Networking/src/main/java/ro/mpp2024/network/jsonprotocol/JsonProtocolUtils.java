package ro.mpp2024.network.jsonprotocol;

import ro.mpp2024.model.*;
import java.util.List;

public class JsonProtocolUtils {

    public static Response createOkResponse() {
        Response response = new Response();
        response.setType(ResponseType.OK);
        return response;
    }

    public static Response createErrorResponse(String errorMessage) {
        Response response = new Response();
        response.setType(ResponseType.ERROR);
        response.setErrorMessage(errorMessage);
        return response;
    }

    public static Response createGetAllCurseResponse(List<Cursa> curse) {
        Response response = new Response();
        response.setType(ResponseType.GET_ALL_CURSE);
        response.setCurse(curse);
        return response;
    }

    public static Response createListDtoCurseResponse(List<CursaDTO> curseDtos) {
        Response response = new Response();
        response.setType(ResponseType.LIST_DTO_CURSE);
        response.setCurseDTO(curseDtos);
        return response;
    }

    public static Response createGetAllParticipantiResponse(List<ParticipantDTO> participanti) {
        Response response = new Response();
        response.setType(ResponseType.GET_ALL_PARTICIPANTI);
        response.setParticipantiDTO(participanti);
        return response;
    }

    public static Response createCautaParticipantiEchipaResponse(List<Participant> participanti) {
        Response response = new Response();
        response.setType(ResponseType.CAUTA_PARTICIPANTI_ECHIPA);
        response.setParticipanti(participanti);
        return response;
    }

    public static Response createNewParticipantCursaResponse(ParticipantCursa participantCursa) {
        Response response = new Response();
        response.setType(ResponseType.NEW_PARTICIPANT);
        response.setParticipantCursa(participantCursa);
        return response;
    }

    public static Response createFindEchipaByNumeResponse(Echipa echipa) {
        Response response = new Response();
        response.setType(ResponseType.FIND_ECHIPA_BY_NUME);
        response.setEchipa(echipa);
        return response;
    }

    public static Response createFindNrPartByCursaResponse(int numberOfParticipants) {
        Response response = new Response();
        response.setType(ResponseType.FIND_NR_PART_BY_CURSA);
        response.setNrparticipant(numberOfParticipants);
        return response;
    }

    public static Response createGetAllParticipantCursaResponse(List<ParticipantCursa> participantCurse) {
        Response response = new Response();
        response.setType(ResponseType.GET_ALL_PARTICIPANT_CURSA);
        response.setParticipantiCurse(participantCurse);
        return response;
    }

    public static Response createFindParticipantByNumeResponse(Participant participant) {
        Response response = new Response();
        response.setType(ResponseType.FIND_PARTICIPANT_BY_NUME);
        response.setParticipant(participant);
        return response;
    }

    public static Request createLoginRequest(Utilizator utilizator) {
        Request request = new Request();
        request.setType(RequestType.LOGIN);
        request.setUtilizator(utilizator);
        return request;
    }

    public static Request createInscriereParticipantRequest(Participant participant) {
        Request request = new Request();
        request.setType(RequestType.INSCRIERE_PARTICIPANT);
        request.setParticipant(participant);
        return request;
    }

    public static Request createCautaParticipantiDupaEchipaRequest(String echipaNume) {
        Request request = new Request();
        request.setType(RequestType.CAUTA_PARTICIPANTI_ECHIPA);
        request.setNumeEchipa(echipaNume);
        return request;
    }

    public static Request createFindEchipaByNumeRequest(String nume) {
        Request request = new Request();
        request.setType(RequestType.FIND_ECHIPA_BY_NUME);
        request.setNumeEchipa(nume);
        return request;
    }

    public static Request createAdaugaEchipaRequest(Echipa echipa) {
        Request request = new Request();
        request.setType(RequestType.ADAUGA_ECHIPA);
        request.setEchipa(echipa);
        return request;
    }

    public static Request createFindNrPartByCursaRequest(Cursa cursa) {
        Request request = new Request();
        request.setType(RequestType.FIND_NR_PART_BY_CURSA);
        request.setCursa(cursa);
        return request;
    }

    public static Request createAdaugaParticipantLaCursaRequest(ParticipantCursa participantCursa) {
        Request req = new Request();
        req.setType(RequestType.ADAUGA_PARTICIPANT_LA_CURSA);
        req.setParticipantCursa(participantCursa);
        return req;
    }


    public static Request createGetAllCurseRequest() {
        Request request = new Request();
        request.setType(RequestType.GET_ALL_CURSE);
        return request;
    }

    public static Request createGetAllParticipantiRequest() {
        Request request = new Request();
        request.setType(RequestType.GET_ALL_PARTICIPANTI);
        return request;
    }

    public static Request createGetAllParticipantCurseRequest() {
        Request request = new Request();
        request.setType(RequestType.GET_ALL_PARTICIPANT_CURSA);
        return request;
    }

    public static Request createListDtoCurseRequest() {
        Request request = new Request();
        request.setType(RequestType.LIST_DTO_CURSE);
        return request;
    }

    public static Request createFindParticipantByNumeRequest(String nume) {
        Request request = new Request();
        request.setType(RequestType.FIND_PARTICIPANT_BY_NUME);
        request.setNumeParticipant(nume);
        return request;
    }
}
