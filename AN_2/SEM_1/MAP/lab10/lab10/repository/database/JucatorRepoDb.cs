using lab10.Main.domain;
using lab10.Main.service;
using Npgsql;
using Exception = System.Exception;

namespace Repository.database;

public class JucatorRepoDb : IRepository<int, Jucator>
{
    private string url;
    private IRepository<int, Elev> repoElev;
    private IRepository<int, Echipa> repoEchipa;


    public JucatorRepoDb(string url, IRepository<int, Elev> repoElev, IRepository<int, Echipa> repoEchipa)
    {
        this.url = url;
        this.repoElev = repoElev;
        this.repoEchipa = repoEchipa;
    }
    public async Task<Jucator?> FindOneAsync(int id)
    {
        const string query = "SELECT * FROM jucator WHERE id = @id;";

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
                            int elevid = reader.GetInt32(reader.GetOrdinal("elevid"));
                            int echipaid = reader.GetInt32(reader.GetOrdinal("echipaid"));
                            var elev = await repoElev.FindOneAsync(elevid);
                            var echipa = await repoEchipa.FindOneAsync(echipaid);

                            if (elev != null && echipa != null)
                            {
                                Jucator j = new Jucator(elev, echipa);
                                j.setId(id);
                                return j;
                            }
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

    public async Task<IEnumerable<Jucator>> FindAllAsync()
    {
        const string query = "SELECT * FROM jucator;";

        var jucatori = new List<Jucator>();

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

                            int elevid = reader.GetInt32(reader.GetOrdinal("elevid"));
                            int echipaid = reader.GetInt32(reader.GetOrdinal("echipaid"));
                            int id = reader.GetInt32(reader.GetOrdinal("id"));


                            var elev = await repoElev.FindOneAsync(elevid);
                            var echipa = await repoEchipa.FindOneAsync(echipaid);
                            Jucator j = new Jucator(elev, echipa);
                            j.setId(id);
                            jucatori.Add(j);
                        }
                    }
                }
            }
        }
        catch (Exception ex)
        {
            throw new Exception($"Eroare la găsirea tuturor jucatorilor: {ex.Message}");
        }

        return jucatori.AsEnumerable();
    }




    public async Task<Jucator?> SaveAsync(Jucator entity)
    {
        const string query = "INSERT INTO jucator (id, elevid, echipaid) VALUES (@id, @elevid, @echipaid);";
        try
        {
            using (var connection = new NpgsqlConnection(url))
            {
                await connection.OpenAsync();

                using (var command = new NpgsqlCommand(query, connection))
                {
                    command.Parameters.AddWithValue("id", NpgsqlTypes.NpgsqlDbType.Integer, entity.getId());

                    command.Parameters.AddWithValue("elevid", NpgsqlTypes.NpgsqlDbType.Integer, entity.getElev().getId());
                    command.Parameters.AddWithValue("echipaid", NpgsqlTypes.NpgsqlDbType.Integer, entity.getEchipa().getId());
                    await command.ExecuteNonQueryAsync();

                }
            }
        }
        catch (Exception ex)
        {
            Console.WriteLine($"Eroare la salvare: {ex.Message}");
            throw;
        }

        return null; 
    }

    public async Task<Jucator?> FindByNameAsync(string numeElev)
    {
        try
        {
            Elev elev = null;
            const string queryElev = "SELECT * FROM elev WHERE nume = @nume;";

            using (var connection = new NpgsqlConnection(url))
            {
                await connection.OpenAsync();

                using (var command = new NpgsqlCommand(queryElev, connection))
                {
                    command.Parameters.AddWithValue("@nume", numeElev);

                    using (var reader = await command.ExecuteReaderAsync())
                    {
                        if (await reader.ReadAsync())
                        {
                            int elevId = reader.GetInt32(reader.GetOrdinal("id"));
                            string elevNume = reader.GetString(reader.GetOrdinal("nume"));
                            string scoala = reader.GetString(reader.GetOrdinal("scoala"));
                            elev = new Elev(elevNume, scoala)
                            {
                                Id = elevId
                            };
                        }
                    }
                }
            }

            if (elev == null)
            {
                Console.WriteLine($"Elevul cu numele '{numeElev}' nu a fost găsit.");
                return null;
            }

            const string queryJucator = "SELECT * FROM jucator WHERE elevid = @elevid;";
            using (var connection = new NpgsqlConnection(url))
            {
                await connection.OpenAsync();

                using (var command = new NpgsqlCommand(queryJucator, connection))
                {
                    command.Parameters.AddWithValue("@elevid", elev.Id);

                    using (var reader = await command.ExecuteReaderAsync())
                    {
                        if (await reader.ReadAsync())
                        {
                            int echipaId = reader.GetInt32(reader.GetOrdinal("echipaid"));
                            int jucatorId = reader.GetInt32(reader.GetOrdinal("id"));
                            var echipa = await repoEchipa.FindOneAsync(echipaId);
                            var jucator = new Jucator(elev, echipa)
                            {
                                Id = jucatorId
                            };
                            return jucator;
                        }
                    }
                }
            }
        }
        catch (Exception ex)
        {
            Console.WriteLine($"Eroare la căutarea jucătorului cu numele '{numeElev}': {ex.Message}");
        }

        return null;
    }

    public Task<Jucator?> DeleteAsync(int id)
    {
        throw new NotImplementedException();
    }

    public Task<Jucator?> UpdateAsync(Jucator entity)
    {
        throw new NotImplementedException();
    }
}