namespace lab3.repository;
using lab3.utils;
using lab3.domain;
using log4net;
using Microsoft.Data.Sqlite;


public class EchipaRepository : IEchipaRepository
{
    private readonly DatabaseUtils dbUtils;
    private static readonly ILog logger = LogManager.GetLogger(typeof(EchipaRepository));

    public EchipaRepository(string connectionString)
    {
        dbUtils = new DatabaseUtils();
    }

    public void Adauga(Echipa echipa)
    {
        logger.Debug("Se adauga o echipa in baza de date.");
        string sql = "INSERT INTO echipa (nume) VALUES (@nume)";
        try
        {
            using (var connection = dbUtils.GetConnection())
            using (var command = new SqliteCommand(sql, connection))
            {
                command.Parameters.AddWithValue("@nume", echipa.GetNume());

                connection.Open();
                command.ExecuteNonQuery();
                var getIdCommand = new SqliteCommand("SELECT last_insert_rowid();", connection);
                var lastInsertRowId = (long)getIdCommand.ExecuteScalar();
                echipa.SetId((int)lastInsertRowId);
                logger.Debug("Echipa a fost adaugata cu succes.");
            }
        }
        catch (Exception e)
        {
            logger.Error("Eroare la adaugarea echipei: " + echipa.GetNume(), e);
        }
    }

    public void Sterge(int id)
    {
        logger.Debug($"Se sterge echipa cu id {id}");
        string sql = "DELETE FROM echipa WHERE idEchipa = @id";
        try
        {
            using (var connection = dbUtils.GetConnection())
            using (var command = new SqliteCommand(sql, connection))
            {
                command.Parameters.AddWithValue("@id", id);
                connection.Open();
                command.ExecuteNonQuery();
                logger.Debug("Echipa a fost stearsa cu succes.");
            }
        }
        catch (Exception e)
        {
            logger.Error($"Eroare la stergerea echipei cu id {id}: " + e.Message, e);
        }
    }

    public void Modifica(Echipa echipa)
    {
        logger.Debug($"Se modifica echipa cu id {echipa.GetId()}");
        string sql = "UPDATE echipa SET nume = @nume WHERE idEchipa = @id";
        try
        {
            using (var connection = dbUtils.GetConnection())
            using (var command = new SqliteCommand(sql, connection))
            {
                command.Parameters.AddWithValue("@nume", echipa.GetNume());
                command.Parameters.AddWithValue("@id", echipa.GetId());

                connection.Open();
                command.ExecuteNonQuery();
                logger.Debug("Echipa a fost modificata cu succes.");
            }
        }
        catch (Exception e)
        {
            logger.Error($"Eroare la modificarea echipei cu id {echipa.GetId()}: " + e.Message, e);
        }
    }

    public Echipa FindById(int id)
    {
        logger.Debug($"Se cauta echipa cu id {id}");
        string sql = "SELECT * FROM echipa WHERE idEchipa = @id";
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
                        var echipa = new Echipa(
                            reader.GetInt32(reader.GetOrdinal("idEchipa")),
                            reader.GetString(reader.GetOrdinal("nume"))
                        );
                        logger.Debug($"Echipa gasita: {echipa.GetNume()}");
                        return echipa;
                    }
                }
            }
        }
        catch (Exception e)
        {
            logger.Error($"Eroare la cautarea echipei cu id {id}: " + e.Message, e);
        }

        logger.Debug("Echipa nu a fost gasita.");
        return null;
    }

    public List<Echipa> FindAll()
    {
        logger.Debug("Se preiau toate echipele din baza de date.");
        List<Echipa> echipeList = new List<Echipa>();
        string sql = "SELECT * FROM echipa";
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
                        var echipa = new Echipa(
                            reader.GetInt32(reader.GetOrdinal("idEchipa")),
                            reader.GetString(reader.GetOrdinal("nume"))
                        );
                        echipeList.Add(echipa);
                    }
                }
            }

            logger.Debug($"S-au preluat {echipeList.Count} echipe.");
        }
        catch (Exception e)
        {
            logger.Error("Eroare la preluarea tuturor echipelor: " + e.Message, e);
        }

        return echipeList;
    }

    public Echipa FindByNume(string nume)
    {
        logger.Debug($"Se cauta echipa cu numele {nume}");
        string sql = "SELECT * FROM echipa WHERE nume = @nume";
        try
        {
            using (var connection = dbUtils.GetConnection())
            using (var command = new SqliteCommand(sql, connection))
            {
                command.Parameters.AddWithValue("@nume", nume);
                connection.Open();
                using (var reader = command.ExecuteReader())
                {
                    if (reader.Read())
                    {
                        var echipa = new Echipa(
                            reader.GetInt32(reader.GetOrdinal("idEchipa")),
                            reader.GetString(reader.GetOrdinal("nume"))
                        );
                        logger.Debug($"Echipa gasita: {echipa.GetNume()}");
                        return echipa;
                    }
                }
            }
        }
        catch (Exception e)
        {
            logger.Error($"Eroare la cautarea echipei cu numele {nume}: " + e.Message, e);
        }

        logger.Debug("Echipa nu a fost gasita.");
        return null;
    }

    public List<Echipa> FindAllOrderedByNume()
    {
        logger.Debug("Se preiau toate echipele ordonate dupa nume.");
        List<Echipa> echipeList = new List<Echipa>();
        string sql = "SELECT * FROM echipa ORDER BY nume";
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
                        var echipa = new Echipa(
                            reader.GetInt32(reader.GetOrdinal("idEchipa")),
                            reader.GetString(reader.GetOrdinal("nume"))
                        );
                        echipeList.Add(echipa);
                    }
                }
            }

            logger.Debug($"S-au preluat {echipeList.Count} echipe ordonate.");
        }
        catch (Exception e)
        {
            logger.Error("Eroare la preluarea echipelor ordonate: " + e.Message, e);
        }

        return echipeList;
    }
}