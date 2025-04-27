package ro.mpp2024.network.utils;

import ro.mpp2024.network.rpcprotocol.ClientRpcReflectionWorker;
import ro.mpp2024.services.IServices;

import java.net.Socket;

public class RpcConcurrentServer extends AbsConcurrentServer {
    private IServices Server;
    public RpcConcurrentServer(int port, IServices transportServer) {
        super(port);
        this.Server = transportServer;
        System.out.println(" RpcConcurrentServer");
    }

    @Override
    protected Thread createWorker(Socket client) {
        ClientRpcReflectionWorker worker = new ClientRpcReflectionWorker(Server, client);

        Thread tw = new Thread(worker);
        return tw;
    }

    @Override
    public void stop(){
        System.out.println("Stopping services ...");
    }
}