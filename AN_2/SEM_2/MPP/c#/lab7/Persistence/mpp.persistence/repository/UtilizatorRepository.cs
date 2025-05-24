namespace Persistence.mpp.persistence.repository;
using Microsoft.Data.Sqlite;
using Model.mpp.model;

public class UtilizatorRepository : IUtilizatorRepository
{
    
    public void Adauga(Utilizator utilizator)
    {
        string sql = "INSERT INTO utilizator (nume, parola) VALUES (@nume, @parola)";

        try
        {
            using var connection = DatabaseUtils.getConnection();
            using var command = new SqliteCommand(sql,connection);
            {
                command.Parameters.AddWithValue("@nume", utilizator.Nume);
                command.Parameters.AddWithValue("@parola", utilizator.Parola);

                connection.Open();
                command.ExecuteNonQuery();
                command.CommandText = "SELECT last_insert_rowid();";
                utilizator.SetId((int)(long)command.ExecuteScalar());
            }
        }
        catch (Exception e)
        {
            Console.WriteLine(e);        }
    }

    public void Sterge(int id)
    {
        string sql = "DELETE FROM utilizator WHERE idUtilizator = @id";

        try
        {
            using var connection = DatabaseUtils.getConnection();
            using var command = new SqliteCommand(sql,connection);
            {
                command.Parameters.AddWithValue("@id", id);
                connection.Open();
                command.ExecuteNonQuery();
            }
        }
        catch (Exception e)
        {
            Console.WriteLine(e);        }
    }

    public void Modifica(Utilizator utilizator)
    {
        string sql = "UPDATE utilizator SET nume = @nume, parola = @parola WHERE idUtilizator = @id";

        try
        {
            using var connection = DatabaseUtils.getConnection();
            using var command = new SqliteCommand(sql,connection);
            {
                command.Parameters.AddWithValue("@nume", utilizator.Nume);
                command.Parameters.AddWithValue("@parola", utilizator.Parola);
                command.Parameters.AddWithValue("@id", utilizator.GetId());

                connection.Open();
                command.ExecuteNonQuery();
            }
        }
        catch (Exception e)
        {
            Console.WriteLine(e);        }
    }

    public Utilizator FindById(int id)
    {
        string sql = "SELECT * FROM utilizator WHERE idUtilizator = @id";
        Utilizator utilizator = null;

        try
        {
            using var connection = DatabaseUtils.getConnection();
            using var command = new SqliteCommand(sql,connection);
            {
                command.Parameters.AddWithValue("@id", id);

                connection.Open();
                using (SqliteDataReader reader = command.ExecuteReader())
                {
                    if (reader.Read())
                    {
                        utilizator = new Utilizator(
                            reader.GetInt32(reader.GetOrdinal("idUtilizator")),
                            reader.GetString(reader.GetOrdinal("nume")),
                            reader.GetString(reader.GetOrdinal("parola"))
                        );
                    }
                }
            }
        }
        catch (Exception e)
        {
            Console.WriteLine(e);        }

        if (utilizator == null)
        {
            Console.WriteLine("Utilizatorul cu idUtilizator null.");
        }

        return utilizator;
    }

    public List<Utilizator> FindAll()
    {
        List<Utilizator> utilizatori = new List<Utilizator>();
        string sql = "SELECT * FROM utilizator";

        try
        {
            using var connection = DatabaseUtils.getConnection();
            using var command = new SqliteCommand(sql,connection);
            {
                connection.Open();
                using (SqliteDataReader reader = command.ExecuteReader())
                {
                    while (reader.Read())
                    {
                        Utilizator utilizator = new Utilizator(
                            reader.GetInt32(reader.GetOrdinal("idUtilizator")),
                            reader.GetString(reader.GetOrdinal("nume")),
                            reader.GetString(reader.GetOrdinal("parola"))
                        );
                        utilizatori.Add(utilizator);
                    }
                }
            }
        }
        catch (Exception e)
        {
            Console.WriteLine(e);        }

        return utilizatori;
    }

    public Utilizator FindByNume(string nume)
    {
        string sql = "SELECT * FROM utilizator WHERE nume = @nume";
        Utilizator utilizator = null;

        try
        {
            using var connection = DatabaseUtils.getConnection();
            using var command = new SqliteCommand(sql,connection);
            {
                command.Parameters.AddWithValue("@nume", nume);

                connection.Open();
                using (SqliteDataReader reader = command.ExecuteReader())
                {
                    if (reader.Read())
                    {
                        utilizator = new Utilizator(
                            reader.GetInt32(reader.GetOrdinal("idUtilizator")),
                            reader.GetString(reader.GetOrdinal("nume")),
                            reader.GetString(reader.GetOrdinal("parola"))
                        );
                    }
                }
            }
        }
        catch (Exception e)
        {
            Console.WriteLine(e);        }

        if (utilizator == null)
        {
            Console.WriteLine("Utilizatorul cu idUtilizator null.");
        }

        return utilizator;
    }
}