package ro.mpp2024.services;

import java.util.List;
import ro.mpp2024.model.*;

public interface IServices {
    void login(Utilizator utilizator, IObserver client) throws Exception;

    void inscriereParticipant(Participant participant) throws Exception;

    List<ParticipantDTO> findAllParticipanti() throws Exception;

    List<Participant> cautaParticipantiDupaEchipa(String numeEchipa) throws Exception;

    Echipa findEchipaByNume(String nume) throws Exception;

    void adaugaEchipa(Echipa echipa) throws Exception;

    List<Cursa> findAllCurse() throws Exception;

    List<CursaDTO> listDtoCurse() throws Exception;

    int findNrPartByCursa(Cursa cursa) throws Exception;

    void adaugaParticipantLaCursa(ParticipantCursa participantCursa) throws Exception;

    List<ParticipantCursa> findAllParticipantCursa() throws Exception;

    void logout(Utilizator utilizator, IObserver client) throws Exception;

    Participant findByNume(String nume) throws Exception;
}
