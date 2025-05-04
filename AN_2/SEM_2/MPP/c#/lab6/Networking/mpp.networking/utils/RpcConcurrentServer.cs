using System.Globalization;
using Networking.mpp.networking.jsonprotocol;

namespace Networking.mpp.networking.utils;
using Services.mpp.services;
using Networking.mpp.networking.rpcprotocol;

public class RpcConcurrentServer : AbsConcurrentServer
{
    private readonly IServices _server;

    public RpcConcurrentServer(int port, IServices server) : base(port)
    {
        this._server = server;
        Console.WriteLine("RpcConcurrentServer started.");
    }

    protected override Thread CreateWorker(TcpClient client)
    {
        ClientJsonWorker worker = new ClientJsonWorker(_server, client);
        return new Thread(worker.Run);
    }

    public new void Stop()
    {
        Console.WriteLine("Stopping services...");
        base.Stop();
    }
}