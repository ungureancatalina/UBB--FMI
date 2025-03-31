package ro.mpp.labfx.repository;

import ro.mpp.labfx.domain.Echipa;
import ro.mpp.labfx.domain.Participant;
import ro.mpp.labfx.domain.ParticipantDTO;

import java.util.List;

public interface ParticipantRepositoryInterface extends RepositoryInterface<Integer,Participant> {
    List<ParticipantDTO> findByEchipa(Echipa echipa);

    Participant findbynume(String nume);
}
