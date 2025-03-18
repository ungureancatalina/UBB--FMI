namespace lab3.repository;
using lab3.utils;
using lab3.domain;
using log4net;
using Microsoft.Data.Sqlite;

public class CursaRepository : ICursaRepository
{
    private readonly DatabaseUtils dbUtils;
    private static readonly ILog logger = LogManager.GetLogger(typeof(CursaRepository));

    public CursaRepository(string connectionString)
    {
        dbUtils = new DatabaseUtils();
    }

    public void Adauga(Cursa cursa)
    {
        logger.Debug("Se adauga o cursa in baza de date.");
        string sql =
            "INSERT INTO cursa (nume, capacitate_minima, capacitate_maxima) VALUES (@nume, @capacitate_minima, @capacitate_maxima)";
        try
        {
            using (var connection = dbUtils.GetConnection())
            using (var command = new SqliteCommand(sql, connection))
            {
                command.Parameters.AddWithValue("@nume", cursa.GetNume());
                command.Parameters.AddWithValue("@capacitate_minima", cursa.GetCapacitateMinima());
                command.Parameters.AddWithValue("@capacitate_maxima", cursa.GetCapacitateMaxima());

                connection.Open();
                command.ExecuteNonQuery();
                var getIdCommand = new SqliteCommand("SELECT last_insert_rowid();", connection);
                var lastInsertRowId = (long)getIdCommand.ExecuteScalar();
                cursa.SetId((int)lastInsertRowId);

                logger.Debug("Cursa a fost adaugata cu succes.");
            }
        }
        catch (Exception e)
        {
            logger.Error("Eroare la adaugarea cursei: " + cursa.GetNume(), e);
        }
    }

    public void Sterge(int id)
    {
        logger.Debug($"Se sterge cursa cu id {id}");
        string sql = "DELETE FROM cursa WHERE idCursa = @id";
        try
        {
            using (var connection = dbUtils.GetConnection())
            using (var command = new SqliteCommand(sql, connection))
            {
                command.Parameters.AddWithValue("@id", id);
                connection.Open();
                command.ExecuteNonQuery();
                logger.Debug("Cursa a fost stearsa cu succes.");
            }
        }
        catch (Exception e)
        {
            logger.Error($"Eroare la stergerea cursei cu id {id}: " + e.Message, e);
        }
    }

    public void Modifica(Cursa cursa)
    {
        logger.Debug($"Se modifica cursa cu id {cursa.GetId()}");
        string sql =
            "UPDATE cursa SET nume = @nume, capacitate_minima = @capacitate_minima, capacitate_maxima = @capacitate_maxima WHERE idCursa = @id";
        try
        {
            using (var connection = dbUtils.GetConnection())
            using (var command = new SqliteCommand(sql, connection))
            {
                command.Parameters.AddWithValue("@nume", cursa.GetNume());
                command.Parameters.AddWithValue("@capacitate_minima", cursa.GetCapacitateMinima());
                command.Parameters.AddWithValue("@capacitate_maxima", cursa.GetCapacitateMaxima());
                command.Parameters.AddWithValue("@id", cursa.GetId());

                connection.Open();
                command.ExecuteNonQuery();
                logger.Debug("Cursa a fost modificata cu succes.");
            }
        }
        catch (Exception e)
        {
            logger.Error($"Eroare la modificarea cursei cu id {cursa.GetId()}: " + e.Message, e);
        }
    }

    public Cursa FindById(int id)
    {
        logger.Debug($"Se cauta cursa cu id {id}");
        string sql = "SELECT * FROM cursa WHERE idCursa = @id";
        try
        {
            using (var connection = dbUtils.GetConnection())
            using (var command = new SqliteCommand(sql, connection))
            {
                command.Parameters.AddWithValue("@id", id);
                connection.Open();
                using (var reader = command.ExecuteReader())
                {
                    if (reader.Read())
                    {
                        var cursa = new Cursa(
                            reader.GetInt32(reader.GetOrdinal("idCursa")),
                            reader.GetString(reader.GetOrdinal("nume")),
                            reader.GetInt32(reader.GetOrdinal("capacitate_minima")),
                            reader.GetInt32(reader.GetOrdinal("capacitate_maxima"))
                        );
                        logger.Debug($"Cursa gasita: {cursa.GetNume()}");
                        return cursa;
                    }
                }
            }
        }
        catch (Exception e)
        {
            logger.Error($"Eroare la gasirea cursei cu id {id}: " + e.Message, e);
        }

        logger.Debug("Cursa nu a fost gasita.");
        return null;
    }

    public List<Cursa> FindAll()
    {
        logger.Debug("Se preiau toate cursele din baza de date.");
        List<Cursa> cursaList = new List<Cursa>();
        string sql = "SELECT * FROM cursa";
        try
        {
            using (var connection = dbUtils.GetConnection())
            using (var command = new SqliteCommand(sql, connection))
            {
                connection.Open();
                using (var reader = command.ExecuteReader())
                {
                    while (reader.Read())
                    {
                        var cursa = new Cursa(
                            reader.GetInt32(reader.GetOrdinal("idCursa")),
                            reader.GetString(reader.GetOrdinal("nume")),
                            reader.GetInt32(reader.GetOrdinal("capacitate_minima")),
                            reader.GetInt32(reader.GetOrdinal("capacitate_maxima"))
                        );
                        cursaList.Add(cursa);
                    }
                }
            }

            logger.Debug($"S-au preluat {cursaList.Count} curse.");
        }
        catch (Exception e)
        {
            logger.Error($"Eroare la preluarea tuturor curselor: " + e.Message, e);
        }

        return cursaList;
    }

    public List<Cursa> FindByCapacitateMinima(int capacitate)
    {
        logger.Debug($"Se cauta curse cu capacitate minima de {capacitate}");
        List<Cursa> cursaList = new List<Cursa>();
        string sql = "SELECT * FROM cursa WHERE capacitate_minima >= @capacitate";
        try
        {
            using (var connection = dbUtils.GetConnection())
            using (var command = new SqliteCommand(sql, connection))
            {
                command.Parameters.AddWithValue("@capacitate", capacitate);
                connection.Open();
                using (var reader = command.ExecuteReader())
                {
                    while (reader.Read())
                    {
                        var cursa = new Cursa(
                            reader.GetInt32(reader.GetOrdinal("idCursa")),
                            reader.GetString(reader.GetOrdinal("nume")),
                            reader.GetInt32(reader.GetOrdinal("capacitate_minima")),
                            reader.GetInt32(reader.GetOrdinal("capacitate_maxima"))
                        );
                        cursaList.Add(cursa);
                    }
                }
            }

            logger.Debug($"S-au gasit {cursaList.Count} curse cu capacitate minima de {capacitate}");
        }
        catch (Exception e)
        {
            logger.Error($"Eroare la cautarea cursei cu capacitate minima de {capacitate}: " + e.Message, e);
        }

        return cursaList;
    }
}