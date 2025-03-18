package ro.mpp.labfx.repository;

import ro.mpp.labfx.domain.Echipa;
import ro.mpp.labfx.domain.Participant;
import java.util.List;

public interface ParticipantRepositoryInterface extends RepositoryInterface<Integer,Participant> {
    List<Participant> findByEchipa(Echipa echipa);
}
