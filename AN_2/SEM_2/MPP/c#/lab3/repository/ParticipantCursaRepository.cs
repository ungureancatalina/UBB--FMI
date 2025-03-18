namespace lab3.repository;
using lab3.utils;
using lab3.domain;
using log4net;
using Microsoft.Data.Sqlite;

public class ParticipantCursaRepository : IParticipantCursaRepository
{
    private static readonly ILog logger = LogManager.GetLogger(typeof(ParticipantCursaRepository));
    private readonly DatabaseUtils dbUtils;

    public ParticipantCursaRepository(string connectionString)
    {
        dbUtils = new DatabaseUtils();
    }

    public List<ParticipantCursa> FindByCursa(Cursa cursa)
    {
        logger.Debug($"Incep cautarea participantilor pentru cursa cu id {cursa.GetId()}");
        List<ParticipantCursa> result = new List<ParticipantCursa>();
        string sql = @"SELECT p.idParticipant, p.nume, p.CNP, p.capacitate_motor, e.idEchipa, e.nume AS numeEchipa
                      FROM participant_cursa pc
                      JOIN participant p ON pc.idParticipant = p.idParticipant
                      JOIN echipa e ON p.idEchipa = e.idEchipa
                      WHERE pc.idCursa = @idCursa";

        try
        {
            using (var connection = dbUtils.GetConnection())
            using (var command = new SqliteCommand(sql, connection))
            {
                command.Parameters.AddWithValue("@idCursa", cursa.GetId());
                connection.Open();
                using (var reader = command.ExecuteReader())
                {
                    while (reader.Read())
                    {
                        int participantId = reader.GetInt32(reader.GetOrdinal("idParticipant"));
                        string nume = reader.GetString(reader.GetOrdinal("nume"));
                        string CNP = reader.GetString(reader.GetOrdinal("CNP"));
                        int capacitateMotor = reader.GetInt32(reader.GetOrdinal("capacitate_motor"));
                        int echipaId = reader.GetInt32(reader.GetOrdinal("idEchipa"));
                        string numeEchipa = reader.GetString(reader.GetOrdinal("numeEchipa"));

                        Echipa echipa = new Echipa(echipaId, numeEchipa);
                        Participant participant = new Participant(participantId, nume, CNP, capacitateMotor, echipa);
                        result.Add(new ParticipantCursa(participant, cursa));
                    }
                }
            }

            logger.Debug($"Cautarea participantilor pentru cursa cu id {cursa.GetId()} s-a incheiat cu succes.");
        }
        catch (Exception e)
        {
            logger.Error($"Eroare la cautarea participantilor pentru cursa cu id {cursa.GetId()}", e);
        }

        return result;
    }

    public List<ParticipantCursa> FindByParticipant(Participant participant)
    {
        logger.Debug($"Incep cautarea curselor pentru participantul cu id {participant.GetId()}");
        List<ParticipantCursa> result = new List<ParticipantCursa>();
        string sql = @"SELECT c.idCursa, c.nume, c.capacitate_minima, c.capacitate_maxima
                      FROM participant_cursa pc
                      JOIN cursa c ON pc.idCursa = c.idCursa
                      WHERE pc.idParticipant = @idParticipant";

        try
        {
            using (var connection = dbUtils.GetConnection())
            using (var command = new SqliteCommand(sql, connection))
            {
                command.Parameters.AddWithValue("@idParticipant", participant.GetId());
                connection.Open();
                using (var reader = command.ExecuteReader())
                {
                    while (reader.Read())
                    {
                        int cursaId = reader.GetInt32(reader.GetOrdinal("idCursa"));
                        string nume = reader.GetString(reader.GetOrdinal("nume"));
                        int capacitateMinima = reader.GetInt32(reader.GetOrdinal("capacitate_minima"));
                        int capacitateMaxima = reader.GetInt32(reader.GetOrdinal("capacitate_maxima"));

                        Cursa cursa = new Cursa(cursaId, nume, capacitateMinima, capacitateMaxima);
                        result.Add(new ParticipantCursa(participant, cursa));
                    }
                }
            }

            logger.Debug($"Cautarea curselor pentru participantul cu id {participant.GetId()} s-a incheiat cu succes.");
        }
        catch (Exception e)
        {
            logger.Error($"Eroare la cautarea curselor pentru participantul cu id {participant.GetId()}", e);
        }

        return result;
    }

    public void Adauga(ParticipantCursa participantCursa)
    {
        logger.Debug($"Incep adaugarea unui participant la cursa cu idCursa {participantCursa.GetCursa().GetId()}");
        string sql = "INSERT INTO participant_cursa (idParticipant, idCursa) VALUES (@idParticipant, @idCursa)";

        try
        {
            using (var connection = dbUtils.GetConnection())
            using (var command = new SqliteCommand(sql, connection))
            {
                command.Parameters.AddWithValue("@idParticipant", participantCursa.GetParticipant().GetId());
                command.Parameters.AddWithValue("@idCursa", participantCursa.GetCursa().GetId());
                connection.Open();
                command.ExecuteNonQuery();
            }

            logger.Debug(
                $"Adaugarea participantului cu id {participantCursa.GetParticipant().GetId()} la cursa cu idCursa {participantCursa.GetCursa().GetId()} s-a realizat cu succes.");
        }
        catch (Exception e)
        {
            logger.Error("Eroare la adaugarea participantului la cursa", e);
        }
    }

    public void Sterge(int participantId)
    {
        logger.Debug($"Incep stergerea participantului cu id {participantId}");
        string sql = "DELETE FROM participant_cursa WHERE idParticipant = @idParticipant";

        try
        {
            using (var connection = dbUtils.GetConnection())
            using (var command = new SqliteCommand(sql, connection))
            {
                command.Parameters.AddWithValue("@idParticipant", participantId);
                connection.Open();
                command.ExecuteNonQuery();
            }

            logger.Debug($"Stergerea participantului cu id {participantId} s-a realizat cu succes.");
        }
        catch (Exception e)
        {
            logger.Error($"Eroare la stergerea participantului cu id {participantId}", e);
        }
    }

    public void Modifica(ParticipantCursa participantCursa)
    {
        logger.Debug(
            $"Incep modificarea participantului cu idParticipant {participantCursa.GetParticipant().GetId()} pentru cursa cu idCursa {participantCursa.GetCursa().GetId()}");
        string sql = "UPDATE participant_cursa SET idCursa = @idCursa WHERE idParticipant = @idParticipant";

        try
        {
            using (var connection = dbUtils.GetConnection())
            using (var command = new SqliteCommand(sql, connection))
            {
                command.Parameters.AddWithValue("@idCursa", participantCursa.GetCursa().GetId());
                command.Parameters.AddWithValue("@idParticipant", participantCursa.GetParticipant().GetId());
                connection.Open();
                command.ExecuteNonQuery();
            }

            logger.Debug(
                $"Modificarea participantului cu idParticipant {participantCursa.GetParticipant().GetId()} pentru cursa cu idCursa {participantCursa.GetCursa().GetId()} s-a realizat cu succes.");
        }
        catch (Exception e)
        {
            logger.Error("Eroare la modificarea participantului cursa", e);
        }
    }

    public ParticipantCursa FindById(int id)
    {
        return null;
    }

    public List<ParticipantCursa> FindAll()
    {
        logger.Debug("Incep cautarea tuturor participantilor cursa");
        List<ParticipantCursa> result = new List<ParticipantCursa>();

        string sql = @"
                SELECT p.idParticipant, p.nume, p.CNP, p.capacitate_motor, 
                       e.idEchipa, e.nume AS echipaNume,
                       c.idCursa, c.nume AS cursaNume, c.capacitate_minima, c.capacitate_maxima
                FROM participant_cursa pc
                JOIN participant p ON pc.idParticipant = p.idParticipant
                JOIN echipa e ON p.idEchipa = e.idEchipa
                JOIN cursa c ON pc.idCursa = c.idCursa";

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
                        int cursaId = reader.GetInt32(6);
                        string cursaNume = reader.GetString(7);
                        int capacitateMinima = reader.GetInt32(8);
                        int capacitateMaxima = reader.GetInt32(9);

                        Echipa echipa = new Echipa(echipaId, echipaNume);
                        Participant participant = new Participant(participantId, nume, CNP, capacitateMotor, echipa);
                        Cursa cursa = new Cursa(cursaId, cursaNume, capacitateMinima, capacitateMaxima);
                        result.Add(new ParticipantCursa(participant, cursa));
                    }
                }
            }

            logger.Debug("Cautarea tuturor participantilor cursa s-a realizat cu succes.");
        }
        catch (Exception e)
        {
            logger.Error("Eroare la preluarea tuturor participantilor cursa", e);
        }

        return result;
    }
}