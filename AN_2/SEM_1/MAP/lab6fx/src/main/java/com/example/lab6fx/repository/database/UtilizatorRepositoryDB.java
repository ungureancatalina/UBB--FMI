package com.example.lab6fx.repository.database;

import com.example.lab6fx.domain.Utilizator;
import com.example.lab6fx.domain.validator.UtilizatorValidator;
import com.example.lab6fx.repository.Page;
import com.example.lab6fx.repository.Pageable;
import com.example.lab6fx.repository.PagingRepository;
import com.example.lab6fx.repository.Repository;

import java.sql.*;
import java.util.*;

public class UtilizatorRepositoryDB  implements PagingRepository<Long, Utilizator> {

    UtilizatorValidator utilizatorValidator;
    private String url;
    private String username;
    private String password;

    public UtilizatorRepositoryDB(UtilizatorValidator utilizatorValidator, String url, String username, String password)
    {
        this.utilizatorValidator = utilizatorValidator;
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public Optional<Utilizator> findOne(Long id) {
        try(Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = connection.prepareStatement("select * from utilizatori " +
                    "where id = ?");

        ) {
            statement.setInt(1, Math.toIntExact(id));
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
                String lastName = resultSet.getString("nume");
                String firstName = resultSet.getString("prenume");
                String password = resultSet.getString("password");
                Utilizator u = new Utilizator(firstName, lastName,password);
                u.setId(id);
                return Optional.of(u);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public Iterable<Utilizator> findAll() throws SQLException {
        Set<Utilizator> utilizatori = new HashSet<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM utilizatori");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String lastName = resultSet.getString("nume");
                String firstName = resultSet.getString("prenume");
                String password = resultSet.getString("password");
                Utilizator u = new Utilizator(firstName, lastName,password);
                u.setId(id);
                utilizatori.add(u);
            }
            return utilizatori;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Utilizator> save(Utilizator entity) {
        if (entity==null)
            throw new IllegalArgumentException("Utilizatorul nu poate fi null");

        utilizatorValidator.validate(entity);

        try{
            Connection connection = DriverManager.getConnection(url, username, password);
            Statement statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO utilizatori(id, nume, prenume, password) VALUES ('"+entity.getId()+"', '"+entity.getLastName()+"', '"+entity.getFirstName()+"'+'"+entity.getPassword()+"')");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }

    @Override
    public Optional<Utilizator> delete(Long id) {
        if (findOne(id)==null)
            throw new IllegalArgumentException("Id-ul nu exista");

        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            Statement statement = connection.createStatement();
            statement.executeUpdate("DELETE FROM utilizatori WHERE id =" + id);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }

    @Override
    public Optional<Utilizator> update(Utilizator entity) {
        return Optional.empty();
    }

    public Utilizator findById(Long id) throws SQLException {
        String query = "SELECT * FROM utilizatori WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String firstName = resultSet.getString("prenume");
                String lastName = resultSet.getString("nume");
                String password = resultSet.getString("password");

                Utilizator utilizator = new Utilizator(firstName, lastName, password);
                utilizator.setId(id);
                return utilizator;
            }
        }
        return null;
    }

    public Utilizator findByFullName(String firstName, String lastName) throws SQLException {
        String query = "SELECT * FROM utilizatori WHERE prenume = ? AND nume = ?";
        Connection connection = DriverManager.getConnection(url, username, password);
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, firstName);
            stmt.setString(2, lastName);
            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                Long id=resultSet.getLong(("id"));
                String first_name = resultSet.getString("prenume");
                String last_name = resultSet.getString("nume");
                String password = resultSet.getString("password");

                Utilizator utilizator = new Utilizator(first_name, last_name, password);
                utilizator.setId(id);
                return utilizator;
            } else {
                return null;
            }
        }
    }

    @Override
    public Page<Utilizator> findAll(Pageable pageable) throws SQLException {
        List<Utilizator> utilizatori = new ArrayList<>();
        String countQuery = "SELECT COUNT(*) AS total FROM utilizatori";
        String query = "SELECT * FROM utilizatori LIMIT ? OFFSET ?";
        int total = 0;

        try (Connection connection = DriverManager.getConnection(url, username, password);
             Statement countStatement = connection.createStatement();
             PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet countResultSet = countStatement.executeQuery(countQuery);
            if (countResultSet.next()) {
                total = countResultSet.getInt("total");
            }
            statement.setInt(1, pageable.getPageSize());
            statement.setInt(2, pageable.getPageNumber() * pageable.getPageSize());

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String lastName = resultSet.getString("nume");
                String firstName = resultSet.getString("prenume");
                String password = resultSet.getString("password");

                Utilizator u = new Utilizator(firstName, lastName, password);
                u.setId(id);
                utilizatori.add(u);
            }
        }

        return new Page<>(utilizatori, total);
        }
}
