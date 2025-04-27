package ro.mpp2024.services;

import java.util.List;
import ro.mpp2024.model.*;

public interface IServices {
    Utilizator login(String nume, String parola,IObserver client) throws Exception;

    void inscriereParticipant(Participant participant) throws Exception;

    List<Participant> findAllParticipanti() throws Exception;

    List<Participant> cautaParticipantiDupaEchipa(String numeEchipa) throws Exception;

    Echipa findEchipaByNume(String nume) throws Exception;

    void adaugaEchipa(Echipa echipa) throws Exception;

    List<Cursa> findAllCurse() throws Exception;

    List<CursaDTO> listDtoCurse() throws Exception;

    int findNrPartByCursa(Cursa cursa) throws Exception;

    void adaugaParticipantLaCursa(Participant participant, Cursa cursa) throws Exception;

    List<ParticipantCursa> findAllParticipantCursa() throws Exception;
}
