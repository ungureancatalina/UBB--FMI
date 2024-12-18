using System;
using lab10.Main.domain;
using lab10.Main.service;
using lab10.Main.ui;
using lab10.service;
using Npgsql;
using Repository;
using Repository.database;

namespace lab9.Main
{
    public class Program
    {
        public static void Main(string[] args)
        {
            string connectionString = "Host=localhost;Port=5432;Database=meciuri;Username=postgres;Password=catalina";

            try
            {
                using (var connection = new NpgsqlConnection(connectionString))
                {
                    connection.Open();
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine("Eroare la conexiunea cu baza de date");
                Console.WriteLine($"{ex.Message}");
            }

            IRepository<int, Echipa> repoEchipa = new EchipaRepoDb(connectionString);
            EchipaService srvEchipa = new EchipaService(repoEchipa);

            IRepository<int, Elev> repoElev = new ElevRepoDb(connectionString);
            ElevService srvElev = new ElevService(repoElev);

            IRepository<int, Jucator> repoJucator = new JucatorRepoDb(connectionString, repoElev, repoEchipa);
            JucatorService srvJucator = new JucatorService(repoJucator);

            IRepository<int, JucatorActiv> repoJucatorActiv = new JucatorActivRepoDb(connectionString);
            JucatorActivService srvJucatorActiv = new JucatorActivService(repoJucatorActiv);

            IRepository<int, Meci> repoMeci = new MeciRepoDb(connectionString,repoEchipa);
            MeciService srvMeci = new MeciService(repoMeci);


            Consola ui = new Consola(srvEchipa, srvElev, srvJucator,srvMeci,srvJucatorActiv);
            ui.run();

        }
    }
}
