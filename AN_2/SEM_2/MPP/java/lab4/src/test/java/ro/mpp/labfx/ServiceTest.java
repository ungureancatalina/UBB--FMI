package ro.mpp.labfx;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ro.mpp.labfx.domain.*;
import ro.mpp.labfx.repository.*;
import ro.mpp.labfx.service.*;

import java.util.List;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

public class ServiceTest {
    private Properties properties;

    @BeforeEach
    public void setUp() {
        properties = new Properties();
        try {
            properties.load(getClass().getClassLoader().getResourceAsStream("bd.config"));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load", e);
        }
    }

    private CursaRepository getCursaRepository() {
        return new CursaRepository(properties);
    }

    private UtilizatorRepository getUtilizatorRepository() {
        return new UtilizatorRepository(properties);
    }

    private EchipaRepository getEchipaRepository() {
        return new EchipaRepository(properties);
    }

    private ParticipantRepository getParticipantRepository() {
        return new ParticipantRepository(properties);
    }

    private ParticipantCursaRepository getParticipantCursaRepository() {
        return new ParticipantCursaRepository(properties);
    }

    private CursaService getCursaService() {
        return new CursaService(getCursaRepository(), getParticipantCursaRepository());
    }

    private UtilizatorService getUtilizatorService() {
        return new UtilizatorService(getUtilizatorRepository());
    }

    private EchipaService getEchipaService() {
        return new EchipaService(getEchipaRepository());
    }

    private ParticipantService getParticipantService() {
        return new ParticipantService(getParticipantRepository());
    }

    private ParticipantCursaService getParticipantCursaService() {
        return new ParticipantCursaService(getParticipantCursaRepository());
    }

    @Test
    @DisplayName("Test Cursa Service")
    public void testCursaService() {
        Logger logger = LogManager.getLogger(CursaService.class);
        CursaService cursaService = getCursaService();
        ParticipantCursaService participantCursaService = getParticipantCursaService();
        CursaRepository cursaRepository = getCursaRepository();
        ParticipantRepository participantRepository = getParticipantRepository();
        EchipaRepository echipaRepository = getEchipaRepository();

        Echipa echipa = echipaRepository.findById(85);
        Participant participant = participantRepository.findById(78);

        Cursa cursa = new Cursa("Cursa Test", 5, 10);
        cursaRepository.adauga(cursa);
        participantCursaService.adauga(participant, cursa);

        List<Cursa> curse = cursaService.findAll();
        assertEquals(6, curse.size());
        assertEquals("Cursa Test", curse.get(5).getNume());

        List<CursaDTO> curseDTO = cursaService.listDTOcurse(curse, participantCursaService);
        assertEquals(6, curseDTO.size());
        assertEquals("Cursa Test", curseDTO.get(5).getNumeDTO());

        cursaRepository.sterge(cursa.getId());
        participantCursaService.sterge(participant, cursa);
    }

    @Test
    @DisplayName("Test Utilizator Service")
    public void testUtilizatorService() {
        UtilizatorService utilizatorService = getUtilizatorService();

        Utilizator utilizator = utilizatorService.findbyid(34);
        assertNotNull(utilizator);

        utilizatorService.autentificare(utilizator.getNume(), utilizator.getParola());
        assertEquals("cata", utilizator.getNume());
    }

    @Test
    @DisplayName("Test Echipa Service")
    public void testEchipaService() {
        EchipaService echipaService = getEchipaService();

        Echipa echipa = new Echipa("Echipa Test");
        echipaService.adauga(echipa);

        assertEquals("Echipa Test", echipaService.findByNume("Echipa Test").getNume());

        echipaService.sterge(echipa.getId());
    }

    @Test
    @DisplayName("Test Participant Service")
    public void testParticipantService() {
        ParticipantService participantService = getParticipantService();
        EchipaRepository echipaRepository = getEchipaRepository();
        ParticipantRepository participantRepository = getParticipantRepository();

        Echipa echipa = echipaRepository.findById(85);
        assertNotNull(echipa);

        Participant participant = new Participant("Participant Test", "1234567890123", 2000, echipa);
        participantRepository.adauga(participant);
        assertNotNull(participant.getId());

        List<ParticipantDTO> participantiEchipa = participantService.getParticipantiByEchipa(echipa);
        assertFalse(participantiEchipa.isEmpty());
        assertEquals("Participant Test", participantiEchipa.get(2).getNumeDTO());

        participantRepository.sterge(participant.getId());
    }

    @Test
    @DisplayName("Test ParticipantCursa Service")
    public void testParticipantCursaService() {
        ParticipantCursaService participantCursaService = getParticipantCursaService();
        CursaRepository cursaRepository = getCursaRepository();
        ParticipantRepository participantRepository = getParticipantRepository();
        EchipaRepository echipaRepository = getEchipaRepository();

        Echipa echipa = echipaRepository.findById(85);
        Participant participant = participantRepository.findById(78);
        assertNotNull(echipa);
        assertNotNull(participant);

        Cursa cursa = new Cursa("Cursa Test", 5, 10);
        cursaRepository.adauga(cursa);

        participantCursaService.adauga(participant, cursa);
        int nrParticipanti = participantCursaService.findNrPartByCursa(cursa);
        assertEquals(1, nrParticipanti);

        participantCursaService.sterge(participant, cursa);
        cursaRepository.sterge(cursa.getId());
    }
}
