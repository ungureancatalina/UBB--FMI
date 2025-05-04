using Microsoft.Extensions.Configuration;

namespace Persistence.mpp.persistence.repository;
using Microsoft.Data.Sqlite;
public static class DatabaseUtils
{
    private static readonly IConfigurationRoot Configuration;

    static DatabaseUtils()
    {
        Configuration = new ConfigurationBuilder()
            .SetBasePath(Directory.GetCurrentDirectory())
            .AddJsonFile("appsettings.json")
            .Build();
    }

    public static SqliteConnection getConnection()
    {
        string connectionString = Configuration.GetConnectionString("Default");

        if (string.IsNullOrWhiteSpace(connectionString))
        {
            Console.WriteLine("Eroare: Nu există string de conexiune configurat!");
            return null;
        }

        var connection = new SqliteConnection(connectionString);
        connection.Open();
        return connection;
    }
}
