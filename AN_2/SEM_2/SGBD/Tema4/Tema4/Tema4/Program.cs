using System;
using System.Configuration;
using System.Data.SqlClient;
using System.Threading;

namespace DeadlockDemo
{
    class Program
    {
        static string connectionString = ConfigurationManager.ConnectionStrings["cn"].ConnectionString;

        // Numarul maxim de incercari in caz de deadlock
        static int maxRetries = 3;

        static void Main(string[] args)
        {
            Console.WriteLine("=== DEADLOCK DEMO ===");

            // Pornire fire de executie
            Thread t1 = new Thread(new ThreadStart(Transaction1));
            Thread t2 = new Thread(new ThreadStart(Transaction2));

            // Start thread-uri
            t1.Start();
            t2.Start();

            // Asteapta finalizarea amandurora
            t1.Join();
            t2.Join();

            Console.WriteLine("=== END ===");
        }

        // Thread-ul 1 executa procedura Deadlock1_OrasTara
        static void Transaction1()
        {
            int retries = 0;
            while (!RunTransaction("Deadlock1_OrasTara", "Transaction 1") && retries < maxRetries)
            {
                retries++;
                Console.WriteLine($"[Transaction 1] Retry {retries}/{maxRetries}");
                Thread.Sleep(1000); // Asteapta 1 secunda inainte de retry
            }

            if (retries == maxRetries)
                Console.WriteLine("[Transaction 1] Aborted after max retries.");
        }

        // Thread-ul 2 executa procedura Deadlock2_OrasTara
        static void Transaction2()
        {
            int retries = 0;
            while (!RunTransaction("Deadlock2_OrasTara", "Transaction 2") && retries < maxRetries)
            {
                retries++;
                Console.WriteLine($"[Transaction 2] Retry {retries}/{maxRetries}");
                Thread.Sleep(1000); // Asteapta 1 secunda inainte de retry
            }

            if (retries == maxRetries)
                Console.WriteLine("[Transaction 2] Aborted after max retries.");
        }

        // Executa o procedura stocata si trateaza deadlock
        static bool RunTransaction(string procedureName, string label)
        {
            bool success = false;

            using (SqlConnection connection = new SqlConnection(connectionString))
            {
                try
                {
                    connection.Open();
                    SqlCommand command = new SqlCommand(procedureName, connection)
                    {
                        CommandType = System.Data.CommandType.StoredProcedure
                    };

                    Console.WriteLine($"{label} executing {procedureName}...");
                    command.ExecuteNonQuery();

                    Console.WriteLine($"{label} executed successfully.");
                    success = true;
                }
                catch (SqlException ex)
                {
                    if (ex.Number == 1205)
                    {
                        // Deadlock detectat
                        Console.WriteLine($"{label} DEADLOCK detected: {ex.Message}");
                    }
                    else
                    {
                        // Alta eroare SQL
                        Console.WriteLine($"{label} ERROR: {ex.Message}");
                    }
                }
            }

            return success;
        }
    }
}
