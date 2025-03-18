package ro.mpp2024.repository;

import ro.mpp2024.domain.Echipa;
import ro.mpp2024.domain.Participant;
import java.util.List;

public interface ParticipantRepositoryInterface extends RepositoryInterface<Integer,Participant> {
    List<Participant> findByEchipa(Echipa echipa);
}
