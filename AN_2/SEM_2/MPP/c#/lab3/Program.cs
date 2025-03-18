using System;
using lab3.repository;
using lab3.domain;
using log4net;
using log4net.Config;
using System.IO;
using lab3.utils;

namespace lab3
{
    class Program
    {
        private static readonly ILog log = LogManager.GetLogger(typeof(Program));

        static void Main(string[] args)
        {
            log4net.Config.XmlConfigurator.Configure(new FileInfo("log4net.config"));

            log.Info("Aplicatia a fost pornita.");

            var dbUtils = new DatabaseUtils();

            try
            {
                using (var connection = dbUtils.GetConnection())
                {
                    connection.Open();
                    log.Info("Conexiune deschisa cu succes.");
                }
            }
            catch (Exception ex)
            {
                log.Error("A aparut o eroare la deschiderea conexiunii: " + ex.Message);
            }

            log.Info("Operatiunea s-a incheiat cu succes.");
        }
    }
}