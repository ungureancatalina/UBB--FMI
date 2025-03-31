package ro.mpp.labfx.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ro.mpp.labfx.domain.Cursa;
import ro.mpp.labfx.domain.CursaDTO;
import ro.mpp.labfx.repository.CursaRepository;
import ro.mpp.labfx.repository.ParticipantCursaRepository;

import java.util.ArrayList;
import java.util.List;

public class CursaService {

    private CursaRepository cursaRepository;
    private ParticipantCursaRepository participantCursaRepository;

    public CursaService(CursaRepository cursaRepository, ParticipantCursaRepository participantCursaRepository) {
        this.cursaRepository = cursaRepository;
        this.participantCursaRepository = participantCursaRepository;
    }

    public List<Cursa> findAll() {
      return (List<Cursa>) cursaRepository.findAll();
    }

    public List<CursaDTO> listDTOcurse (List<Cursa> curse, ParticipantCursaService participantCursaService) {
        if (curse == null || curse.isEmpty()) {
            return List.of();
        }

        List<CursaDTO> lista = new ArrayList<>();
        for (Cursa c : curse) {
            int nr_part = participantCursaService.findNrPartByCursa(c);
            CursaDTO dto = new CursaDTO(c.getNume(),c.getCapacitateMinima(),c.getCapacitateMaxima(), nr_part);
            lista.add(dto);
        }
        return lista;
    }


}
