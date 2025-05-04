namespace Persistence.mpp.persistence.repository;
using Microsoft.Data.Sqlite;
using Model.mpp.model;


public class ParticipantCursaRepository : IParticipantCursaRepository
{

    public List<ParticipantCursa> FindByCursa(Cursa cursa)
    {
        List<ParticipantCursa> result = new List<ParticipantCursa>();
        string sql = @"SELECT p.idParticipant, p.nume, p.CNP, p.capacitate_motor, e.idEchipa, e.nume AS numeEchipa
                      FROM participant_cursa pc
                      JOIN participant p ON pc.idParticipant = p.idParticipant
                      JOIN echipa e ON p.idEchipa = e.idEchipa
                      WHERE pc.idCursa = @idCursa";

        try
        {
            using var connection = DatabaseUtils.getConnection();
            using var command = new SqliteCommand(sql,connection);
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
        }
        catch (Exception e)
        {
            Console.WriteLine(e);
        }

        return result;
    }

    public List<ParticipantCursa> FindByParticipant(Participant participant)
    {
        List<ParticipantCursa> result = new List<ParticipantCursa>();
        string sql = @"SELECT c.idCursa, c.nume, c.capacitate_minima, c.capacitate_maxima
                      FROM participant_cursa pc
                      JOIN cursa c ON pc.idCursa = c.idCursa
                      WHERE pc.idParticipant = @idParticipant";

        try
        {
            using var connection = DatabaseUtils.getConnection();
            using var command = new SqliteCommand(sql,connection);
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

        }
        catch (Exception e)
        {
            Console.WriteLine(e);        }

        return result;
    }

    public void Adauga(ParticipantCursa participantCursa)
    {
        string sql = "INSERT INTO participant_cursa (idParticipant, idCursa) VALUES (@idParticipant, @idCursa)";

        try
        {
            using var connection = DatabaseUtils.getConnection();
            using var command = new SqliteCommand(sql,connection);
            {
                command.Parameters.AddWithValue("@idParticipant", participantCursa.Participant.GetId());
                command.Parameters.AddWithValue("@idCursa", participantCursa.Cursa.GetId());
                connection.Open();
                command.ExecuteNonQuery();
            }
            
        }
        catch (Exception e)
        {
            Console.WriteLine(e);        }
    }

    public void Sterge(int participantId)
    {
        string sql = "DELETE FROM participant_cursa WHERE idParticipant = @idParticipant";

        try
        {
            using var connection = DatabaseUtils.getConnection();
            using var command = new SqliteCommand(sql,connection);
            {
                command.Parameters.AddWithValue("@idParticipant", participantId);
                connection.Open();
                command.ExecuteNonQuery();
            }

        }
        catch (Exception e)
        {
            Console.WriteLine(e);        }
    }

    public void Modifica(ParticipantCursa participantCursa)
    {
        string sql = "UPDATE participant_cursa SET idCursa = @idCursa WHERE idParticipant = @idParticipant";

        try
        {
            using var connection = DatabaseUtils.getConnection();
            using var command = new SqliteCommand(sql,connection);
            {
                command.Parameters.AddWithValue("@idCursa", participantCursa.Cursa.GetId());
                command.Parameters.AddWithValue("@idParticipant", participantCursa.Participant.GetId());
                connection.Open();
                command.ExecuteNonQuery();
            }
            
        }
        catch (Exception e)
        {
            Console.WriteLine(e);        }
    }

    public ParticipantCursa FindById(int id)
    {
        return null;
    }

    public List<ParticipantCursa> FindAll()
    {
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
            using var connection = DatabaseUtils.getConnection();
            using var command = new SqliteCommand(sql,connection);
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
                            Console.WriteLine("reader is NULL");
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

        }
        catch (Exception e)
        {
            Console.WriteLine(e);        }

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
            using var connection = DatabaseUtils.getConnection();
            using var command = new SqliteCommand(sql,connection);
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
            Console.WriteLine(e);        
        }

        return result;
    }

    public void adaugainbaza(ParticipantCursa participantCursa)
    {
        if (participantCursa.Participant == null || participantCursa.Cursa == null)
        {
            Console.WriteLine("Participant sau Cursa este NULL in adaugainbaza!");
            return;
        }

        string sql = "INSERT INTO participant_cursa (idParticipant, idCursa) VALUES (@idParticipant, @idCursa)";

        try
        {
            using var connection = DatabaseUtils.getConnection();
            using var command = new SqliteCommand(sql, connection);
            {
                command.Parameters.AddWithValue("@idParticipant", participantCursa.Participant.GetId());
                command.Parameters.AddWithValue("@idCursa", participantCursa.Cursa.GetId());
                connection.Open();
                command.ExecuteNonQuery();
            }
        }
        catch (Exception e)
        {
            Console.WriteLine(e);
        }
    }

}