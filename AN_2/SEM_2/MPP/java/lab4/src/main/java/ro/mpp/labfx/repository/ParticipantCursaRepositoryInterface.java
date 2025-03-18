package ro.mpp.labfx.repository;

import ro.mpp.labfx.domain.Cursa;
import ro.mpp.labfx.domain.Participant;
import ro.mpp.labfx.domain.ParticipantCursa;
import java.util.List;

public interface ParticipantCursaRepositoryInterface extends RepositoryInterface<Integer,ParticipantCursa> {
    List<ParticipantCursa> findByCursa(Cursa cursa);
    List<ParticipantCursa> findByParticipant(Participant participant);
}
