package ro.mpp.labfx.service;

import ro.mpp.labfx.domain.Cursa;
import ro.mpp.labfx.domain.Participant;
import ro.mpp.labfx.domain.ParticipantCursa;
import ro.mpp.labfx.repository.ParticipantCursaRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParticipantCursaService {
    private ParticipantCursaRepository participantCursaRepository;

    public ParticipantCursaService(ParticipantCursaRepository participantCursaRepository) {
        this.participantCursaRepository = participantCursaRepository;
    }
    public int findNrPartByCursa(Cursa cursa){
        return participantCursaRepository.findNrPartByCursa(cursa);
    }

    public void adauga(Participant participant, Cursa cursa){
        participantCursaRepository.adaugainbaza(participant,cursa);
    }

    public void sterge(Participant participant, Cursa cursa){
        participantCursaRepository.sterge(participant.getId());
    }

    public List<ParticipantCursa> findAll(){
        return participantCursaRepository.findAll();
    }
}
