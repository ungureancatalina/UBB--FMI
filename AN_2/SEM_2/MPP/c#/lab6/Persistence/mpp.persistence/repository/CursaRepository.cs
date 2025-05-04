using Model.mpp.model;
using Microsoft.Data.Sqlite;
using System.Data;

namespace Persistence.mpp.persistence.repository
{
    public class CursaRepository : ICursaRepository
    {
        public void Adauga(Cursa cursa)
        {
            string sql = "INSERT INTO cursa (nume, capacitate_minima, capacitate_maxima) VALUES (@nume, @capacitate_minima, @capacitate_maxima)";
            try
            {
                using var connection = DatabaseUtils.getConnection();
                using var command = new SqliteCommand(sql,connection);
                command.Parameters.AddWithValue("@nume", cursa.Nume);
                command.Parameters.AddWithValue("@capacitate_minima", cursa.CapacitateMinima);
                command.Parameters.AddWithValue("@capacitate_maxima", cursa.CapacitateMaxima);

                command.ExecuteNonQuery();
                command.CommandText = "SELECT last_insert_rowid();";
                cursa.SetId((int)(long)command.ExecuteScalar());
            }
            catch (Exception e)
            {
               Console.WriteLine(e);
            }
        }

        public void Sterge(int id)
        {
            string sql = "DELETE FROM cursa WHERE idCursa = @id";
            try
            {
                using var connection = DatabaseUtils.getConnection();
                using var command = new SqliteCommand(sql,connection);
                command.Parameters.AddWithValue("@id", id);
                command.ExecuteNonQuery();
            }
            catch (Exception e)
            {
                Console.WriteLine(e);
            }
        }

        public void Modifica(Cursa cursa)
        {
            string sql = "UPDATE cursa SET nume = @nume, capacitate_minima = @capacitate_minima, capacitate_maxima = @capacitate_maxima WHERE idCursa = @id";
            try
            {
                using var connection = DatabaseUtils.getConnection();
                using var command = new SqliteCommand(sql,connection);
                command.Parameters.AddWithValue("@nume", cursa.Nume);
                command.Parameters.AddWithValue("@capacitate_minima", cursa.CapacitateMinima);
                command.Parameters.AddWithValue("@capacitate_maxima", cursa.CapacitateMaxima);
                command.Parameters.AddWithValue("@id", cursa.GetId());
                command.ExecuteNonQuery();
            }
            catch (Exception e)
            {
                Console.WriteLine(e);
            }
        }

        public Cursa FindById(int id)
        {
            string sql = "SELECT * FROM cursa WHERE idCursa = @id";
            try
            {
                using var connection = DatabaseUtils.getConnection();
                using var command = new SqliteCommand(sql,connection);
                command.Parameters.AddWithValue("@id", id);

                using var reader = command.ExecuteReader();
                if (reader.Read())
                {
                    var cursa = new Cursa(
                        reader.GetInt32(reader.GetOrdinal("idCursa")),
                        reader.GetString(reader.GetOrdinal("nume")),
                        reader.GetInt32(reader.GetOrdinal("capacitate_minima")),
                        reader.GetInt32(reader.GetOrdinal("capacitate_maxima"))
                    );
                    return cursa;
                }
            }
            catch (Exception e)
            {
                Console.WriteLine(e);
            }
            return null;
        }

        public List<Cursa> FindAll()
        {
       
            List<Cursa> cursaList = new();
            string sql = "SELECT * FROM cursa";
            try
            {
                using var connection = DatabaseUtils.getConnection();
                using var command = new SqliteCommand(sql,connection);
                using var reader = command.ExecuteReader();

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
            catch (Exception e)
            {
                Console.WriteLine(e);
            }

            return cursaList;
        }

        public List<Cursa> FindByCapacitateMinima(int capacitate)
        {
            List<Cursa> cursaList = new();
            string sql = "SELECT * FROM cursa WHERE capacitate_minima >= @capacitate";
            try
            {
                using var connection = DatabaseUtils.getConnection();
                using var command = new SqliteCommand(sql,connection);
                command.Parameters.AddWithValue("@capacitate", capacitate);
                using var reader = command.ExecuteReader();

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
            catch (Exception e)
            {
                Console.WriteLine(e);            }

            return cursaList;
        }
    }
}
