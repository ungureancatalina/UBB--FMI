using lab10.Main.domain;
using Npgsql;
using Repository;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Repository.database;
public class MeciRepoDb : IRepository<int, Meci>
{
    private string url;
    private IRepository<int, Echipa> repoEchipa;

    public MeciRepoDb(string url, IRepository<int, Echipa> repoEchipa)
    {
        this.url = url;
        this.repoEchipa = repoEchipa;
    }

    public async Task<Meci?> FindOneAsync(int id)
    {
        const string query = "SELECT * FROM meci WHERE id = @id;";

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
                            int echipa1_id = reader.GetInt32(reader.GetOrdinal("echipa1_id"));
                            int echipa2_id = reader.GetInt32(reader.GetOrdinal("echipa2_id"));
                            DateTime data = reader.GetDateTime(reader.GetOrdinal("data"));

                            var echipa1 = await repoEchipa.FindOneAsync(echipa1_id);
                            var echipa2 = await repoEchipa.FindOneAsync(echipa2_id);

                            if (echipa1 != null && echipa2 != null)
                            {
                                return new Meci(echipa1, echipa2, data);
                            }
                        }
                    }
                }
            }
        }
        catch (Exception ex)
        {
            Console.WriteLine($"Error: {ex.Message}");
        }

        return null;
    }

    public async Task<IEnumerable<Meci>> FindAllAsync()
    {
        const string query = "SELECT * FROM meci;";
        var meciuri = new List<Meci>();

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
                            int echipa1_id = reader.GetInt32(reader.GetOrdinal("echipa1_id"));
                            int echipa2_id = reader.GetInt32(reader.GetOrdinal("echipa2_id"));
                            DateTime data = reader.GetDateTime(reader.GetOrdinal("data"));

                            var echipa1 = await repoEchipa.FindOneAsync(echipa1_id);
                            var echipa2 = await repoEchipa.FindOneAsync(echipa2_id);

                            if (echipa1 != null && echipa2 != null)
                            {
                                meciuri.Add(new Meci(echipa1, echipa2, data));
                            }
                        }
                    }
                }
            }
        }
        catch (Exception ex)
        {
            Console.WriteLine($"Error: {ex.Message}");
        }

        return meciuri;
    }

    public async Task<Meci> SaveAsync(Meci entity)
    {
        const string query = "INSERT INTO meci (echipa1_id, echipa2_id, data) VALUES (@echipa1_id, @echipa2_id, @data) RETURNING id;";

        try
        {
            using (var connection = new NpgsqlConnection(url))
            {
                await connection.OpenAsync();

                using (var command = new NpgsqlCommand(query, connection))
                {
                    command.Parameters.AddWithValue("@echipa1_id", entity.Echipa1.getId());
                    command.Parameters.AddWithValue("@echipa2_id", entity.Echipa2.getId());
                    command.Parameters.AddWithValue("@data", entity.Data);

                    var result = await command.ExecuteScalarAsync();
                    if (result != null)
                    {
                        int id = Convert.ToInt32(result);
                        return new Meci(entity.Echipa1, entity.Echipa2, entity.Data) { Id = id };
                    }
                }
            }
        }
        catch (Exception ex)
        {
            Console.WriteLine($"Error: {ex.Message}");
        }

        return null;
    }

    public async Task<Meci?> DeleteAsync(int id)
    {
        throw new NotImplementedException();
    }

    public async Task<Meci?> UpdateAsync(Meci entity)
    {
        throw new NotImplementedException();
    }
}