package ro.mpp2024.persistence.respository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ro.mpp2024.model.Cursa;
import ro.mpp2024.model.Echipa;
import ro.mpp2024.model.Participant;
import ro.mpp2024.model.ParticipantCursa;
import ro.mpp2024.persistence.ParticipantCursaRepositoryInterface;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ParticipantCursaRepository implements ParticipantCursaRepositoryInterface {
    private static final Logger logger = LogManager.getLogger(ParticipantCursaRepository.class);
    private final JdbcUtils dbUtils;

    public ParticipantCursaRepository(Properties props) {
        this.dbUtils = new JdbcUtils(props);
    }

    @Override
    public List<ParticipantCursa> findByCursa(Cursa cursa) {
        logger.traceEntry("Incep cautarea participantilor pentru cursa cu id " + cursa.getId());
        List<ParticipantCursa> result = new ArrayList<>();
        String sql = "SELECT p.idParticipant, p.nume, p.CNP, p.capacitate_motor, e.idEchipa, e.nume as echipaNume, c.idCursa, c.nume as cursaNume, c.capacitate_minima, c.capacitate_maxima " +
                "FROM participant_cursa pc " +
                "JOIN participant p ON pc.idParticipant = p.idParticipant " +
                "JOIN cursa c ON pc.idCursa = c.idCursa " +
                "JOIN echipa e ON p.idEchipa = e.idEchipa " +
                "WHERE pc.idCursa = ?";

        try (Connection connection = dbUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, cursa.getId());
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int participantId = resultSet.getInt("idParticipant");
                String nume = resultSet.getString("nume");
                String CNP = resultSet.getString("CNP");
                int capacitateMotor = resultSet.getInt("capacitate_motor");
                int echipaId = resultSet.getInt("idEchipa");
                String echipaNume = resultSet.getString("echipaNume");
                int cursaId = resultSet.getInt("idCursa");
                String cursaNume = resultSet.getString("cursaNume");
                int capacitateMinima = resultSet.getInt("capacitate_minima");
                int capacitateMaxima = resultSet.getInt("capacitate_maxima");

                Echipa echipa = new Echipa(echipaId, echipaNume);
                Cursa cursaObj = new Cursa(cursaId, cursaNume, capacitateMinima, capacitateMaxima);
                Participant participant = new Participant(participantId, nume, CNP, capacitateMotor, echipa);

                result.add(new ParticipantCursa(participant, cursaObj));
            }

            logger.traceExit("Cautarea participantilor pentru cursa cu id " + cursa.getId() + " s-a incheiat cu succes.");
        } catch (SQLException e) {
            logger.error("Eroare la cautarea participantilor pentru cursa cu id " + cursa.getId(), e);
        }

        return result;
    }

    @Override
    public List<ParticipantCursa> findByParticipant(Participant participant) {
        logger.traceEntry("Incep cautarea curselor pentru participantul cu id " + participant.getId());
        List<ParticipantCursa> result = new ArrayList<>();
        String sql = "SELECT p.idParticipant, p.nume, p.CNP, p.capacitate_motor, e.idEchipa, e.nume as echipaNume, c.idCursa, c.nume as cursaNume, c.capacitate_minima, c.capacitate_maxima " +
                "FROM participant_cursa pc " +
                "JOIN participant p ON pc.idParticipant = p.idParticipant " +
                "JOIN cursa c ON pc.idCursa = c.idCursa " +
                "JOIN echipa e ON p.idEchipa = e.idEchipa " +
                "WHERE pc.idParticipant = ?";

        try (Connection connection = dbUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, participant.getId());
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int participantId = resultSet.getInt("idParticipant");
                String nume = resultSet.getString("nume");
                String CNP = resultSet.getString("CNP");
                int capacitateMotor = resultSet.getInt("capacitate_motor");
                int echipaId = resultSet.getInt("idEchipa");
                String echipaNume = resultSet.getString("echipaNume");
                int cursaId = resultSet.getInt("idCursa");
                String cursaNume = resultSet.getString("cursaNume");
                int capacitateMinima = resultSet.getInt("capacitate_minima");
                int capacitateMaxima = resultSet.getInt("capacitate_maxima");

                Echipa echipa = new Echipa(echipaId, echipaNume);
                Cursa cursaObj = new Cursa(cursaId, cursaNume, capacitateMinima, capacitateMaxima);
                result.add(new ParticipantCursa(new Participant(participantId, nume, CNP, capacitateMotor, echipa), cursaObj));
            }

            logger.traceExit("Cautarea curselor pentru participantul cu id " + participant.getId() + " s-a incheiat cu succes.");
        } catch (SQLException e) {
            logger.error("Eroare la cautarea curselor pentru participantul cu id " + participant.getId(), e);
        }

        return result;
    }


    @Override
    public void adauga(ParticipantCursa ambele) {
        logger.traceEntry("Incep adaugarea unui participant la cursa cu idCursa " + ambele.getCursa().getId());
        String sql = "INSERT INTO participant_cursa (idParticipant, idCursa) VALUES (?, ?)";

        try (Connection connection = dbUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, ambele.getParticipant().getId());
            statement.setInt(2, ambele.getCursa().getId());
            statement.executeUpdate();

            logger.traceExit("Adaugarea participantului cu idParticipant " + ambele.getParticipant().getId() + " la cursa cu idCursa " + ambele.getCursa().getId() + " s-a realizat cu succes.");
        } catch (SQLException e) {
            logger.error("Eroare la adaugarea participantului la cursa", e);
        }
    }

    @Override
    public void sterge(Integer id) {
        logger.traceEntry("Incep stergerea participantului cu id " + id);
        String sql = "DELETE FROM participant_cursa WHERE idParticipant = ?";

        try (Connection connection = dbUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            statement.executeUpdate();

            logger.traceExit("Stergerea participantului cu id " + id + " s-a realizat cu succes.");
        } catch (SQLException e) {
            logger.error("Eroare la stergerea participantului cu id " + id, e);
        }
    }

    @Override
    public void modifica(ParticipantCursa ambele) {
        logger.traceEntry("Incep modificarea participantului cu idParticipant " + ambele.getParticipant().getId() + " pentru cursa cu idCursa " + ambele.getCursa().getId());
        String sql = "UPDATE participant_cursa SET idCursa = ? WHERE idParticipant = ?";

        try (Connection connection = dbUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, ambele.getCursa().getId());
            statement.setInt(2, ambele.getParticipant().getId());
            statement.executeUpdate();

            logger.traceExit("Modificarea participantului cu idParticipant " + ambele.getParticipant().getId() + " pentru cursa cu idCursa " + ambele.getCursa().getId() + " s-a realizat cu succes.");
        } catch (SQLException e) {
            logger.error("Eroare la modificarea participantului cursa", e);
        }
    }

    @Override
    public ParticipantCursa findById(Integer id) {
        logger.traceEntry("Incep cautarea participantului cursa cu id " + id);
        return null;
    }

    @Override
    public List<ParticipantCursa> findAll() {
        logger.traceEntry("Incep cautarea tuturor participantilor cursa");
        List<ParticipantCursa> result = new ArrayList<>();
        String sql = "SELECT pc.idParticipant, p.nume, p.CNP, p.capacitate_motor, p.idEchipa, " +
                "c.idCursa, c.nume AS cursaNume, c.capacitate_minima, c.capacitate_maxima, " +
                "e.idEchipa, e.nume AS echipaNume " +
                "FROM participant_cursa pc " +
                "JOIN participant p ON pc.idParticipant = p.idParticipant " +
                "JOIN cursa c ON pc.idCursa = c.idCursa " +
                "LEFT JOIN echipa e ON p.idEchipa = e.idEchipa";

        try (Connection connection = dbUtils.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                int participantId = resultSet.getInt("idParticipant");
                String nume = resultSet.getString("nume");
                String CNP = resultSet.getString("CNP");
                int capacitateMotor = resultSet.getInt("capacitate_motor");
                int echipaId = resultSet.getInt("idEchipa");

                Echipa echipa = null;
                if (resultSet.wasNull()) {
                    logger.warn("Participant with idParticipant " + participantId + " does not have an associated echipa.");
                } else {
                    String echipaNume = resultSet.getString("echipaNume");
                    echipa = new Echipa(echipaId, echipaNume);
                }

                int cursaId = resultSet.getInt("idCursa");
                String cursaNume = resultSet.getString("cursaNume");
                int capacitateMinima = resultSet.getInt("capacitate_minima");
                int capacitateMaxima = resultSet.getInt("capacitate_maxima");
                Cursa cursaObj = new Cursa(cursaId, cursaNume, capacitateMinima, capacitateMaxima);

                Participant participant = new Participant(participantId, nume, CNP, capacitateMotor, echipa);
                result.add(new ParticipantCursa(participant, cursaObj));
            }

            logger.traceExit("Cautarea tuturor participantilor cursa s-a realizat cu succes.");
        } catch (SQLException e) {
            logger.error("Eroare la preluarea tuturor participantilor cursa", e);
        }

        return result;
    }

    @Override
    public int findNrPartByCursa(Cursa cursa) {
        logger.traceEntry("Incep cautarea participantilor pentru cursa cu id " + cursa.getId());
        int result = 0;
        String sql = "SELECT p.idParticipant, p.nume, p.CNP, p.capacitate_motor, e.idEchipa, e.nume as echipaNume, c.idCursa, c.nume as cursaNume, c.capacitate_minima, c.capacitate_maxima " +
                "FROM participant_cursa pc " +
                "JOIN participant p ON pc.idParticipant = p.idParticipant " +
                "JOIN cursa c ON pc.idCursa = c.idCursa " +
                "JOIN echipa e ON p.idEchipa = e.idEchipa " +
                "WHERE pc.idCursa = ?";

        try (Connection connection = dbUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, cursa.getId());
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int participantId = resultSet.getInt("idParticipant");
                int cursaId = resultSet.getInt("idCursa");
                String cursaNume = resultSet.getString("cursaNume");
                int capacitateMinima = resultSet.getInt("capacitate_minima");
                int capacitateMaxima = resultSet.getInt("capacitate_maxima");

                result+=1;
            }

            logger.traceExit("Cautarea participantilor pentru cursa cu id " + cursa.getId() + " s-a incheiat cu succes.");
        } catch (SQLException e) {
            logger.error("Eroare la cautarea participantilor pentru cursa cu id " + cursa.getId(), e);
        }
        return result;
    }

    @Override
    public void adaugainbaza(ParticipantCursa participantCursa) {
        logger.traceEntry("Încep adăugarea în baza de date pentru ParticipantCursa: " +
                participantCursa.getParticipant().getId() + " + " +
                participantCursa.getCursa().getId());

        String sql = "INSERT INTO participant_cursa (idParticipant, idCursa) VALUES (?, ?)";

        try (Connection connection = dbUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, participantCursa.getParticipant().getId());
            statement.setInt(2, participantCursa.getCursa().getId());
            statement.executeUpdate();

            logger.traceExit("Adăugarea s-a realizat cu succes.");
        } catch (SQLException e) {
            logger.error("Eroare la adăugarea ParticipantCursa în baza de date", e);
        }
    }

}
