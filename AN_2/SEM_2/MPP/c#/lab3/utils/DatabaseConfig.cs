namespace lab3.utils;

public class DatabaseConfig
{
    public static string GetConnectionString()
    {
        try
        {
            string configFilePath = Path.Combine(AppDomain.CurrentDomain.BaseDirectory, "bd.config");
            if (!File.Exists(configFilePath))
            {
                throw new FileNotFoundException("Fișierul bd.config nu a fost găsit.", configFilePath);
            }

            XDocument xmlDoc = XDocument.Load(configFilePath);
            XElement? connectionStringElement = xmlDoc.Root?.Element("connectionString");

            if (connectionStringElement == null || string.IsNullOrWhiteSpace(connectionStringElement.Value))
            {
                throw new Exception("Elementul connectionString nu a fost găsit sau este gol în bd.config.");
            }

            return connectionStringElement.Value.Trim();
        }
        catch (Exception ex)
        {
            Console.WriteLine("Eroare la citirea fișierului de configurare: " + ex.Message);
            throw;
        }
    }
}