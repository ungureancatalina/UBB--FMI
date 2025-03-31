namespace lab4.repository;
using lab4.utils;
using lab4.domain;
using log4net;
using Microsoft.Data.Sqlite;

public class ParticipantCursaRepository : IParticipantCursaRepository
{
    private static readonly ILog Logger = LogManager.GetLogger(typeof(ParticipantCursaRepository));
    private readonly DatabaseUtils _dbUtils;

    public ParticipantCursaRepository(string connectionString)
    {
        _dbUtils = new DatabaseUtils();
    }

    public List<ParticipantCursa> FindByCursa(Cursa cursa)
    {
        Logger.Debug($"Incep cautarea participantilor pentru cursa cu id {cursa.GetId()}");
        List<ParticipantCursa> result = new List<ParticipantCursa>();
        string sql = @"SELECT p.idParticipant, p.nume, p.CNP, p.capacitate_motor, e.idEchipa, e.nume AS numeEchipa
                      FROM participant_cursa pc
                      JOIN participant p ON pc.idParticipant = p.idParticipant
                      JOIN echipa e ON p.idEchipa = e.idEchipa
                      WHERE pc.idCursa = @idCursa";

        try
        {
            using (var connection = _dbUtils.GetConnection())
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
                        string cnp = reader.GetString(reader.GetOrdinal("CNP"));
                        int capacitateMotor = reader.GetInt32(reader.GetOrdinal("capacitate_motor"));
                        int echipaId = reader.GetInt32(reader.GetOrdinal("idEchipa"));
                        string numeEchipa = reader.GetString(reader.GetOrdinal("numeEchipa"));

                        Echipa echipa = new Echipa(echipaId, numeEchipa);
                        Participant participant = new Participant(participantId, nume, cnp, capacitateMotor, echipa);
                        result.Add(new ParticipantCursa(participant, cursa));
                    }
                }
            }

            Logger.Debug($"Cautarea participantilor pentru cursa cu id {cursa.GetId()} s-a incheiat cu succes.");
        }
        catch (Exception e)
        {
            Logger.Error($"Eroare la cautarea participantilor pentru cursa cu id {cursa.GetId()}", e);
        }

        return result;
    }

    public List<ParticipantCursa> FindByParticipant(Participant participant)
    {
        Logger.Debug($"Incep cautarea curselor pentru participantul cu id {participant.GetId()}");
        List<ParticipantCursa> result = new List<ParticipantCursa>();
        string sql = @"SELECT c.idCursa, c.nume, c.capacitate_minima, c.capacitate_maxima
                      FROM participant_cursa pc
                      JOIN cursa c ON pc.idCursa = c.idCursa
                      WHERE pc.idParticipant = @idParticipant";

        try
        {
            using (var connection = _dbUtils.GetConnection())
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

            Logger.Debug($"Cautarea curselor pentru participantul cu id {participant.GetId()} s-a incheiat cu succes.");
        }
        catch (Exception e)
        {
            Logger.Error($"Eroare la cautarea curselor pentru participantul cu id {participant.GetId()}", e);
        }

        return result;
    }

    public void Adauga(ParticipantCursa participantCursa)
    {
        Logger.Debug($"Incep adaugarea unui participant la cursa cu idCursa {participantCursa.GetCursa().GetId()}");
        string sql = "INSERT INTO participant_cursa (idParticipant, idCursa) VALUES (@idParticipant, @idCursa)";

        try
        {
            using (var connection = _dbUtils.GetConnection())
            using (var command = new SqliteCommand(sql, connection))
            {
                command.Parameters.AddWithValue("@idParticipant", participantCursa.GetParticipant().GetId());
                command.Parameters.AddWithValue("@idCursa", participantCursa.GetCursa().GetId());
                connection.Open();
                command.ExecuteNonQuery();
            }

            Logger.Debug(
                $"Adaugarea participantului cu id {participantCursa.GetParticipant().GetId()} la cursa cu idCursa {participantCursa.GetCursa().GetId()} s-a realizat cu succes.");
        }
        catch (Exception e)
        {
            Logger.Error("Eroare la adaugarea participantului la cursa", e);
        }
    }

    public void Sterge(int participantId)
    {
        Logger.Debug($"Incep stergerea participantului cu id {participantId}");
        string sql = "DELETE FROM participant_cursa WHERE idParticipant = @idParticipant";

        try
        {
            using (var connection = _dbUtils.GetConnection())
            using (var command = new SqliteCommand(sql, connection))
            {
                command.Parameters.AddWithValue("@idParticipant", participantId);
                connection.Open();
                command.ExecuteNonQuery();
            }

            Logger.Debug($"Stergerea participantului cu id {participantId} s-a realizat cu succes.");
        }
        catch (Exception e)
        {
            Logger.Error($"Eroare la stergerea participantului cu id {participantId}", e);
        }
    }

    public void Modifica(ParticipantCursa participantCursa)
    {
        Logger.Debug(
            $"Incep modificarea participantului cu idParticipant {participantCursa.GetParticipant().GetId()} pentru cursa cu idCursa {participantCursa.GetCursa().GetId()}");
        string sql = "UPDATE participant_cursa SET idCursa = @idCursa WHERE idParticipant = @idParticipant";

        try
        {
            using (var connection = _dbUtils.GetConnection())
            using (var command = new SqliteCommand(sql, connection))
            {
                command.Parameters.AddWithValue("@idCursa", participantCursa.GetCursa().GetId());
                command.Parameters.AddWithValue("@idParticipant", participantCursa.GetParticipant().GetId());
                connection.Open();
                command.ExecuteNonQuery();
            }

            Logger.Debug(
                $"Modificarea participantului cu idParticipant {participantCursa.GetParticipant().GetId()} pentru cursa cu idCursa {participantCursa.GetCursa().GetId()} s-a realizat cu succes.");
        }
        catch (Exception e)
        {
            Logger.Error("Eroare la modificarea participantului cursa", e);
        }
    }

    public ParticipantCursa FindById(int id)
    {
        return null;
    }

    public List<ParticipantCursa> FindAll()
    {
        Logger.Debug("Incep cautarea tuturor participantilor cursa");
        List<ParticipantCursa> result = new List<ParticipantCursa>();

        string sql = @"
         SELECT p.idParticipant, p.nume, p.CNP, p.capacitate_motor, p.idEchipa, 
           e.idEchipa, e.nume AS echipaNume, 
           c.idCursa, c.nume AS cursaNume, c.capacitate_minima, c.capacitate_maxima
        FROM participant_cursa pc
        JOIN participant p ON pc.idParticipant = p.idParticipant
        JOIN echipa e ON p.idEchipa = e.idEchipa
        JOIN cursa c ON pc.idCursa = c.idCursa";

        try
        {
            using (var connection = _dbUtils.GetConnection())
            using (var command = new SqliteCommand(sql, connection))
            {
                connection.Open();
                using (var reader = command.ExecuteReader())
                { int idParticipantOrdinal = reader.GetOrdinal("idParticipant");
                    int numeOrdinal = reader.GetOrdinal("nume");
                    int cnpOrdinal = reader.GetOrdinal("CNP");
                    int capacitateMotorOrdinal = reader.GetOrdinal("capacitate_motor");
                    int idEchipaOrdinal = reader.GetOrdinal("idEchipa");
                    int echipaNumeOrdinal = reader.GetOrdinal("echipaNume");
                    int idCursaOrdinal = reader.GetOrdinal("idCursa");
                    int cursaNumeOrdinal = reader.GetOrdinal("cursaNume");
                    int capacitateMinimaOrdinal = reader.GetOrdinal("capacitate_minima");
                    int capacitateMaximaOrdinal = reader.GetOrdinal("capacitate_maxima");

                    while (reader.Read())
                    {
                        int participantId = reader.GetInt32(idParticipantOrdinal);
                        string nume = reader.GetString(numeOrdinal);
                        string cnp = reader.GetString(cnpOrdinal);
                        int capacitateMotor = reader.GetInt32(capacitateMotorOrdinal);
                        int echipaId = reader.GetInt32(idEchipaOrdinal);
                        
                        Echipa echipa = null;
                        if (reader.IsDBNull(idCursaOrdinal))
                        {
                            Logger.Warn("Participantul nu are asociata o echipa");
                        }
                        else
                        {
                            String echipanume= reader.GetString(echipaNumeOrdinal);
                            echipa = new Echipa(echipaId, echipanume);
                        }

                        string echipaNume = reader.GetString(echipaNumeOrdinal);
                        int cursaId = reader.GetInt32(idCursaOrdinal);
                        string cursaNume = reader.GetString(cursaNumeOrdinal);
                        int capacitateMinima = reader.GetInt32(capacitateMinimaOrdinal);
                        int capacitateMaxima = reader.GetInt32(capacitateMaximaOrdinal);
                        
                        Participant participant = new Participant(participantId, nume, cnp, capacitateMotor, echipa);
                        Cursa cursa = new Cursa(cursaId, cursaNume, capacitateMinima, capacitateMaxima);
                        result.Add(new ParticipantCursa(participant, cursa));
                    }
                }
            }

            Logger.Debug("Cautarea tuturor participantilor cursa s-a realizat cu succes.");
        }
        catch (Exception e)
        {
            Logger.Error("Eroare la preluarea tuturor participantilor cursa", e);
        }

        return result;
    }
    
   public int CountByCursa(Cursa cursa)
    {
        int result = 0;
        string sql = @"
            SELECT p.idParticipant, p.nume, p.CNP, p.capacitate_motor, e.idEchipa, e.nume AS echipaNume, 
                   c.idCursa, c.nume AS cursaNume, c.capacitate_minima, c.capacitate_maxima
            FROM participant_cursa pc
            JOIN participant p ON pc.idParticipant = p.idParticipant
            JOIN cursa c ON pc.idCursa = c.idCursa
            JOIN echipa e ON p.idEchipa = e.idEchipa
             WHERE pc.idCursa = @idCursa";

        try
        {
            using (var connection = _dbUtils.GetConnection())
            using (var command = new SqliteCommand(sql, connection))
            {
                command.Parameters.AddWithValue("@idCursa", cursa.GetId().ToString());
                connection.Open();

                using (var reader = command.ExecuteReader())
                {
                    while (reader.Read())
                    {
                        int participantId = reader.GetInt32(reader.GetOrdinal("idParticipant"));
                        int cursaId = reader.GetInt32(reader.GetOrdinal("idCursa"));
                        string cursaNume = reader.GetString(reader.GetOrdinal("cursaNume"));
                        int capacitateMinima = reader.GetInt32(reader.GetOrdinal("capacitate_minima"));
                        int capacitateMaxima = reader.GetInt32(reader.GetOrdinal("capacitate_maxima"));
                        result+=1;
                    }
                }
            }
        }
        catch (Exception e)
        {
            Logger.Error($"Eroare la cautarea participantilor pentru cursa cu id {cursa.GetId()}", e);
        }

        return result;
    }
   
    public void adaugainbaza(Participant participant, Cursa cursa)
    {
        Logger.Debug($"Incep adaugarea unui participant la cursa cu idCursa {participant.GetId()} {cursa.GetId()}");
        string sql = "INSERT INTO participant_cursa (idParticipant, idCursa) VALUES (@idParticipant, @idCursa)";

        try
        {
            using (var connection = _dbUtils.GetConnection())
            using (var command = new SqliteCommand(sql, connection))
            {
                command.Parameters.AddWithValue("@idParticipant",participant.GetId());
                command.Parameters.AddWithValue("@idCursa", cursa.GetId());
                connection.Open();
                command.ExecuteNonQuery();
            }

            Logger.Debug(
                $"Adaugarea participantului cu id {participant.GetId()} la cursa cu idCursa {cursa.GetId()} s-a realizat cu succes.");
        }
        catch (Exception e)
        {
            Logger.Error("Eroare la adaugarea participantului la cursa", e);
        }
    }
   
}