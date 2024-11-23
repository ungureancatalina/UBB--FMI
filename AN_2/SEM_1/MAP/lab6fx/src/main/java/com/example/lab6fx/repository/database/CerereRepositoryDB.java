package com.example.lab6fx.repository.database;

import com.example.lab6fx.domain.Cerere;
import com.example.lab6fx.domain.validator.CerereValidator;
import com.example.lab6fx.repository.Repository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class CerereRepositoryDB implements Repository<Long, Cerere> {
    private CerereValidator cerereValidator;
    private String url;
    private String username;
    private String password;

    public CerereRepositoryDB(CerereValidator cerereValidator, String url, String username, String password) {
        this.cerereValidator = cerereValidator;
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public Optional<Cerere> findOne(Long id) {
        try(Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = connection.prepareStatement("select * from cereri " +
                    "where id = ?");

        ) {
            statement.setInt(1, Math.toIntExact(id));
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
                Long id1 = resultSet.getLong("id_trimitere");
                Long id2 = resultSet.getLong("id_primire");
                LocalDateTime date = resultSet.getTimestamp("data_cerere").toLocalDateTime();
                String status = resultSet.getString("status");
                Cerere c = new Cerere(id1, id2, date, status);
                c.setId(id);
                return Optional.of(c);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public Iterable<Cerere> findAll()  throws SQLException {
        Set<Cerere> cererile = new HashSet<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM cereri");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                Long id1 = resultSet.getLong("id_trimitere");
                Long id2 = resultSet.getLong("id_primire");
                LocalDateTime date = resultSet.getTimestamp("data_cerere").toLocalDateTime();
                String status = resultSet.getString("status");
                Cerere c = new Cerere(id1, id2, date, status);
                c.setId(id);
                cererile.add(c);
            }
            return cererile;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Cerere> save(Cerere entity) {
        if (entity == null)
            throw new IllegalArgumentException("Cererea nu poate fi null");

        cerereValidator.validate(entity);

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String query = "INSERT INTO cereri(id, id_trimitere, id_primire, data_cerere, status) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setLong(1, entity.getId());
                statement.setLong(2, entity.getId1());
                statement.setLong(3, entity.getId2());
                statement.setTimestamp(4, Timestamp.valueOf(entity.getData_cererii()));
                statement.setString(5, entity.getStatus());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }

    @Override
    public Optional<Cerere> delete(Long id) {
        Optional<Cerere> cerereOptional = findOne(id);
        if (!cerereOptional.isPresent())
            throw new IllegalArgumentException("Id-ul nu exista");

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String query = "DELETE FROM cereri WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setLong(1, id);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return cerereOptional;
    }


    @Override
    public Optional<Cerere> update(Cerere entity) {
        String query = "UPDATE cereri SET id_trimitere = ?, id_primire = ?, data_cerere = ?, status = ? WHERE id = ?";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setLong(1, entity.getId1());
            statement.setLong(2, entity.getId2());
            statement.setTimestamp(3, Timestamp.valueOf(entity.getData_cererii()));
            statement.setString(4, entity.getStatus());
            statement.setLong(5, entity.getId());

            int rowsUpdated = statement.executeUpdate();

            if (rowsUpdated == 0) {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.of(entity);
    }

}

