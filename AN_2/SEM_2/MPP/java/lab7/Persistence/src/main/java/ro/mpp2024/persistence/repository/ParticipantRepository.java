package ro.mpp2024.persistence.repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ro.mpp2024.model.Echipa;
import ro.mpp2024.model.Participant;
import ro.mpp2024.persistence.ParticipantRepositoryInterface;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ParticipantRepository implements ParticipantRepositoryInterface {
    private final JdbcUtils _dbUtils;
    private static final Logger logger = LogManager.getLogger(ParticipantRepository.class);

    public ParticipantRepository(Properties props) {
        this._dbUtils = new JdbcUtils(props);
    }

    @Override
    public void adauga(Participant participant) {
        logger.traceEntry("Incep adaugarea participantului: " + participant.getNume());
        String sql = "INSERT INTO participant (nume, CNP, capacitate_motor, idEchipa) VALUES (?, ?, ?, ?)";
        try (Connection connection = _dbUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, participant.getNume());
            statement.setString(2, participant.getCNP());
            statement.setInt(3, participant.getCapacitateMotor());
            statement.setInt(4, participant.getEchipa().getId());
            statement.executeUpdate();
            logger.traceExit("Participantul " + participant.getNume() + " a fost adaugat cu succes.");
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    participant.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            logger.error("Eroare la adaugarea participantului: " + participant.getNume(), e);
        }
    }

    @Override
    public void sterge(Integer id) {
        logger.traceEntry("Incep stergerea participantului cu id " + id);
        String sql = "DELETE FROM participant WHERE idParticipant = ?";
        try (Connection connection = _dbUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
            logger.traceExit("Participantul cu id " + id + " a fost sters cu succes.");
        } catch (SQLException e) {
            logger.error("Eroare la stergerea participantului cu id " + id, e);
        }
    }

    @Override
    public void modifica(Participant participant) {
        logger.traceEntry("Incep modificarea participantului cu idParticipant " + participant.getId());
        String sql = "UPDATE participant SET nume = ?, CNP = ?, capacitate_motor = ?, idEchipa = ? WHERE idParticipant = ?";
        try (Connection connection = _dbUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, participant.getNume());
            statement.setString(2, participant.getCNP());
            statement.setInt(3, participant.getCapacitateMotor());
            statement.setInt(4, participant.getEchipa().getId());
            statement.setInt(5, participant.getId());
            statement.executeUpdate();
            logger.traceExit("Participantul cu idParticipant " + participant.getId() + " a fost modificat cu succes.");
        } catch (SQLException e) {
            logger.error("Eroare la modificarea participantului cu id " + participant.getId(), e);
        }
    }

    @Override
    public Participant findById(Integer id) {
        logger.traceEntry("Incep cautarea participantului cu id " + id);
        String sql = "SELECT p.idParticipant, p.nume, p.CNP, p.capacitate_motor, e.idEchipa, e.nume as echipaNume " +
                "FROM participant p " +
                "JOIN echipa e ON p.idEchipa = e.idEchipa " +
                "WHERE p.idParticipant = ?";
        try (Connection connection = _dbUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Participant participant = new Participant(
                        resultSet.getInt("idParticipant"),
                        resultSet.getString("nume"),
                        resultSet.getString("CNP"),
                        resultSet.getInt("capacitate_motor"),
                        new Echipa(resultSet.getInt("idEchipa"), resultSet.getString("echipaNume"))
                );
                logger.traceExit("Participantul cu id " + id + " a fost gasit.");
                return participant;
            }
        } catch (SQLException e) {
            logger.error("Eroare la cautarea participantului cu id " + id, e);
        }
        logger.traceExit("Participantul cu id " + id + " nu a fost gasit.");
        return null;
    }

    @Override
    public List<Participant> findAll() {
        logger.traceEntry("Incep cautarea tuturor participantilor.");
        List<Participant> participantList = new ArrayList<>();
        String sql = "SELECT p.idParticipant, p.nume, p.CNP, p.capacitate_motor, e.idEchipa, e.nume as echipaNume " +
                "FROM participant p " +
                "JOIN echipa e ON p.idEchipa = e.idEchipa";
        try (Connection connection = _dbUtils.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                Participant participant = new Participant(
                        resultSet.getInt("idParticipant"),
                        resultSet.getString("nume"),
                        resultSet.getString("CNP"),
                        resultSet.getInt("capacitate_motor"),
                        new Echipa(resultSet.getInt("idEchipa"), resultSet.getString("echipaNume"))
                );
                participantList.add(participant);
            }
            logger.traceExit("Cautarea tuturor participantilor s-a realizat cu succes.");
        } catch (SQLException e) {
            logger.error("Eroare la preluarea tuturor participantilor.", e);
        }
        return participantList;
    }

    @Override
    public List<Participant> findByEchipa(Echipa echipa) {
        logger.traceEntry("Incep cautarea participantilor din echipa cu id " + echipa.getId());
        List<Participant> result = new ArrayList<>();
        String sql = "SELECT p.idParticipant, p.nume, p.CNP, p.capacitate_motor, e.idEchipa, e.nume as echipaNume " +
                "FROM participant p " +
                "JOIN echipa e ON p.idEchipa = e.idEchipa " +
                "WHERE p.idEchipa = ?";
        try (Connection connection = _dbUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, echipa.getId());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Echipa ech = new Echipa(resultSet.getInt("idEchipa"), resultSet.getString("echipaNume"));
                Participant participant = new Participant(
                        resultSet.getInt("idParticipant"),
                        resultSet.getString("nume"),
                        resultSet.getString("CNP"),
                        resultSet.getInt("capacitate_motor"),
                        ech
                );
                result.add(participant);
            }
            logger.traceExit("Cautarea participantilor din echipa cu id " + echipa.getId() + " s-a realizat cu succes.");
        } catch (SQLException e) {
            logger.error("Eroare la cautarea participantilor din echipa cu id " + echipa.getId(), e);
        }
        return result;
    }


    @Override
    public Participant findByNume(String nume) {
        logger.traceEntry("Incep cautarea participantului cu numele " + nume);
        String sql = "SELECT p.idParticipant, p.nume, p.CNP, p.capacitate_motor, e.idEchipa, e.nume as echipaNume " +
                "FROM participant p " +
                "JOIN echipa e ON p.idEchipa = e.idEchipa " +
                "WHERE p.nume = ?";
        try (Connection connection = _dbUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, nume);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Participant participant = new Participant(
                        resultSet.getInt("idParticipant"),
                        resultSet.getString("nume"),
                        resultSet.getString("CNP"),
                        resultSet.getInt("capacitate_motor"),
                        new Echipa(resultSet.getInt("idEchipa"), resultSet.getString("echipaNume"))
                );
                logger.traceExit("Participantul cu numele " + nume + " a fost gasit.");
                return participant;
            }
        } catch (SQLException e) {
            logger.error("Eroare la cautarea participantului cu numele " + nume, e);
        }
        logger.traceExit("Participantul cu numele " + nume + " nu a fost gasit.");
        return null;
    }


}
