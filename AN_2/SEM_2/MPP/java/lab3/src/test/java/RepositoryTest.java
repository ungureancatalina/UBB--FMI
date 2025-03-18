import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ro.mpp2024.domain.*;
import ro.mpp2024.repository.*;

import java.io.FileReader;
import java.util.Properties;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RepositoryTest {

    private Properties properties;

    @BeforeEach
    public void setUp() {
        properties = new Properties();
        try {
            properties.load( getClass().getClassLoader().getResourceAsStream("bd.config"));
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
    private ParticipantCursaRepository getParticipantCursaRepository() { return new ParticipantCursaRepository(properties);}

    @Test
    @DisplayName("Test Cursa Repository: Add, Modify, FindById, FindAll, Delete")
    public void testCursaRepository() {
        Logger logger = LogManager.getLogger(CursaRepository.class);
        CursaRepository cursaRepository = getCursaRepository();

        Cursa cursa = new Cursa("Cursa Test", 5, 10);
        cursaRepository.adauga(cursa);
        assertNotNull(cursa.getId());

        Cursa updatedCursa = new Cursa(cursa.getId(),"Cursa Modificata", 6, 12);
        cursaRepository.modifica(updatedCursa);

        Cursa foundCursa = cursaRepository.findById(cursa.getId());
        assertNotNull(foundCursa);
        assertEquals("Cursa Modificata", foundCursa.getNume());

        List<Cursa> curse = cursaRepository.findAll();
        assertEquals(1, curse.size());
        assertEquals("Cursa Modificata", curse.get(0).getNume());

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

        logger.traceEntry(utilizator.getId().toString());

        Utilizator updatedUtilizator = new Utilizator(utilizator.getId(), "Utilizator Modificat", "newpassword123");
        utilizatorRepository.modifica(updatedUtilizator);

        Utilizator foundUtilizator = utilizatorRepository.findById(updatedUtilizator.getId());
        assertNotNull(foundUtilizator);
        assertEquals("Utilizator Modificat", foundUtilizator.getNume());

        List<Utilizator> utilizatori = utilizatorRepository.findAll();
        assertEquals(1, utilizatori.size());
        assertEquals("Utilizator Modificat", utilizatori.get(0).getNume());

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

        logger.traceEntry(echipa.getId().toString());

        Echipa updatedEchipa = new Echipa(echipa.getId(), "Echipa Modificata");
        echipaRepository.modifica(updatedEchipa);

        Echipa foundEchipa = echipaRepository.findById(updatedEchipa.getId());
        assertNotNull(foundEchipa);
        assertEquals("Echipa Modificata", foundEchipa.getNume());

        List<Echipa> echipe = echipaRepository.findAll();
        assertEquals(1, echipe.size());
        assertEquals("Echipa Modificata", echipe.get(0).getNume());

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

        logger.traceEntry(participant.getId().toString());

        Participant updatedParticipant = new Participant(participant.getId(), "Participant Modificat", "9876543210987", 2500, echipa);
        participantRepository.modifica(updatedParticipant);

        Participant foundParticipant = participantRepository.findById(updatedParticipant.getId());
        assertNotNull(foundParticipant);
        assertEquals("Participant Modificat", foundParticipant.getNume());
        assertEquals(echipa.getId(), foundParticipant.getEchipa().getId());

        List<Participant> participants = participantRepository.findAll();
        assertEquals(1, participants.size());
        assertEquals("Participant Modificat", participants.get(0).getNume());
        assertEquals(echipa.getId(), participants.get(0).getEchipa().getId());

        participantRepository.sterge(updatedParticipant.getId());
        foundParticipant = participantRepository.findById(updatedParticipant.getId());
        assertNull(foundParticipant);

        echipaRepository.sterge(echipa.getId());
    }

    @Test
    @DisplayName("Test ParticipantCursa Repository: Add, Modify, FindByCursa, FindAll, Delete")
    public void testParticipantCursaRepository() {
        Logger logger = LogManager.getLogger(ParticipantCursaRepository.class);
        ParticipantCursaRepository participantCursaRepository = new ParticipantCursaRepository(properties);
        ParticipantRepository participantRepository = getParticipantRepository();
        CursaRepository cursaRepository = getCursaRepository();

        Cursa cursa = new Cursa("Cursa Test", 100, 1000);
        cursaRepository.adauga(cursa);
        assertNotNull(cursa.getId());

        EchipaRepository echipaRepository = getEchipaRepository();
        Echipa echipa = new Echipa("Echipa Test");
        echipaRepository.adauga(echipa);

        Participant participant = new Participant("Participant Test", "1234567890123", 2000, echipa);
        participantRepository.adauga(participant);
        assertNotNull(participant.getId());

        ParticipantCursa participantCursa = new ParticipantCursa(participant, cursa);
        participantCursaRepository.adauga(participantCursa);

        List<ParticipantCursa> participantCurse = participantCursaRepository.findByCursa(cursa);
        assertEquals(1, participantCurse.size());
        assertEquals(participant.getId(), participantCurse.get(0).getParticipant().getId());

        assertEquals(echipa.getId(), participantCurse.get(0).getParticipant().getEchipa().getId());

        Participant participant2 = new Participant("Participant Test 2", "9876543210987", 2500, echipa);
        participantRepository.adauga(participant2);
        assertNotNull(participant2.getId());

        ParticipantCursa participantCursa2 = new ParticipantCursa(participant2, cursa);
        participantCursaRepository.adauga(participantCursa2);

        List<ParticipantCursa> updatedParticipantCurse = participantCursaRepository.findByCursa(cursa);
        assertEquals(2, updatedParticipantCurse.size());

        for (ParticipantCursa pc : updatedParticipantCurse) {
            assertEquals(echipa.getId(), pc.getParticipant().getEchipa().getId());
        }

        List<ParticipantCursa> allParticipants = participantCursaRepository.findAll();
        assertFalse(allParticipants.isEmpty());

        participantCursaRepository.sterge(participant.getId());
        participantCurse = participantCursaRepository.findByCursa(cursa);
        assertEquals(1, participantCurse.size());

        participantRepository.sterge(participant2.getId());
        cursaRepository.sterge(cursa.getId());
        echipaRepository.sterge(echipa.getId());
    }

}
