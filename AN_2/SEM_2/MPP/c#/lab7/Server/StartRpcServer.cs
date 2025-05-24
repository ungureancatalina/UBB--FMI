using Microsoft.Extensions.Configuration;
using Model.mpp.model;
using Networking.mpp.networking.utils;
using Persistence.mpp.persistence;
using Persistence.mpp.persistence.repository;
using Server.mpp.server;
using Services.mpp.services;

namespace Server
{
    public class StartRpcServer
    {
        private static int defaultPort = 55555;

        public static void Main(string[] args)
        {
            var config = new ConfigurationBuilder()
                .SetBasePath(Directory.GetCurrentDirectory())
                .AddJsonFile("appsettings.json", optional: true, reloadOnChange: true)
                .Build();

            int serverPort = config.GetSection("Server").GetValue<int>("Port", defaultPort);

            try
            {
                var testConn = DatabaseUtils.getConnection();
                Console.WriteLine("Conexiune DB reușită: " + testConn.DataSource);
            }
            catch (Exception e)
            {
                Console.WriteLine("Eroare conexiune DB: " + e.Message);
            }

            
            IParticipantCursaRepository participantCursaRepo = new ParticipantCursaRepository();
            IParticipantRepository participantRepo = new ParticipantRepository();
            IEchipaRepository echipaRepo = new EchipaRepository();
            ICursaRepository cursaRepo = new CursaRepository();
            IUtilizatorRepository utilizatorRepo = new UtilizatorRepository();
            IServices service = new ServicesImpl(utilizatorRepo, echipaRepo, participantRepo, cursaRepo, participantCursaRepo);
            
            Console.WriteLine("Starting server on port: " + serverPort);

            AbstractServer server = new RpcConcurrentServer(serverPort, service);
            try
            {
                server.Start();
            }
            catch (Exception e)
            {
                Console.Error.WriteLine("Error starting the server: " + e.Message);
            }
            finally
            {
                try
                {
                    server.Stop();
                }
                catch (Exception e)
                {
                    Console.Error.WriteLine("Error stopping the server: " + e.Message);
                }
            }
        }
    }
}