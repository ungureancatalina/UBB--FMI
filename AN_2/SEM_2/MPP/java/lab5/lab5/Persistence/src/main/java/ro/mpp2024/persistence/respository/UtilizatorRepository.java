package ro.mpp2024.persistence.respository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ro.mpp2024.model.Utilizator;
import ro.mpp2024.persistence.UtilizatorRepositoryInterface;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class UtilizatorRepository implements UtilizatorRepositoryInterface {
    private final JdbcUtils dbUtils;
    private static final Logger logger = LogManager.getLogger(UtilizatorRepository.class);

    public UtilizatorRepository(Properties props) {
        this.dbUtils = new JdbcUtils(props);
    }

    @Override
    public void adauga(Utilizator utilizator) {
        logger.traceEntry("Incep adaugarea utilizatorului: " + utilizator.getNume());
        String sql = "INSERT INTO utilizator (nume, parola) VALUES (?, ?)";
        try (Connection connection = dbUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, utilizator.getNume());
            statement.setString(2, utilizator.getParola());
            statement.executeUpdate();
            logger.traceExit("Utilizatorul " + utilizator.getNume() + " a fost adaugat cu succes.");
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    utilizator.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            logger.error("Eroare la adaugarea utilizatorului: " + utilizator.getNume(), e);
        }
    }

    @Override
    public void sterge(Integer id) {
        logger.traceEntry("Incep stergerea utilizatorului cu id " + id);
        String sql = "DELETE FROM utilizator WHERE idUtilizator = ?";
        try (Connection connection = dbUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
            logger.traceExit("Utilizatorul cu id " + id + " a fost sters cu succes.");
        } catch (SQLException e) {
            logger.error("Eroare la stergerea utilizatorului cu id " + id, e);
        }
    }

    @Override
    public void modifica(Utilizator utilizator) {
        logger.traceEntry("Incep modificarea utilizatorului cu idUtilizator " + utilizator.getId());
        String sql = "UPDATE utilizator SET nume = ?, parola = ? WHERE idUtilizator = ?";
        try (Connection connection = dbUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, utilizator.getNume());
            statement.setString(2, utilizator.getParola());
            statement.setInt(3, utilizator.getId());
            statement.executeUpdate();
            logger.traceExit("Utilizatorul cu idUtilizator " + utilizator.getId() + " a fost modificat cu succes.");
        } catch (SQLException e) {
            logger.error("Eroare la modificarea utilizatorului cu id " + utilizator.getId(), e);
        }
    }

    @Override
    public Utilizator findById(Integer id) {
        logger.traceEntry("Incep cautarea utilizatorului cu id " + id);
        String sql = "SELECT * FROM utilizator WHERE idUtilizator = ?";
        try (Connection connection = dbUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Utilizator utilizator = new Utilizator(
                        resultSet.getInt("idUtilizator"),
                        resultSet.getString("nume"),
                        resultSet.getString("parola")
                );
                logger.traceExit("Utilizatorul cu id " + id + " a fost gasit.");
                return utilizator;
            }
        } catch (SQLException e) {
            logger.error("Eroare la cautarea utilizatorului cu id " + id, e);
        }
        logger.traceExit("Utilizatorul cu id " + id + " nu a fost gasit.");
        return null;
    }

    @Override
    public List<Utilizator> findAll() {
        logger.traceEntry("Incep cautarea tuturor utilizatorilor.");
        List<Utilizator> utilizatori = new ArrayList<>();
        String sql = "SELECT * FROM utilizator";
        try (Connection connection = dbUtils.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                Utilizator utilizator = new Utilizator(
                        resultSet.getInt("idUtilizator"),
                        resultSet.getString("nume"),
                        resultSet.getString("parola")
                );
                utilizatori.add(utilizator);
            }
            logger.traceExit("Cautarea tuturor utilizatorilor s-a realizat cu succes.");
        } catch (SQLException e) {
            logger.error("Eroare la preluarea tuturor utilizatorilor.", e);
        }
        return utilizatori;
    }

    @Override
    public Utilizator findByNume(String nume) {
        logger.traceEntry("Incep cautarea utilizatorului cu numele " + nume);
        String sql = "SELECT * FROM utilizator WHERE nume = ?";
        try (Connection connection = dbUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, nume.trim());
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Utilizator utilizator = new Utilizator(
                            resultSet.getInt("idUtilizator"),
                            resultSet.getString("nume"),
                            resultSet.getString("parola")
                    );
                    logger.traceExit("Utilizatorul cu numele " + nume + " a fost gasit.");
                    return utilizator;
                }
            }
        } catch (SQLException e) {
            logger.error("Eroare la cautarea utilizatorului cu numele " + nume, e);
        }
        logger.traceExit("Utilizatorul nu a fost gasit.");
        return null;
    }
}
