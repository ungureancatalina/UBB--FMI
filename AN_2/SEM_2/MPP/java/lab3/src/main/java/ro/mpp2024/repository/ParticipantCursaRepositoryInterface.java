package ro.mpp2024.repository;

import ro.mpp2024.domain.Cursa;
import ro.mpp2024.domain.Participant;
import ro.mpp2024.domain.ParticipantCursa;
import java.util.List;

public interface ParticipantCursaRepositoryInterface extends RepositoryInterface<Integer,ParticipantCursa> {
    List<ParticipantCursa> findByCursa(Cursa cursa);
    List<ParticipantCursa> findByParticipant(Participant participant);
}
