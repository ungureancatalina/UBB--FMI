package ro.mpp2024.persistence;

import ro.mpp2024.model.Echipa;
import ro.mpp2024.model.Participant;
import ro.mpp2024.model.ParticipantDTO;

import java.util.List;

public interface ParticipantRepositoryInterface extends RepositoryInterface<Integer,Participant> {
    List<Participant> findByEchipa(Echipa echipa);

    Participant findByNume(String nume);

    List<ParticipantDTO> findAllDTO();
}
