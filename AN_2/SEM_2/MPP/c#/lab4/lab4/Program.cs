
using System.Data;
using lab4.domain;
using log4net;
using lab4.service;
using lab4.utils;

namespace lab4
{
    public class Program
    {
        private static readonly ILog Log = LogManager.GetLogger(typeof(Program));

        [STAThread]
        private static void Main()
        {
            log4net.Config.XmlConfigurator.Configure(new FileInfo("log4net.config"));

            Log.Info("Aplicatia a fost pornita.");

            var dbUtils = new DatabaseUtils();

            String connection = null;  

            try
            {
                IDbConnection connectionc = dbUtils.GetConnection();
                connection = DatabaseConfig.GetConnectionString();
                connectionc.Open();
                Log.Info("Conexiune deschisa cu succes.");
            }
            catch (Exception ex)
            {
                Log.Error("A aparut o eroare la deschiderea conexiunii: " + ex.Message);
            }

            if (connection != null)
            {
                Log.Info("Operatiunea s-a incheiat cu succes.");
                
                IUtilizatorRepository utilizatorRepo = new UtilizatorRepository(connection);
                IParticipantRepository participantRepo = new ParticipantRepository(connection);
                IParticipantCursaRepository participantCursaRepo = new ParticipantCursaRepository(connection);
                ICursaRepository cursaRepo = new CursaRepository(connection);
                IEchipaRepository echipaRepo = new EchipaRepository(connection);
                Validare validare = new Validare(participantRepo,echipaRepo);
                
                var utilizatorService = new UtilizatorService(utilizatorRepo,validare);
                var participantCursaService = new ParticipantCursaService(participantCursaRepo);
                var cursaService = new CursaService(cursaRepo, participantCursaService);
                var echipaService = new EchipaService(echipaRepo);
                var participantService = new ParticipantService(participantRepo,echipaRepo,validare);

                Application.EnableVisualStyles();
                Application.SetCompatibleTextRenderingDefault(false);
                Application.Run(new LoginForm(utilizatorService, echipaService, participantService, participantCursaService, cursaService));
            }
            else
            {
                Log.Error("Nu s-a putut stabili conexiunea la baza de date.");
            }
        }
    }
}