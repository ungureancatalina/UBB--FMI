namespace Persistence.mpp.persistence.repository;
using Model.mpp.model;
using Microsoft.Data.Sqlite;

public class ParticipantRepository : IParticipantRepository
{
    public void Adauga(Participant participant)
    {
        string sql = @"INSERT INTO participant (nume, CNP, capacitate_motor, idEchipa) VALUES (@nume, @CNP, @capacitate_motor, @idEchipa)";
        try
        {
            using var connection = DatabaseUtils.getConnection();
            connection.Open(); 

            using var command = new SqliteCommand(sql, connection);
            command.Parameters.AddWithValue("@nume", participant.Nume);
            command.Parameters.AddWithValue("@CNP", participant.Cnp);
            command.Parameters.AddWithValue("@capacitate_motor", participant.CapacitateMotor);
            command.Parameters.AddWithValue("@idEchipa", participant.Echipa.GetId());

            command.ExecuteNonQuery(); 

            command.CommandText = "SELECT last_insert_rowid();";
            command.Parameters.Clear(); 
            participant.SetId((int)(long)command.ExecuteScalar());
        }
        catch (Exception e)
        {
            Console.WriteLine(" Eroare la inserare: " + e.Message);
        }
    }

    public void Sterge(int id)
    {
        string sql = "DELETE FROM participant WHERE idParticipant = @idParticipant";

        try
        {
            using var connection = DatabaseUtils.getConnection();
            using var command = new SqliteCommand(sql,connection);
            {
                command.Parameters.AddWithValue("@idParticipant", id);

                connection.Open();
                command.ExecuteNonQuery();
            }
        }
        catch (Exception e)
        {
            Console.WriteLine(e);        }
    }

    public void Modifica(Participant participant)
    {
        string sql =
            "UPDATE participant SET nume = @nume, CNP = @CNP, capacitate_motor = @capacitate_motor, idEchipa = @idEchipa WHERE idParticipant = @idParticipant";

        try
        {
            using var connection = DatabaseUtils.getConnection();
            using var command = new SqliteCommand(sql,connection);
            {
                command.Parameters.AddWithValue("@nume", participant.Nume);
                command.Parameters.AddWithValue("@CNP", participant.Cnp);
                command.Parameters.AddWithValue("@capacitate_motor", participant.CapacitateMotor);
                command.Parameters.AddWithValue("@idEchipa", participant.Echipa.GetId());
                command.Parameters.AddWithValue("@idParticipant", participant.GetId());

                connection.Open();
                command.ExecuteNonQuery();

            }
        }
        catch (Exception e)
        {
            Console.WriteLine(e);        }
    }


    public Participant FindById(int id)
    {
        string sql = @"
        SELECT p.idParticipant, p.nume, p.CNP, p.capacitate_motor, 
               e.idEchipa, e.nume AS echipaNume
        FROM participant p
        JOIN echipa e ON p.idEchipa = e.idEchipa
        WHERE p.idParticipant = @id";

        try
        {
            using var connection = DatabaseUtils.getConnection();
            using var command = new SqliteCommand(sql,connection);
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

                        return participant;
                    }
                }
            }
        }
        catch (Exception e)
        {
            Console.WriteLine(e);        }

        return null;
    }

    public List<Participant> FindAll()
    {
        List<Participant> participants = new List<Participant>();
        string sql = @"
        SELECT p.idParticipant, p.nume, p.CNP, p.capacitate_motor, 
               e.idEchipa, e.nume AS echipaNume
        FROM participant p
        JOIN echipa e ON p.idEchipa = e.idEchipa";

        try
        {
            using var connection = DatabaseUtils.getConnection();
            using var command = new SqliteCommand(sql,connection);
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

        }
        catch (Exception e)
        {
            Console.WriteLine(e);        }

        return participants;
    }

    public List<Participant> FindByEchipa(Echipa echipa)
    {
        var result = new List<Participant>();
        const string sql = "SELECT p.idParticipant, p.nume, p.CNP, p.capacitate_motor, e.idEchipa, e.nume AS echipaNume " +
                           "FROM participant p " +
                           "JOIN echipa e ON p.idEchipa = e.idEchipa " +
                           "WHERE p.idEchipa = @idEchipa";
        using var connection = DatabaseUtils.getConnection();
        using var command = new SqliteCommand(sql,connection);
        command.Parameters.AddWithValue("@idEchipa", echipa.GetId());

        connection.Open();
        using var reader = command.ExecuteReader();
        while (reader.Read())
        {
            var ech = new Echipa(reader.GetInt32(4), reader.GetString(5));
            var participant = new Participant(
                reader.GetInt32(0),
                reader.GetString(1),
                reader.GetString(2),
                reader.GetInt32(3),
                ech
            );
            result.Add(participant);
        }

        return result;
    }
    
    public Participant FindByNume(string nume)
    {
        const string sql = "SELECT p.idParticipant, p.nume, p.CNP, p.capacitate_motor, e.idEchipa, e.nume AS echipaNume " +
                           "FROM participant p " +
                           "JOIN echipa e ON p.idEchipa = e.idEchipa " +
                           "WHERE p.nume = @nume";
        using var connection = DatabaseUtils.getConnection();
        using var command = new SqliteCommand(sql,connection);
        command.Parameters.AddWithValue("@nume", nume);

        connection.Open();
        using var reader = command.ExecuteReader();
        if (reader.Read())
        {
            var echipa = new Echipa(reader.GetInt32(4), reader.GetString(5));
            var participant = new Participant(
                reader.GetInt32(0),
                reader.GetString(1),
                reader.GetString(2),
                reader.GetInt32(3),
                echipa
            );
            return participant;
        }
        return null;
    }
}