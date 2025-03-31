using System.Collections;

namespace lab4.repository;
using lab4.utils;
using lab4.domain;
using log4net;
using Microsoft.Data.Sqlite;

public class ParticipantRepository : IParticipantRepository
{
    private static readonly ILog Logger = LogManager.GetLogger(typeof(ParticipantRepository));
    private readonly DatabaseUtils _dbUtils;

    public ParticipantRepository(string connectionString)
    {
        _dbUtils = new DatabaseUtils();
    }

    public void Adauga(Participant participant)
    {
        Logger.Debug($"Incep adaugarea participantului: {participant.GetNume()}");
        string sql =
            "INSERT INTO participant (nume, CNP, capacitate_motor, idEchipa) VALUES (@nume, @CNP, @capacitate_motor, @idEchipa)";

        try
        {
            using (var connection = _dbUtils.GetConnection())
            using (var command = new SqliteCommand(sql, connection))
            {
                command.Parameters.AddWithValue("@nume", participant.GetNume());
                command.Parameters.AddWithValue("@CNP", participant.GetCnp());
                command.Parameters.AddWithValue("@capacitate_motor", participant.GetCapacitateMotor());
                command.Parameters.AddWithValue("@idEchipa", participant.GetEchipa().GetId());

                connection.Open();
                command.ExecuteNonQuery();

                var getIdCommand = new SqliteCommand("SELECT last_insert_rowid();", connection);
                var lastInsertRowId = (long)getIdCommand.ExecuteScalar();
                participant.SetId((int)lastInsertRowId);

                Logger.Debug($"Participantul {participant.GetNume()} a fost adaugat cu succes.");
            }
        }
        catch (Exception e)
        {
            Logger.Error($"Eroare la adaugarea participantului: {participant.GetNume()}", e);
        }
    }

    public void Sterge(int id)
    {
        Logger.Debug($"Incep stergerea participantului cu id {id}");
        string sql = "DELETE FROM participant WHERE idParticipant = @idParticipant";

        try
        {
            using (var connection = _dbUtils.GetConnection())
            using (var command = new SqliteCommand(sql, connection))
            {
                command.Parameters.AddWithValue("@idParticipant", id);

                connection.Open();
                command.ExecuteNonQuery();
                Logger.Debug($"Participantul cu id {id} a fost sters cu succes.");
            }
        }
        catch (Exception e)
        {
            Logger.Error($"Eroare la stergerea participantului cu id {id}", e);
        }
    }

    public void Modifica(Participant participant)
    {
        Logger.Debug($"Incep modificarea participantului cu id {participant.GetId()}");
        string sql =
            "UPDATE participant SET nume = @nume, CNP = @CNP, capacitate_motor = @capacitate_motor, idEchipa = @idEchipa WHERE idParticipant = @idParticipant";

        try
        {
            using (var connection = _dbUtils.GetConnection())
            using (var command = new SqliteCommand(sql, connection))
            {
                command.Parameters.AddWithValue("@nume", participant.GetNume());
                command.Parameters.AddWithValue("@CNP", participant.GetCnp());
                command.Parameters.AddWithValue("@capacitate_motor", participant.GetCapacitateMotor());
                command.Parameters.AddWithValue("@idEchipa", participant.GetEchipa().GetId());
                command.Parameters.AddWithValue("@idParticipant", participant.GetId());

                connection.Open();
                command.ExecuteNonQuery();

                Logger.Debug($"Participantul cu id {participant.GetId()} a fost modificat cu succes.");
            }
        }
        catch (Exception e)
        {
            Logger.Error($"Eroare la modificarea participantului cu id {participant.GetId()}", e);
        }
    }


    public Participant FindById(int id)
    {
        Logger.Debug($"Incep cautarea participantului cu id {id}");
        string sql = @"
        SELECT p.idParticipant, p.nume, p.CNP, p.capacitate_motor, 
               e.idEchipa, e.nume AS echipaNume
        FROM participant p
        JOIN echipa e ON p.idEchipa = e.idEchipa
        WHERE p.idParticipant = @id";

        try
        {
            using (var connection = _dbUtils.GetConnection())
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
                        string cnp = reader.GetString(2);
                        int capacitateMotor = reader.GetInt32(3);
                        int echipaId = reader.GetInt32(4);
                        string echipaNume = reader.GetString(5);

                        Echipa echipa = new Echipa(echipaId, echipaNume);
                        Participant participant = new Participant(participantId, nume, cnp, capacitateMotor, echipa);

                        Logger.Debug($"Participantul cu id {id} a fost gasit.");
                        return participant;
                    }
                }
            }
        }
        catch (Exception e)
        {
            Logger.Error($"Eroare la cautarea participantului cu id {id}", e);
        }

        Logger.Debug($"Participantul cu id {id} nu a fost gasit.");
        return null;
    }

    public List<Participant> FindAll()
    {
        Logger.Debug("Incep cautarea tuturor participantilor.");
        List<Participant> participants = new List<Participant>();
        string sql = @"
        SELECT p.idParticipant, p.nume, p.CNP, p.capacitate_motor, 
               e.idEchipa, e.nume AS echipaNume
        FROM participant p
        JOIN echipa e ON p.idEchipa = e.idEchipa";

        try
        {
            using (var connection = _dbUtils.GetConnection())
            using (var command = new SqliteCommand(sql, connection))
            {
                connection.Open();
                using (var reader = command.ExecuteReader())
                {
                    while (reader.Read())
                    {
                        int participantId = reader.GetInt32(0);
                        string nume = reader.GetString(1);
                        string cnp = reader.GetString(2);
                        int capacitateMotor = reader.GetInt32(3);
                        int echipaId = reader.GetInt32(4);
                        string echipaNume = reader.GetString(5);

                        Echipa echipa = new Echipa(echipaId, echipaNume);
                        participants.Add(new Participant(participantId, nume, cnp, capacitateMotor, echipa));
                    }
                }
            }

            Logger.Debug("Cautarea tuturor participantilor s-a realizat cu succes.");
        }
        catch (Exception e)
        {
            Logger.Error("Eroare la preluarea tuturor participantilor.", e);
        }

        return participants;
    }


    public List<ParticipantDTO> FindByEchipa(Echipa echipa)
    {
        Logger.Debug($"Incep cautarea participantilor din echipa cu id {echipa.GetId()}");
        List<ParticipantDTO> result = new List<ParticipantDTO>();
        string sql = "SELECT * FROM participant WHERE idEchipa = @idEchipa";

        try
        {
            using (var connection = _dbUtils.GetConnection())
            using (var command = new SqliteCommand(sql, connection))
            {
                command.Parameters.AddWithValue("@idEchipa", echipa.GetId());

                connection.Open();
                using (var reader = command.ExecuteReader())
                {
                    while (reader.Read())
                    {
                        string nume = reader.GetString(reader.GetOrdinal("nume"));
                        int capacitateMotor = reader.GetInt32(reader.GetOrdinal("capacitate_motor"));
                        result.Add(new ParticipantDTO( nume, capacitateMotor, echipa.GetNume()));
                    }
                }
            }

            Logger.Debug($"Cautarea participantilor din echipa cu id {echipa.GetId()} s-a realizat cu succes.");
        }
        catch (Exception e)
        {
            Logger.Error($"Eroare la cautarea participantilor din echipa cu id {echipa.GetId()}", e);
        }

        return result;
    }
    public List<Participant> FindByNume(string nume)
    {
        Logger.Debug($"Incep cautarea participantilor cu numele {nume}");
        List<Participant> result = new List<Participant>();
        string sql = "SELECT * FROM participant WHERE nume = @nume";

        try
        {
            using (var connection = _dbUtils.GetConnection())
            using (var command = new SqliteCommand(sql, connection))
            {
                command.Parameters.AddWithValue("@nume", nume);

                connection.Open();
                using (var reader = command.ExecuteReader())
                {
                    while (reader.Read())
                    {
                        int participantId = reader.GetInt32(reader.GetOrdinal("idParticipant"));
                        string numeParticipant = reader.GetString(reader.GetOrdinal("nume"));
                        string cnp = reader.GetString(reader.GetOrdinal("CNP"));
                        int capacitateMotor = reader.GetInt32(reader.GetOrdinal("capacitate_motor"));
                        int echipaId = reader.GetInt32(reader.GetOrdinal("idEchipa"));
                        string echipaNume = reader.GetString(reader.GetOrdinal("nume"));

                        Echipa echipa = new Echipa(echipaId, echipaNume);
                        result.Add(new Participant(participantId, numeParticipant, cnp, capacitateMotor, echipa));
                    }
                }
            }

            Logger.Debug($"Cautarea participantilor cu numele {nume} s-a realizat cu succes.");
        }
        catch (Exception e)
        {
            Logger.Error($"Eroare la cautarea participantilor cu numele {nume}.", e);
        }

        return result;
    }
    


}