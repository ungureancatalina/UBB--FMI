namespace lab3.repository;
using lab3.utils;
using lab3.domain;
using log4net;
using Microsoft.Data.Sqlite;

public class ParticipantRepository : IParticipantRepository
{
    private static readonly ILog logger = LogManager.GetLogger(typeof(ParticipantRepository));
    private readonly DatabaseUtils dbUtils;

    public ParticipantRepository(string connectionString)
    {
        dbUtils = new DatabaseUtils();
    }

    public void Adauga(Participant participant)
    {
        logger.Debug($"Incep adaugarea participantului: {participant.GetNume()}");
        string sql =
            "INSERT INTO participant (nume, CNP, capacitate_motor, idEchipa) VALUES (@nume, @CNP, @capacitate_motor, @idEchipa)";

        try
        {
            using (var connection = dbUtils.GetConnection())
            using (var command = new SqliteCommand(sql, connection))
            {
                command.Parameters.AddWithValue("@nume", participant.GetNume());
                command.Parameters.AddWithValue("@CNP", participant.GetCNP());
                command.Parameters.AddWithValue("@capacitate_motor", participant.GetCapacitateMotor());
                command.Parameters.AddWithValue("@idEchipa", participant.GetEchipa().GetId());

                connection.Open();
                command.ExecuteNonQuery();

                var getIdCommand = new SqliteCommand("SELECT last_insert_rowid();", connection);
                var lastInsertRowId = (long)getIdCommand.ExecuteScalar();
                participant.SetId((int)lastInsertRowId);

                logger.Debug($"Participantul {participant.GetNume()} a fost adaugat cu succes.");
            }
        }
        catch (Exception e)
        {
            logger.Error($"Eroare la adaugarea participantului: {participant.GetNume()}", e);
        }
    }

    public void Sterge(int id)
    {
        logger.Debug($"Incep stergerea participantului cu id {id}");
        string sql = "DELETE FROM participant WHERE idParticipant = @idParticipant";

        try
        {
            using (var connection = dbUtils.GetConnection())
            using (var command = new SqliteCommand(sql, connection))
            {
                command.Parameters.AddWithValue("@idParticipant", id);

                connection.Open();
                command.ExecuteNonQuery();
                logger.Debug($"Participantul cu id {id} a fost sters cu succes.");
            }
        }
        catch (Exception e)
        {
            logger.Error($"Eroare la stergerea participantului cu id {id}", e);
        }
    }

    public void Modifica(Participant participant)
    {
        logger.Debug($"Incep modificarea participantului cu id {participant.GetId()}");
        string sql =
            "UPDATE participant SET nume = @nume, CNP = @CNP, capacitate_motor = @capacitate_motor, idEchipa = @idEchipa WHERE idParticipant = @idParticipant";

        try
        {
            using (var connection = dbUtils.GetConnection())
            using (var command = new SqliteCommand(sql, connection))
            {
                command.Parameters.AddWithValue("@nume", participant.GetNume());
                command.Parameters.AddWithValue("@CNP", participant.GetCNP());
                command.Parameters.AddWithValue("@capacitate_motor", participant.GetCapacitateMotor());
                command.Parameters.AddWithValue("@idEchipa", participant.GetEchipa().GetId());
                command.Parameters.AddWithValue("@idParticipant", participant.GetId());

                connection.Open();
                command.ExecuteNonQuery();

                logger.Debug($"Participantul cu id {participant.GetId()} a fost modificat cu succes.");
            }
        }
        catch (Exception e)
        {
            logger.Error($"Eroare la modificarea participantului cu id {participant.GetId()}", e);
        }
    }


    public Participant FindById(int id)
    {
        logger.Debug($"Incep cautarea participantului cu id {id}");
        string sql = @"
        SELECT p.idParticipant, p.nume, p.CNP, p.capacitate_motor, 
               e.idEchipa, e.nume AS echipaNume
        FROM participant p
        JOIN echipa e ON p.idEchipa = e.idEchipa
        WHERE p.idParticipant = @id";

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
                        int participantId = reader.GetInt32(0);
                        string nume = reader.GetString(1);
                        string CNP = reader.GetString(2);
                        int capacitateMotor = reader.GetInt32(3);
                        int echipaId = reader.GetInt32(4);
                        string echipaNume = reader.GetString(5);

                        Echipa echipa = new Echipa(echipaId, echipaNume);
                        Participant participant = new Participant(participantId, nume, CNP, capacitateMotor, echipa);

                        logger.Debug($"Participantul cu id {id} a fost gasit.");
                        return participant;
                    }
                }
            }
        }
        catch (Exception e)
        {
            logger.Error($"Eroare la cautarea participantului cu id {id}", e);
        }

        logger.Debug($"Participantul cu id {id} nu a fost gasit.");
        return null;
    }

    public List<Participant> FindAll()
    {
        logger.Debug("Incep cautarea tuturor participantilor.");
        List<Participant> participants = new List<Participant>();
        string sql = @"
        SELECT p.idParticipant, p.nume, p.CNP, p.capacitate_motor, 
               e.idEchipa, e.nume AS echipaNume
        FROM participant p
        JOIN echipa e ON p.idEchipa = e.idEchipa";

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
                        int participantId = reader.GetInt32(0);
                        string nume = reader.GetString(1);
                        string CNP = reader.GetString(2);
                        int capacitateMotor = reader.GetInt32(3);
                        int echipaId = reader.GetInt32(4);
                        string echipaNume = reader.GetString(5);

                        Echipa echipa = new Echipa(echipaId, echipaNume);
                        participants.Add(new Participant(participantId, nume, CNP, capacitateMotor, echipa));
                    }
                }
            }

            logger.Debug("Cautarea tuturor participantilor s-a realizat cu succes.");
        }
        catch (Exception e)
        {
            logger.Error("Eroare la preluarea tuturor participantilor.", e);
        }

        return participants;
    }


    public List<Participant> FindByEchipa(Echipa echipa)
    {
        logger.Debug($"Incep cautarea participantilor din echipa cu id {echipa.GetId()}");
        List<Participant> result = new List<Participant>();
        string sql = "SELECT * FROM participant WHERE idEchipa = @idEchipa";

        try
        {
            using (var connection = dbUtils.GetConnection())
            using (var command = new SqliteCommand(sql, connection))
            {
                command.Parameters.AddWithValue("@idEchipa", echipa.GetId());

                connection.Open();
                using (var reader = command.ExecuteReader())
                {
                    while (reader.Read())
                    {
                        int participantId = reader.GetInt32(reader.GetOrdinal("idParticipant"));
                        string nume = reader.GetString(reader.GetOrdinal("nume"));
                        string CNP = reader.GetString(reader.GetOrdinal("CNP"));
                        int capacitateMotor = reader.GetInt32(reader.GetOrdinal("capacitate_motor"));
                        result.Add(new Participant(participantId, nume, CNP, capacitateMotor, echipa));
                    }
                }
            }

            logger.Debug($"Cautarea participantilor din echipa cu id {echipa.GetId()} s-a realizat cu succes.");
        }
        catch (Exception e)
        {
            logger.Error($"Eroare la cautarea participantilor din echipa cu id {echipa.GetId()}", e);
        }

        return result;
    }


}