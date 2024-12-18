using lab10.Main.domain;
using Npgsql;
using Repository;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

public class JucatorActivRepoDb : IRepository<int, JucatorActiv>
{
    private string url;

    public JucatorActivRepoDb(string url)
    {
        this.url = url;
    }

    public async Task<JucatorActiv?> FindOneAsync(int id)
    {
        const string query = "SELECT * FROM jucator_activ WHERE id = @id;";

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
                            var jucatorId = reader.GetInt32(reader.GetOrdinal("id_jucator"));
                            var meciId = reader.GetInt32(reader.GetOrdinal("id_meci"));
                            var puncte = reader.GetInt32(reader.GetOrdinal("nr_puncte_inscrise"));
                            var tip = reader.GetString(reader.GetOrdinal("tip"));

                            var tipJucator = Enum.TryParse<TipJucator>(tip, out var parsedTip) ? parsedTip : TipJucator.Rezerva;

                            var jucatorActiv = new JucatorActiv(jucatorId, meciId, puncte, tipJucator)
                            {
                                Id = id
                            };

                            return jucatorActiv;
                        }
                    }
                }
            }
        }
        catch (Exception ex)
        {
            throw new Exception($"Eroare la căutarea JucatorActiv cu ID {id}: {ex.Message}");
        }

        return null;
    }

    public async Task<IEnumerable<JucatorActiv>> FindAllAsync()
    {
        const string query = "SELECT * FROM jucator_activ;";

        var jucatoriActiv = new List<JucatorActiv>();

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
                            var id = reader.GetInt32(reader.GetOrdinal("id"));
                            var jucatorId = reader.GetInt32(reader.GetOrdinal("id_jucator"));
                            var meciId = reader.GetInt32(reader.GetOrdinal("id_meci"));
                            var puncte = reader.GetInt32(reader.GetOrdinal("nr_puncte_inscrise"));
                            var tip = reader.GetString(reader.GetOrdinal("tip"));

                            var tipJucator = Enum.TryParse<TipJucator>(tip, out var parsedTip) ? parsedTip : TipJucator.Rezerva;

                            var jucatorActiv = new JucatorActiv(jucatorId, meciId, puncte, tipJucator)
                            {
                                Id = id
                            };
                            jucatoriActiv.Add(jucatorActiv);
                        }
                    }
                }
            }
        }
        catch (Exception ex)
        {
            throw new Exception($"Eroare la găsirea tuturor JucatorActiv: {ex.Message}");
        }

        return jucatoriActiv;
    }

    public async Task<JucatorActiv?> SaveAsync(JucatorActiv entity)
    {
        const string query = "INSERT INTO jucator_activ (id_jucator, id_meci, nr_puncte_inscrise, tip) VALUES (@id_jucator, @id_meci, @nr_puncte_inscrise, @tip) RETURNING id;";

        try
        {
            using (var connection = new NpgsqlConnection(url))
            {
                await connection.OpenAsync();

                using (var command = new NpgsqlCommand(query, connection))
                {
                    command.Parameters.AddWithValue("id_jucator", NpgsqlTypes.NpgsqlDbType.Integer, entity.idJucator);
                    command.Parameters.AddWithValue("id_meci", NpgsqlTypes.NpgsqlDbType.Integer, entity.idMeci);
                    command.Parameters.AddWithValue("nr_puncte_inscrise", NpgsqlTypes.NpgsqlDbType.Integer, entity.nrPuncteInscrise);
                    command.Parameters.AddWithValue("tip", NpgsqlTypes.NpgsqlDbType.Varchar, entity.tip.ToString());

                    var newId = await command.ExecuteScalarAsync(); 
                    entity.setId(Convert.ToInt32(newId));
                }
            }
        }
        catch (Exception ex)
        {
            Console.WriteLine($"Eroare la salvare JucatorActiv: {ex.Message}");
            throw;
        }

        return null;
    }

    public async Task<JucatorActiv?> DeleteAsync(int id)
    {
        throw new NotImplementedException();
    }

    public async Task<JucatorActiv?> UpdateAsync(JucatorActiv entity)
    {
        throw new NotImplementedException();
    }
}
