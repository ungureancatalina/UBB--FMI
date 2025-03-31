package ro.mpp.labfx;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ro.mpp.labfx.domain.*;
import ro.mpp.labfx.repository.*;

import java.util.List;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

public class RepoTest {
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

    @Test
    @DisplayName("Test Cursa Repository: Add, Modify, FindById, FindAll, Delete")
    public void testCursaRepository() {
        Logger logger = LogManager.getLogger(CursaRepository.class);
        CursaRepository cursaRepository = getCursaRepository();

        Cursa cursa = new Cursa("Cursa Test", 5, 10);
        cursaRepository.adauga(cursa);
        assertNotNull(cursa.getId());

        Cursa updatedCursa = new Cursa(cursa.getId(), "Cursa Modificata", 6, 12);
        cursaRepository.modifica(updatedCursa);

        Cursa foundCursa = cursaRepository.findById(cursa.getId());
        assertNotNull(foundCursa);
        assertEquals("Cursa Modificata", foundCursa.getNume());

        List<Cursa> curse = cursaRepository.findAll();
        assertEquals(6, curse.size());
        assertEquals("Cursa Modificata", curse.get(5).getNume());

        cursaRepository.sterge(updatedCursa.getId());
        foundCursa = cursaRepository.findById(cursa.getId());
        assertNull(foundCursa);
    }

    @Test
    @DisplayName("Test Utilizator Repository: Add, Modify, FindById, FindAll, Delete")
    public void testUtilizatorRepository() {
        Logger logger = LogManager.getLogger(UtilizatorRepository.class);
        UtilizatorRepository utilizatorRepository = getUtilizatorRepository();

        Utilizator utilizator = new Utilizator("Utilizator Test", "password123");
        utilizatorRepository.adauga(utilizator);
        assertNotNull(utilizator.getId());

        Utilizator updatedUtilizator = new Utilizator(utilizator.getId(), "Utilizator Modificat", "newpassword123");
        utilizatorRepository.modifica(updatedUtilizator);

        Utilizator foundUtilizator = utilizatorRepository.findById(updatedUtilizator.getId());
        assertNotNull(foundUtilizator);
        assertEquals("Utilizator Modificat", foundUtilizator.getNume());

        List<Utilizator> utilizatori = utilizatorRepository.findAll();
        assertEquals(2, utilizatori.size());
        assertEquals("Utilizator Modificat", utilizatori.get(1).getNume());

        utilizatorRepository.sterge(updatedUtilizator.getId());
        foundUtilizator = utilizatorRepository.findById(updatedUtilizator.getId());
        assertNull(foundUtilizator);
    }

    @Test
    @DisplayName("Test Echipa Repository: Add, Modify, FindById, FindAll, Delete")
    public void testEchipaRepository() {
        Logger logger = LogManager.getLogger(EchipaRepository.class);
        EchipaRepository echipaRepository = getEchipaRepository();

        Echipa echipa = new Echipa("Echipa Test");
        echipaRepository.adauga(echipa);
        assertNotNull(echipa.getId());

        Echipa updatedEchipa = new Echipa(echipa.getId(), "Echipa Modificata");
        echipaRepository.modifica(updatedEchipa);

        Echipa foundEchipa = echipaRepository.findById(updatedEchipa.getId());
        assertNotNull(foundEchipa);
        assertEquals("Echipa Modificata", foundEchipa.getNume());

        List<Echipa> echipe = echipaRepository.findAll();
        assertEquals(8, echipe.size());
        assertEquals("Echipa Modificata", echipe.get(7).getNume());

        echipaRepository.sterge(updatedEchipa.getId());
        foundEchipa = echipaRepository.findById(updatedEchipa.getId());
        assertNull(foundEchipa);
    }

    @Test
    @DisplayName("Test Participant Repository: Add, Modify, FindById, FindAll, Delete")
    public void testParticipantRepository() {
        Logger logger = LogManager.getLogger(ParticipantRepository.class);
        ParticipantRepository participantRepository = getParticipantRepository();
        EchipaRepository echipaRepository = getEchipaRepository();

        Echipa echipa = new Echipa("Echipa Test");
        echipaRepository.adauga(echipa);
        assertNotNull(echipa.getId());

        Participant participant = new Participant("Participant Test", "1234567890123", 2000, echipa);
        participantRepository.adauga(participant);
        assertNotNull(participant.getId());

        Participant updatedParticipant = new Participant(participant.getId(), "Participant Modificat", "9876543210987", 2500, echipa);
        participantRepository.modifica(updatedParticipant);

        Participant foundParticipant = participantRepository.findById(updatedParticipant.getId());
        assertNotNull(foundParticipant);
        assertEquals("Participant Modificat", foundParticipant.getNume());
        assertEquals(echipa.getId(), foundParticipant.getEchipa().getId());

        echipaRepository.sterge(echipa.getId());
        participantRepository.sterge(updatedParticipant.getId());
    }

    @Test
    @DisplayName("Test Utilizator Repository: FindByNume")
    public void testFindUtilizatorByNume() {
        UtilizatorRepository utilizatorRepository = getUtilizatorRepository();
        Utilizator utilizator = utilizatorRepository.findByNume("cata");
        assertNotNull(utilizator);
        assertEquals("cata", utilizator.getNume());
    }

    @Test
    @DisplayName("Test Echipa Repository: FindByNume")
    public void testFindEchipaByNume() {
        EchipaRepository echipaRepository = getEchipaRepository();
        Echipa echipa = echipaRepository.findByNume("BMW");
        assertNotNull(echipa);
        assertEquals("BMW", echipa.getNume());
    }

    @Test
    @DisplayName("Test Participant Repository: FindByEchipa")
    public void testFindParticipantByEchipa() {
        EchipaRepository echipaRepository = getEchipaRepository();
        ParticipantRepository participantRepository = getParticipantRepository();
        Echipa echipa = echipaRepository.findByNume("Honda");
        assertNotNull(echipa);
        List<ParticipantDTO> participants = participantRepository.findByEchipa(echipa);
        assertFalse(participants.isEmpty());
    }

    @Test
    @DisplayName("Test ParticipantCursa Repository: FindNrPartByCursa")
    public void testFindNrPartByCursa() {
        CursaRepository cursaRepository = getCursaRepository();
        ParticipantCursaRepository participantCursaRepository = getParticipantCursaRepository();
        Cursa cursa = cursaRepository.findById(67);
        assertNotNull(cursa);
        int nrParticipants = participantCursaRepository.findNrPartByCursa(cursa);
        assertTrue(nrParticipants >= 0);
    }
}
