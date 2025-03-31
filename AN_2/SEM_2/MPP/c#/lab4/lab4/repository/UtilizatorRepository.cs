namespace lab4.repository;
using lab4.utils;
using lab4.domain;
using log4net;
using Microsoft.Data.Sqlite;

public class UtilizatorRepository : IUtilizatorRepository
{
    private readonly DatabaseUtils _dbUtils;
    private static readonly ILog Logger = LogManager.GetLogger(typeof(UtilizatorRepository));

    public UtilizatorRepository(string connectionString)
    {
        _dbUtils = new DatabaseUtils();
    }

    public void Adauga(Utilizator utilizator)
    {
        Logger.Debug($"Incep adaugarea utilizatorului: {utilizator.GetNume()}");
        string sql = "INSERT INTO utilizator (nume, parola) VALUES (@nume, @parola)";

        try
        {
            using (var connection = _dbUtils.GetConnection())
            using (var command = new SqliteCommand(sql, connection))
            {
                command.Parameters.AddWithValue("@nume", utilizator.GetNume());
                command.Parameters.AddWithValue("@parola", utilizator.GetParola());

                connection.Open();
                command.ExecuteNonQuery();

                var getIdCommand = new SqliteCommand("SELECT last_insert_rowid();", connection);
                var lastInsertRowId = (long)getIdCommand.ExecuteScalar();
                utilizator.SetId((int)lastInsertRowId);

                Logger.Debug($"Utilizatorul {utilizator.GetNume()} a fost adaugat cu succes.");
            }
        }
        catch (Exception e)
        {
            Logger.Error($"Eroare la adaugarea utilizatorului: {utilizator.GetNume()}", e);
        }
    }

    public void Sterge(int id)
    {
        Logger.Debug($"Incep stergerea utilizatorului cu id {id}");
        string sql = "DELETE FROM utilizator WHERE idUtilizator = @id";

        try
        {
            using (SqliteConnection connection = _dbUtils.GetConnection())
            using (SqliteCommand command = new SqliteCommand(sql, connection))
            {
                command.Parameters.AddWithValue("@id", id);
                connection.Open();
                command.ExecuteNonQuery();
                Logger.Debug($"Utilizatorul cu id {id} a fost sters cu succes.");
            }
        }
        catch (Exception e)
        {
            Logger.Error($"Eroare la stergerea utilizatorului cu id {id}", e);
        }
    }

    public void Modifica(Utilizator utilizator)
    {
        Logger.Debug($"Incep modificarea utilizatorului cu idUtilizator {utilizator.GetId()}");
        string sql = "UPDATE utilizator SET nume = @nume, parola = @parola WHERE idUtilizator = @id";

        try
        {
            using (SqliteConnection connection = _dbUtils.GetConnection())
            using (SqliteCommand command = new SqliteCommand(sql, connection))
            {
                command.Parameters.AddWithValue("@nume", utilizator.GetNume());
                command.Parameters.AddWithValue("@parola", utilizator.GetParola());
                command.Parameters.AddWithValue("@id", utilizator.GetId());

                connection.Open();
                command.ExecuteNonQuery();
                Logger.Debug($"Utilizatorul cu idUtilizator {utilizator.GetId()} a fost modificat cu succes.");
            }
        }
        catch (Exception e)
        {
            Logger.Error($"Eroare la modificarea utilizatorului cu id {utilizator.GetId()}", e);
        }
    }

    public Utilizator FindById(int id)
    {
        Logger.Debug($"Incep cautarea utilizatorului cu id {id}");
        string sql = "SELECT * FROM utilizator WHERE idUtilizator = @id";
        Utilizator utilizator = null;

        try
        {
            using (SqliteConnection connection = _dbUtils.GetConnection())
            using (SqliteCommand command = new SqliteCommand(sql, connection))
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
                        Logger.Debug($"Utilizatorul cu id {id} a fost gasit.");
                    }
                }
            }
        }
        catch (Exception e)
        {
            Logger.Error($"Eroare la cautarea utilizatorului cu id {id}", e);
        }

        if (utilizator == null)
        {
            Logger.Debug($"Utilizatorul cu id {id} nu a fost gasit.");
        }

        return utilizator;
    }

    public List<Utilizator> FindAll()
    {
        Logger.Debug("Incep cautarea tuturor utilizatorilor.");
        List<Utilizator> utilizatori = new List<Utilizator>();
        string sql = "SELECT * FROM utilizator";

        try
        {
            using (SqliteConnection connection = _dbUtils.GetConnection())
            using (SqliteCommand command = new SqliteCommand(sql, connection))
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

                Logger.Debug("Cautarea tuturor utilizatorilor s-a realizat cu succes.");
            }
        }
        catch (Exception e)
        {
            Logger.Error("Eroare la preluarea tuturor utilizatorilor.", e);
        }

        return utilizatori;
    }

    public Utilizator FindByNume(string nume)
    {
        Logger.Debug($"Incep cautarea utilizatorului cu numele {nume}");
        string sql = "SELECT * FROM utilizator WHERE nume = @nume";
        Utilizator utilizator = null;

        try
        {
            using (SqliteConnection connection = _dbUtils.GetConnection())
            using (SqliteCommand command = new SqliteCommand(sql, connection))
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
                        Logger.Debug($"Utilizatorul cu numele {nume} a fost gasit.");
                    }
                }
            }
        }
        catch (Exception e)
        {
            Logger.Error($"Eroare la cautarea utilizatorului cu numele {nume}", e);
        }

        if (utilizator == null)
        {
            Logger.Debug($"Utilizatorul cu numele {nume} nu a fost gasit.");
        }

        return utilizator;
    }
}