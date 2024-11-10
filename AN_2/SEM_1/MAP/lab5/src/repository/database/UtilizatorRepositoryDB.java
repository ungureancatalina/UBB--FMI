package repository.database;

import domain.Utilizator;
import domain.validator.UtilizatorValidator;
import repository.Repository;

import java.sql.*;
import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;

public class UtilizatorRepositoryDB  implements Repository<Long, Utilizator> {

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

    @SuppressWarnings("ReassignedVariable")
    @Override
    public Optional<Utilizator> findOne(Long aLong) {
        String query = "SELECT * FROM utilizatori WHERE \"id\" = ?";
        Utilizator utilizator = null;
        try(Connection connection= DriverManager.getConnection(url, username, password);
        PreparedStatement preparedStatement = connection.prepareStatement(query);) {
            preparedStatement.setLong(1, aLong);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next())
            {
                String nume=resultSet.getString("nume");
                String prenume= resultSet.getString("prenume");
                utilizator = new Utilizator(nume,prenume);
                utilizator.setId(aLong);
            }
        }
        catch(SQLException e)
        {
            throw new RuntimeException(e);
        }
        return Optional.ofNullable(utilizator);
    }

    @Override
    public Iterable<Utilizator> findAll() {
        HashMap<Long,Utilizator> utilizatorHashMap = new HashMap<>();
        String query = "SELECT * FROM utilizatori";
        try(Connection connection= DriverManager.getConnection(url, username, password);
            PreparedStatement preparedStatement = connection.prepareStatement(query);) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next())
            {
                Long id=resultSet.getLong("id");
                String nume=resultSet.getString("nume");
                String prenume= resultSet.getString("prenume");
                Utilizator utilizator = new Utilizator(nume,prenume);
                utilizator.setId(id);

                utilizatorHashMap.put(utilizator.getId(),utilizator);
            }
        }
        catch(SQLException e)
        {
            throw new RuntimeException(e);
        }
        return utilizatorHashMap.values();
    }

    @Override
    public Optional<Utilizator> save(Utilizator entity) {
        if(entity == null)
            throw new IllegalArgumentException("Utilizatorul nu poate fi null");

        String query = "INSERT INTO utilizatori (\"id\" ,\"nume\",\"prenume\") VALUES (?, ?, ?)";

        try(Connection connection= DriverManager.getConnection(url, username, password);
            PreparedStatement preparedStatement = connection.prepareStatement(query);) {
            preparedStatement.setLong(1, entity.getId());
            preparedStatement.setString(2,entity.getLastName());
            preparedStatement.setString(3,entity.getFirstName());
            preparedStatement.executeUpdate();
        }
        catch(SQLException e)
        {
            throw new RuntimeException(e);
        }
        return Optional.of(entity);
    }

    @SuppressWarnings("ReassignedVariable")
    @Override
    public Optional<Utilizator> delete(Long aLong) {
        String query = "DELETE FROM utilizatori WHERE \"id\" = ?";

        try(Connection connection= DriverManager.getConnection(url, username, password);
            PreparedStatement preparedStatement = connection.prepareStatement(query);) {
            preparedStatement.setLong(1, aLong);
            preparedStatement.executeUpdate();
        }
        catch(SQLException e)
        {
            throw new RuntimeException(e);
        }
        Utilizator toRemove= null;
        for(Utilizator utilizator : findAll())
        {
            if(Objects.equals(utilizator.getId(),aLong))
                toRemove=utilizator;
        }
        return Optional.ofNullable(toRemove);
    }

    @Override
    public Optional<Utilizator> update(Utilizator entity) {
        return Optional.empty();
    }
}
