namespace Networking.mpp.networking.utils;

public abstract class AbstractServer
{
    private readonly int _port;
    private TcpListener? _server=null;

    protected AbstractServer(int port)
    {
        _port = port;
    }

    public void Start()
    {
        try
        {
            _server = new TcpListener(IPAddress.Any, _port);
            _server.Start();

            while (true)
            {
                Console.WriteLine("Waiting for clients ...");
                TcpClient client = _server.AcceptTcpClient();
                Console.WriteLine("Client connected ...");
                ProcessRequest(client);
            }
        }
        catch (Exception e)
        {
            throw new Exception("Starting server error", e);
        }
        finally
        {
            Stop();
        }
    }

    protected abstract void ProcessRequest(TcpClient client);

    public void Stop()
    {
        try
        {
            _server.Stop();
        }
        catch (Exception e)
        {
            throw new Exception("Closing server error", e);
        }
    }
}