using log4net;

namespace lab4.utils;
using Microsoft.Data.Sqlite;
using System;

public class DatabaseUtils
{
    private static readonly ILog Logger = LogManager.GetLogger(typeof(DatabaseUtils));
    private readonly string _connectionString = DatabaseConfig.GetConnectionString();

    public SqliteConnection GetConnection()
    {
        try
        {
            var connection = new SqliteConnection(_connectionString);
            return connection;
        }
        catch (Exception ex)
        {
            Console.WriteLine("Error creating SQLite connection: " + ex.Message);
            throw;
        }
    }

}