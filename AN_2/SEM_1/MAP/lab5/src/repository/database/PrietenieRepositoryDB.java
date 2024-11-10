package repository.database;

import domain.Prietenie;
import domain.validator.PrietenieValidator;
import repository.Repository;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class PrietenieRepositoryDB implements Repository<Long, Prietenie> {

    PrietenieValidator prietenieValidator;
    private String url;
    private String user;
    private String password;

    public PrietenieRepositoryDB(PrietenieValidator prietenieValidator, String url, String user, String password) {
        this.prietenieValidator = prietenieValidator;
        this.url = url;
        this.user = user;
        this.password = password;
    }

    @SuppressWarnings("ReassignedVariable")
    @Override
    public Optional<Prietenie> findOne(Long aLong) {
        String query = "SELECT * FROM prietenii WHERE \"id\" = ?";
        Prietenie prietenie = null;
        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query);) {
            preparedStatement.setLong(1, aLong);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Long iduser1 = resultSet.getLong("iduser1");
                Long iduser2 = resultSet.getLong("iduser2");
                prietenie = new Prietenie(iduser1, iduser2);
                prietenie.setId(aLong);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.ofNullable(prietenie);
    }

    @Override
    public Iterable<Prietenie> findAll() {
        Map<Long,Prietenie> prietenieMap = new HashMap<>();
        String query = "SELECT * FROM prietenii";
        try(Connection connection= DriverManager.getConnection(url, user, password);
            PreparedStatement preparedStatement = connection.prepareStatement(query);) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next())
            {
                Long id=resultSet.getLong("id");
                Long iduser1=resultSet.getLong("iduser1");
                Long iduser2=resultSet.getLong("iduser2");
                Prietenie prietenie = new Prietenie(iduser1, iduser2);
                prietenie.setId(id);

                prietenieMap.put(prietenie.getId(),prietenie);
            }
        }
        catch(SQLException e)
        {
            throw new RuntimeException(e);
        }
        return prietenieMap.values();
    }

    @Override
    public Optional<Prietenie> save(Prietenie entity) {
        if(entity == null)
            throw new IllegalArgumentException("Prietenia nu poate fi null");

        String query = "INSERT INTO prietenii (\"id\" ,\"iduser1\",\"iduser2\") VALUES (?, ?, ?)";

        try(Connection connection= DriverManager.getConnection(url,user,password);
            PreparedStatement preparedStatement = connection.prepareStatement(query);) {
            preparedStatement.setLong(1, entity.getId());
            preparedStatement.setLong(2, entity.getIdUser1());
            preparedStatement.setLong(3, entity.getIdUser2());
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
    public Optional<Prietenie> delete(Long aLong) {
        String query = "DELETE FROM prietenii WHERE \"id\" = ?";

        try(Connection connection= DriverManager.getConnection(url, user, password);
            PreparedStatement preparedStatement = connection.prepareStatement(query);) {
            preparedStatement.setLong(1, aLong);
            preparedStatement.executeUpdate();
        }
        catch(SQLException e)
        {
            throw new RuntimeException(e);
        }
        Prietenie toRemove= null;
        for(Prietenie prietenie : findAll())
        {
            if(Objects.equals(prietenie.getId(),aLong))
                toRemove=prietenie;
        }
        return Optional.ofNullable(toRemove);
    }

    @Override
    public Optional<Prietenie> update(Prietenie entity) {
        return Optional.empty();
    }
}
