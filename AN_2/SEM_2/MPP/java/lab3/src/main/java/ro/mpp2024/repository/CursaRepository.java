package ro.mpp2024.repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ro.mpp2024.domain.Cursa;
import ro.mpp2024.utils.JdbcUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class CursaRepository implements CursaRepositoryInterface {
    private final JdbcUtils dbUtils;
    private static final Logger logger = LogManager.getLogger(CursaRepository.class);

    public CursaRepository(Properties props) {
        this.dbUtils = new JdbcUtils(props);
    }

    @Override
    public void adauga(Cursa cursa) {
        logger.traceEntry("Se adauga o cursa in baza de date.");
        String sql = "INSERT INTO cursa (nume, capacitate_minima, capacitate_maxima) VALUES (?, ?, ?)";
        try (Connection connection = dbUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, cursa.getNume());
            statement.setInt(2, cursa.getCapacitateMinima());
            statement.setInt(3, cursa.getCapacitateMaxima());
            statement.executeUpdate();
            logger.traceExit("Cursa a fost adaugata cu succes.");
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    cursa.setId(generatedKeys.getInt(1));
                }
            }
        }
        catch (SQLException e) {
            logger.error("Eroare la adaugarea cursei: " + cursa.getNume(), e);
        }
    }

    @Override
    public void sterge(Integer id) {
        logger.traceEntry("Se sterge cursa cu id " + id);
        String sql = "DELETE FROM cursa WHERE idCursa = ?";
        try (Connection connection = dbUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
            logger.traceExit("Cursa a fost stearsa cu succes.");
        } catch (SQLException e) {
            logger.error("Eroare la stergerea cursei cu id " + id + ": " + e.getMessage(), e);
        }
    }

    @Override
    public void modifica(Cursa cursa) {
        logger.traceEntry("Se modifica cursa cu id " + cursa.getId());
        String sql = "UPDATE cursa SET nume = ?, capacitate_minima = ?, capacitate_maxima = ? WHERE idCursa = ?";
        try (Connection connection = dbUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, cursa.getNume());
            statement.setInt(2, cursa.getCapacitateMinima());
            statement.setInt(3, cursa.getCapacitateMaxima());
            statement.setInt(4, cursa.getId());
            statement.executeUpdate();
            logger.traceExit("Cursa a fost modificata cu succes.");
        } catch (SQLException e) {
            logger.error("Eroare la modificarea cursei cu id " + cursa.getId() + ": " + e.getMessage(), e);
        }
    }

    @Override
    public Cursa findById(Integer id) {
        logger.traceEntry("Se cauta cursa cu id " + id);
        String sql = "SELECT * FROM cursa WHERE idCursa = ?";
        try (Connection connection = dbUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Cursa cursa = new Cursa(
                        resultSet.getInt("idCursa"),
                        resultSet.getString("nume"),
                        resultSet.getInt("capacitate_minima"),
                        resultSet.getInt("capacitate_maxima")
                );
                logger.traceExit("Cursa gasita: " + cursa.getNume());
                return cursa;
            }
        } catch (SQLException e) {
            logger.error("Eroare la gasirea cursei cu id " + id + ": " + e.getMessage(), e);
        }
        logger.traceExit("Cursa nu a fost gasita.");
        return null;
    }

    @Override
    public List<Cursa> findAll() {
        logger.traceEntry("Se preiau toate cursele din baza de date.");
        List<Cursa> cursaList = new ArrayList<>();
        String sql = "SELECT * FROM cursa";
        try (Connection connection = dbUtils.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                Cursa cursa = new Cursa(
                        resultSet.getInt("idCursa"),
                        resultSet.getString("nume"),
                        resultSet.getInt("capacitate_minima"),
                        resultSet.getInt("capacitate_maxima")
                );
                cursaList.add(cursa);
            }
            logger.traceExit("S-au preluat " + cursaList.size() + " curse.");
        } catch (SQLException e) {
            logger.error("Eroare la preluarea tuturor curselor: " + e.getMessage(), e);
        }
        return cursaList;
    }

    @Override
    public List<Cursa> findByCapacitateMinima(int capacitate) {
        logger.traceEntry("Se cauta curse cu capacitate minima de " + capacitate);
        List<Cursa> cursaList = new ArrayList<>();
        String sql = "SELECT * FROM cursa WHERE capacitate_minima >= ?";
        try (Connection connection = dbUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, capacitate);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Cursa cursa = new Cursa(
                        resultSet.getInt("idCursa"),
                        resultSet.getString("nume"),
                        resultSet.getInt("capacitate_minima"),
                        resultSet.getInt("capacitate_maxima")
                );
                cursaList.add(cursa);
            }
            logger.traceExit("S-au gasit " + cursaList.size() + " curse cu capacitate minima de " + capacitate);
        } catch (SQLException e) {
            logger.error("Eroare la cautarea curselor cu capacitate minima de " + capacitate + ": " + e.getMessage(), e);
        }
        return cursaList;
    }
}