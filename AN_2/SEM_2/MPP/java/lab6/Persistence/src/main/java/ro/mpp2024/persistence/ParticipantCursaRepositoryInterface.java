package ro.mpp2024.persistence;

import ro.mpp2024.model.Cursa;
import ro.mpp2024.model.Participant;
import ro.mpp2024.model.ParticipantCursa;

import java.util.List;

public interface ParticipantCursaRepositoryInterface extends RepositoryInterface<Integer,ParticipantCursa> {
    List<ParticipantCursa> findByCursa(Cursa cursa);
    List<ParticipantCursa> findByParticipant(Participant participant);
    int findNrPartByCursa(Cursa cursa);
    void adaugainbaza(ParticipantCursa participantCursa);
}
