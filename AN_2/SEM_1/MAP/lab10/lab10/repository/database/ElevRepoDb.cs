using lab10.Main.domain;
using Npgsql;

namespace Repository.database;

public class ElevRepoDb : IRepository<int, Elev>
{
    private string url;

    public ElevRepoDb(string url)
    {
        this.url = url;
    }
    public async Task<Elev?> FindOneAsync(int id)
    {
        const string query = "SELECT * FROM elev WHERE id = @id;";
        try
        {
            using (var connection = new NpgsqlConnection(url))
            {
                await connection.OpenAsync();

                using (var command = new NpgsqlCommand(query, connection))
                {
                    command.Parameters.AddWithValue("@id", NpgsqlTypes.NpgsqlDbType.Integer, id);
                    using (var reader = await command.ExecuteReaderAsync())
                    {
                        if (await reader.ReadAsync())
                        {
                            
                            string nume = reader.GetString(reader.GetOrdinal("nume"));
                            string scoala = reader.GetString(reader.GetOrdinal("scoala"));
                            Elev e = new Elev(nume, scoala);
                            e.setId(id);
                            return e;
                        }
                    }
                }
            }
        }
        catch (Exception ex)
        {
            throw new Exception($"Eroare la căutarea elevului cu ID {id}: {ex.Message}");
        }

        return null;
    }

    public async Task<IEnumerable<Elev>> FindAllAsync()
    {
        const string query = "SELECT * FROM elev;";
        var elevi = new List<Elev>();

        try
        {
            using (var connection = new NpgsqlConnection(url))
            {
                await connection.OpenAsync(); 

                using (var command = new NpgsqlCommand(query, connection))
                {
                    using (var reader = await command.ExecuteReaderAsync()) 
                    {
                        while (await reader.ReadAsync()) 
                        {
                            string nume = reader.GetString(reader.GetOrdinal("nume"));
                            string scoala = reader.GetString(reader.GetOrdinal("scoala"));
                            int id = reader.GetInt32(reader.GetOrdinal("id"));

                            Elev e = new Elev(nume, scoala);
                            e.setId(id);

                            elevi.Add(e);
                        }
                    }
                }
            }
        }
        catch (Exception ex)
        {
            throw new Exception($"Eroare la găsirea tuturor elevilor: {ex.Message}");
        }

        return elevi.AsEnumerable();
    }


    public async Task<Elev?> SaveAsync(Elev entity)
    {
        const string query = "INSERT INTO elev (id, nume,scoala) VALUES (@id, @nume,@scoala);";

        try
        {
            using (var connection = new NpgsqlConnection(url))
            {
                await connection.OpenAsync();

                using (var command = new NpgsqlCommand(query, connection))
                {
                    command.Parameters.AddWithValue("id", NpgsqlTypes.NpgsqlDbType.Integer, entity.getId());
                    command.Parameters.AddWithValue("nume", NpgsqlTypes.NpgsqlDbType.Text, entity.getNume());
                    command.Parameters.AddWithValue("scoala", NpgsqlTypes.NpgsqlDbType.Text, entity.getScoala());
                    await command.ExecuteNonQueryAsync();
                }
            }
        }
        catch (Exception ex)
        {
            Console.WriteLine($"Eroare la salvare: {ex.Message}");
        }

        return null;
    }

    public Task<Elev?> DeleteAsync(int id)
    {
        throw new NotImplementedException();
    }

    public Task<Elev?> UpdateAsync(Elev entity)
    {
        throw new NotImplementedException();
    }
    public async Task<Elev?> FindByNumeAndScoalaAsync(string nume, string scoala)
    {
        const string query = "SELECT * FROM elev WHERE nume = @nume AND scoala = @scoala LIMIT 1;";

        try
        {
            using (var connection = new NpgsqlConnection(url))
            {
                await connection.OpenAsync();

                using (var command = new NpgsqlCommand(query, connection))
                {
                    command.Parameters.AddWithValue("@nume", NpgsqlTypes.NpgsqlDbType.Text, nume);
                    command.Parameters.AddWithValue("@scoala", NpgsqlTypes.NpgsqlDbType.Text, scoala);

                    using (var reader = await command.ExecuteReaderAsync())
                    {
                        if (await reader.ReadAsync())
                        {
                            int id = reader.GetInt32(reader.GetOrdinal("id"));
                            string numeElev = reader.GetString(reader.GetOrdinal("nume"));
                            string scoalaElev = reader.GetString(reader.GetOrdinal("scoala"));

                            Elev elev = new Elev(numeElev, scoalaElev);
                            elev.setId(id);
                            return elev;
                        }
                    }
                }
            }
        }
        catch (Exception ex)
        {
            Console.WriteLine($"Eroare la căutarea elevului: {ex.Message}");
            throw;
        }

        return null;
    }

}