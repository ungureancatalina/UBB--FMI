using lab10.Main.domain;
using Microsoft.Extensions.Logging.Abstractions;
using Npgsql;

namespace Repository.database;

public class EchipaRepoDb : IRepository<int, Echipa>
{
    private string url;

    public EchipaRepoDb(string url)
    {
        this.url = url;
    }
    public async Task<Echipa?> FindOneAsync(int id)
    {
        const string query = "SELECT * FROM echipa WHERE id = @id;";

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
                            Echipa e = new Echipa(nume);
                            e.setId(id);
                            return e;
                        }
                    }
                }
            }
        }
        catch (Exception ex)
        {
            throw new Exception($"Eroare la căutarea echipei cu ID {id}: {ex.Message}");
        }

        return null; 
    }

    public async Task<IEnumerable<Echipa>> FindAllAsync()
    {
        const string query = "SELECT * FROM echipa;";
        var echipe = new List<Echipa>();

        try
        {
            using (var connection = new NpgsqlConnection(url))
            {
                await connection.OpenAsync();

                using (var command = new NpgsqlCommand(query, connection))
                {
                    using (var reader = command.ExecuteReader())
                    {
                        while (reader.Read())
                        {
                            string nume = reader.GetString(reader.GetOrdinal("nume"));
                            int id = reader.GetInt32(reader.GetOrdinal("id"));
                            Echipa e = new Echipa(nume);
                            e.setId(id);
                            echipe.Add(e);
                        }
                    }
                }
            }
        }
        catch (Exception ex)
        {
            throw new Exception($"Eroare la găsirea tuturor echipelor: {ex.Message}");
        }

        return echipe.AsEnumerable();
    }

    public async Task<Echipa?> SaveAsync(Echipa entity)
    {
        const string query = "INSERT INTO echipa (id, nume) VALUES (@id, @nume);";

        try
        {
            using (var connection = new NpgsqlConnection(url))
            {
                await connection.OpenAsync();

                using (var command = new NpgsqlCommand(query, connection))
                {
                    command.Parameters.AddWithValue("id", NpgsqlTypes.NpgsqlDbType.Integer, entity.getId());
                    command.Parameters.AddWithValue("nume", NpgsqlTypes.NpgsqlDbType.Text, entity.nume);
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

    public async Task<Echipa?> DeleteAsync(int id)
    {
        if (await FindOneAsync(id) == null)
        {
            throw new ArgumentException("ID inexistent");
        }

        const string query = "DELETE FROM echipa WHERE id = @id;";

        try
        {
            using (var connection = new NpgsqlConnection(url))
            {
                await connection.OpenAsync();

                using (var command = new NpgsqlCommand(query, connection))
                {
                    command.Parameters.AddWithValue("@id", NpgsqlTypes.NpgsqlDbType.Bigint, id);
                    await command.ExecuteNonQueryAsync();

                }
            }
        }
        catch (Exception ex)
        {
            throw new Exception($"Eroare la ștergerea echipei cu ID {id}: {ex.Message}");
        }

        return null;
    }

    public Task<Echipa?> UpdateAsync(Echipa entity)
    {
        return null;
    }
}