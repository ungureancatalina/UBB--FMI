import ro.mpp2024.network.utils.AbstractServer;
import ro.mpp2024.network.utils.ServerException;
import ro.mpp2024.network.utils.RpcConcurrentServer;
import ro.mpp2024.persistence.*;
import ro.mpp2024.persistence.respository.*;
import ro.mpp2024.server.ServicesImpl;
import ro.mpp2024.services.IServices;

import java.io.IOException;
import java.util.Properties;

public class StartRpcServer {
    private static int defaultPort=55555;
    public static void main(String[] args) {

        Properties serverProps = new Properties();
        try {
            serverProps.load(StartRpcServer.class.getResourceAsStream("/server.properties"));
            System.out.println("Server properties set. ");
            serverProps.list(System.out);
        } catch (IOException e) {
            System.err.println("Cannot find server.properties "+e);
            return;
        }
        ParticipantCursaRepositoryInterface participantCursaRepositoryInterface = new ParticipantCursaRepository(serverProps);
        ParticipantRepositoryInterface participantRepositoryInterface = new ParticipantRepository(serverProps);
        EchipaRepositoryInterface echipaRepositoryInterface = new EchipaRepository(serverProps);
        CursaRepositoryInterface cursaRepositoryInterface = new CursaRepository(serverProps);
        UtilizatorRepositoryInterface utilizatorRepositoryInterface = new UtilizatorRepository(serverProps);
        IServices ServerImpl = new ServicesImpl(utilizatorRepositoryInterface,echipaRepositoryInterface,participantRepositoryInterface,cursaRepositoryInterface,participantCursaRepositoryInterface);
        int ServerPort=defaultPort;
        try {
            ServerPort = Integer.parseInt(serverProps.getProperty("server.port"));
        } catch (NumberFormatException nef){
            System.err.println("Wrong  Port Number"+nef.getMessage());
            System.err.println("Using default port "+defaultPort);
        }
        System.out.println("Starting server on port: " + ServerPort);
        AbstractServer server = new RpcConcurrentServer(ServerPort, ServerImpl);
        try {
            server.start();
        } catch (ServerException e) {
            System.err.println("Error starting the server" + e.getMessage());
        }finally {
            try {
                server.stop();
            }catch(ServerException e){
                System.err.println("Error stopping server "+e.getMessage());
            }
        }
    }
}