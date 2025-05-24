package ro.mpp2024.persistence.repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ro.mpp2024.model.Echipa;
import ro.mpp2024.persistence.EchipaRepositoryInterface;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class EchipaRepository implements EchipaRepositoryInterface {
    private final JdbcUtils dbUtils;
    private static final Logger logger = LogManager.getLogger(EchipaRepository.class);

    public EchipaRepository(Properties props) {
        this.dbUtils = new JdbcUtils(props);
    }

    @Override
    public void adauga(Echipa echipa) {
        logger.traceEntry("Se adauga o echipa in baza de date.");
        String sql = "INSERT INTO echipa (nume) VALUES (?)";
        try (Connection connection = dbUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, echipa.getNume());
            statement.executeUpdate();
            logger.traceExit("Echipa a fost adaugata cu succes.");
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    echipa.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            logger.error("Eroare la adaugarea echipei: " + echipa.getNume(), e);
        }
    }

    @Override
    public void sterge(Integer id) {
        logger.traceEntry("Se sterge echipa cu id " + id);
        String sql = "DELETE FROM echipa WHERE idEchipa = ?";
        try (Connection connection = dbUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
            logger.traceExit("Echipa a fost stearsa cu succes.");
        } catch (SQLException e) {
            logger.error("Eroare la stergerea echipei cu id " + id, e);
        }
    }

    @Override
    public void modifica(Echipa echipa) {
        logger.traceEntry("Se modifica echipa cu id " + echipa.getId());
        String sql = "UPDATE echipa SET nume = ? WHERE idEchipa = ?";
        try (Connection connection = dbUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, echipa.getNume());
            statement.setInt(2, echipa.getId());
            statement.executeUpdate();
            logger.traceExit("Echipa a fost modificata cu succes.");
        } catch (SQLException e) {
            logger.error("Eroare la modificarea echipei cu id " + echipa.getId(), e);
        }
    }

    @Override
    public Echipa findById(Integer id) {
        logger.traceEntry("Se cauta echipa cu id " + id);
        String sql = "SELECT * FROM echipa WHERE idEchipa = ?";
        try (Connection connection = dbUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Echipa echipa = new Echipa(
                        resultSet.getInt("idEchipa"),
                        resultSet.getString("nume")
                );
                logger.traceExit("Echipa gasita: " + echipa.getNume());
                return echipa;
            }
        } catch (SQLException e) {
            logger.error("Eroare la cautarea echipei cu id " + id, e);
        }
        logger.traceExit("Echipa nu a fost gasita.");
        return null;
    }

    @Override
    public List<Echipa> findAll() {
        logger.traceEntry("Se preiau toate echipele din baza de date.");
        List<Echipa> echipeList = new ArrayList<>();
        String sql = "SELECT * FROM echipa";
        try (Connection connection = dbUtils.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                Echipa echipa = new Echipa(
                        resultSet.getInt("idEchipa"),
                        resultSet.getString("nume")
                );
                echipeList.add(echipa);
            }
            logger.traceExit("S-au preluat " + echipeList.size() + " echipe.");
        } catch (SQLException e) {
            logger.error("Eroare la preluarea tuturor echipelor.", e);
        }
        return echipeList;
    }

    @Override
    public Echipa findByNume(String nume) {
        logger.traceEntry("Se cauta echipa cu numele " + nume);
        String sql = "SELECT * FROM echipa WHERE nume = ?";
        try (Connection connection = dbUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, nume);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Echipa echipa = new Echipa(
                        resultSet.getInt("idEchipa"),
                        resultSet.getString("nume")
                );
                logger.traceExit("Echipa gasita: " + echipa.getNume());
                return echipa;
            }
        } catch (SQLException e) {
            logger.error("Eroare la cautarea echipei cu numele " + nume, e);
        }
        logger.traceExit("Echipa nu a fost gasita.");
        return null;
    }

    @Override
    public List<Echipa> findAllOrderedByNume() {
        logger.traceEntry("Se preiau toate echipele ordonate dupa nume.");
        List<Echipa> echipeList = new ArrayList<>();
        String sql = "SELECT * FROM echipa ORDER BY nume";
        try (Connection connection = dbUtils.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                Echipa echipa = new Echipa(
                        resultSet.getInt("idEchipa"),
                        resultSet.getString("nume")
                );
                echipeList.add(echipa);
            }
            logger.traceExit("S-au preluat " + echipeList.size() + " echipe ordonate.");
        } catch (SQLException e) {
            logger.error("Eroare la preluarea echipelor ordonate.", e);
        }
        return echipeList;
    }
}
