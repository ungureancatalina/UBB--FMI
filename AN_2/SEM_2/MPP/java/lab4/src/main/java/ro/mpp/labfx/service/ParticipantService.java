package ro.mpp.labfx.service;

import ro.mpp.labfx.domain.Echipa;
import ro.mpp.labfx.domain.Participant;
import ro.mpp.labfx.domain.ParticipantDTO;
import ro.mpp.labfx.repository.ParticipantRepository;
import java.util.List;

public class ParticipantService {
    private ParticipantRepository participantRepository;

    public ParticipantService(ParticipantRepository participantRepository) {
        this.participantRepository = participantRepository;
    }

    public List<Participant> findAll() {
        List<Participant> participanti = participantRepository.findAll();
        return participanti;
    }

    public List<ParticipantDTO> getParticipantiByEchipa(Echipa echipa) {
        List<ParticipantDTO> participanti = participantRepository.findByEchipa(echipa);
        return participanti;
    }


    public void inscriereParticipant(Participant participant) {
        participantRepository.adauga(participant);
    }

    public Participant findbyNume(Participant participant) {
        return participantRepository.findbynume(participant.getNume());
    }
}