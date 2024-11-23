package com.example.lab6fx.repository.database;

import com.example.lab6fx.domain.Prietenie;
import com.example.lab6fx.domain.Tuple;
import com.example.lab6fx.domain.validator.PrietenieValidator;
import com.example.lab6fx.repository.Repository;

import java.sql.*;
import java.sql.Date;
import java.util.*;

public class PrietenieRepositoryDB implements Repository<Tuple<Long, Long>, Prietenie> {

    PrietenieValidator prietenieValidator;
    private String url;
    private String username;
    private String password;

    public PrietenieRepositoryDB(PrietenieValidator prietenieValidator, String url, String username, String password) {
        this.prietenieValidator = prietenieValidator;
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public Optional<Prietenie> findOne(Tuple<Long, Long> longLongTuple) {
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM prietenii WHERE iduser1 = ? AND iduser2 = ?;")) {

            statement.setLong(1, longLongTuple.getLeft());
            statement.setLong(2, longLongTuple.getRight());

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Long idUser1 = resultSet.getLong("iduser1");
                    Long idUser2 = resultSet.getLong("iduser2");
                    Timestamp date = resultSet.getTimestamp("friendsfrom");

                    Prietenie prietenie = new Prietenie();
                    prietenie.setId(new Tuple<>(idUser1, idUser2));
                    prietenie.setFriendsfrom(date.toLocalDateTime());

                    return Optional.of(prietenie);
                } else {
                    return Optional.empty();
                }
            } catch (SQLException e) {
                throw new RuntimeException("Eroare la gasirea prieteniei: " + e.getMessage(), e);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Nu se poate conecta la baza de date prietenii: " + e.getMessage(), e);
        }
    }
    @Override
    public Iterable<Prietenie> findAll() {
        Set<Prietenie> prieteni = new HashSet<>();

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM prietenii;");
             ResultSet resultSet = statement.executeQuery()
        ) {

            while (resultSet.next()) {
                Long idUser1 = resultSet.getLong("iduser1");
                Long idUser2 = resultSet.getLong("iduser2");
                Timestamp date = resultSet.getTimestamp("friendsfrom");

                Prietenie prietenie = new Prietenie();
                prietenie.setId(new Tuple<>(idUser1, idUser2));
                prietenie.setFriendsfrom(date.toLocalDateTime());
                prieteni.add(prietenie);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return prieteni;
    }

    @Override
    public Optional<Prietenie> save(Prietenie entity) {
        if (entity == null)
            throw new IllegalArgumentException("Prietenia nu poate fi null");

        prietenieValidator.validate(entity);

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("INSERT INTO prietenii(iduser1,iduser2,friendsfrom) VALUES (?,?,?)")) {

            statement.setInt(1, entity.getId().getLeft().intValue());
            statement.setInt(2, entity.getId().getRight().intValue());
            statement.setDate(3, Date.valueOf(entity.getFriendsfrom().toLocalDate()));

            statement.executeUpdate();

        }catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }

    @Override
    public Optional<Prietenie> delete(Tuple<Long, Long> longLongTuple) {
        Optional<Prietenie> prietenie = findOne(longLongTuple);

        if(prietenie.isPresent()){
            try (Connection connection = DriverManager.getConnection(url, username, password);
                 PreparedStatement statement = connection.prepareStatement("DELETE FROM prietenii WHERE iduser1 = ? AND iduser2 = ?;")) {

                statement.setLong(1, longLongTuple.getLeft());
                statement.setLong(2, longLongTuple.getRight());

                statement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            return prietenie;
        }else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Prietenie> update(Prietenie entity) {
        return Optional.empty();
    }
}
