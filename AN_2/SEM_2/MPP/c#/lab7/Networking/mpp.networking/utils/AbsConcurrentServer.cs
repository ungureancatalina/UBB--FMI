namespace Networking.mpp.networking.utils;

public abstract class AbsConcurrentServer : AbstractServer
{
    protected AbsConcurrentServer(int port) : base(port)
    {
        Console.WriteLine("Concurrent AbstractServer");
    }

    protected override void ProcessRequest(TcpClient client)
    {
        Thread workerThread = CreateWorker(client);
        workerThread.Start();
    }

    protected abstract Thread CreateWorker(TcpClient client);
    public virtual void Stop()
    {
    }
}