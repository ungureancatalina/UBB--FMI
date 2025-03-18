namespace lab3.utils;
using Microsoft.Data.Sqlite;
using System;

public class DatabaseUtils
{
    private readonly string _connectionString;
    public DatabaseUtils()
    {
        _connectionString = DatabaseConfig.GetConnectionString();
    }

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