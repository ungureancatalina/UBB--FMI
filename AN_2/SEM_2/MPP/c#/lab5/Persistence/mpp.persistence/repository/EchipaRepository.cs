using Microsoft.Data.Sqlite;
using Model.mpp.model;
using System.Collections.Generic;

namespace Persistence.mpp.persistence.repository
{
    public class EchipaRepository : IEchipaRepository
    {
        public void Adauga(Echipa echipa)
        {
            string sql = "INSERT INTO echipa (nume) VALUES (@nume)";
            try
            {
                using var connection = DatabaseUtils.getConnection();
                using var command = new SqliteCommand(sql,connection);
                command.Parameters.AddWithValue("@nume", echipa.Nume);
                command.ExecuteNonQuery();

                command.CommandText = "SELECT last_insert_rowid();";
                echipa.SetId((int)(long)command.ExecuteScalar());
            }
            catch (Exception e)
            {
                Console.WriteLine(e);
            }
        }

        public void Sterge(int id)
        {
            string sql = "DELETE FROM echipa WHERE idEchipa = @id";
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

        public void Modifica(Echipa echipa)
        {
            string sql = "UPDATE echipa SET nume = @nume WHERE idEchipa = @id";
            try
            {
                using var connection = DatabaseUtils.getConnection();
                using var command = new SqliteCommand(sql,connection);
                command.Parameters.AddWithValue("@nume", echipa.Nume);
                command.Parameters.AddWithValue("@id", echipa.GetId());
                command.ExecuteNonQuery();
            }
            catch (Exception e)
            {
                Console.WriteLine(e);
            }
        }

        public Echipa FindById(int id)
        {
            string sql = "SELECT * FROM echipa WHERE idEchipa = @id";
            try
            {
                using var connection = DatabaseUtils.getConnection();
                using var command = new SqliteCommand(sql,connection);
                command.Parameters.AddWithValue("@id", id);
                using var reader = command.ExecuteReader();
                if (reader.Read())
                {
                    return new Echipa(reader.GetInt32(0), reader.GetString(1));
                }
            }
            catch (Exception e)
            {
                Console.WriteLine(e);
            }
            return null;
        }

        public List<Echipa> FindAll()
        {
            var echipeList = new List<Echipa>();
            string sql = "SELECT * FROM echipa";
            try
            {
                using var connection = DatabaseUtils.getConnection();
                using var command = new SqliteCommand(sql,connection);
                using var reader = command.ExecuteReader();
                while (reader.Read())
                {
                    echipeList.Add(new Echipa(reader.GetInt32(0), reader.GetString(1)));
                }
            }
            catch (Exception e)
            {
                Console.WriteLine(e);
            }

            return echipeList;
        }

        public Echipa FindByNume(string nume)
        {
            string sql = "SELECT * FROM echipa WHERE nume = @nume";
            try
            {
                using var connection = DatabaseUtils.getConnection();
                using var command = new SqliteCommand(sql,connection);
                command.Parameters.AddWithValue("@nume", nume);
                using var reader = command.ExecuteReader();
                if (reader.Read())
                {
                    return new Echipa(reader.GetInt32(0), reader.GetString(1));
                }
            }
            catch (Exception e)
            {
                Console.WriteLine(e);
            }

            return null;
        }

        public List<Echipa> FindAllOrderedByNume()
        {
            var echipeList = new List<Echipa>();
            string sql = "SELECT * FROM echipa ORDER BY nume";
            try
            {
                using var connection = DatabaseUtils.getConnection();
                using var command = new SqliteCommand(sql,connection);
                using var reader = command.ExecuteReader();
                while (reader.Read())
                {
                    echipeList.Add(new Echipa(reader.GetInt32(0), reader.GetString(1)));
                }
            }
            catch (Exception e)
            {
                Console.WriteLine(e);            }

            return echipeList;
        }
    }
}
