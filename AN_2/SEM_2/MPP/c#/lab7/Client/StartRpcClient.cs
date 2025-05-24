using System.Text.Json;
using Microsoft.Extensions.Configuration;
using System.Windows.Forms;
using Networking.mpp.networking.rpcprotocol;
using Services.mpp.services;
using Client.mpp.client.gui;
using Model.mpp.model;
using Networking.mpp.networking.jsonprotocol;

namespace Client
{
    public class StartRpcClient
    {
        private static int defaultPort = 55555;
        private static string defaultHost = "localhost";

        [STAThread]
        public static void Main(string[] args)
        {
            var config = new ConfigurationBuilder()
                .SetBasePath(Directory.GetCurrentDirectory())
                .AddJsonFile("appsettings.json", optional: true, reloadOnChange: true)
                .Build();

            string serverIP = config.GetSection("Server").GetValue<string>("Host", defaultHost);
            int serverPort = config.GetSection("Server").GetValue<int>("Port", defaultPort);

            IServices server = new ServicesJsonProxy(serverIP, serverPort);

            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);

            LoginForm loginForm = new LoginForm();
            loginForm.SetServer(server);
            
            Application.Run(loginForm);
        }
    }
}