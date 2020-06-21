package WebSocket.NewsFeed;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.glassfish.tyrus.server.Server;

public class StartUp 
{
    public static void main( String[] args )
    {
        System.out.println( "Starting server" );
        runServer();
    }
    
    public static void runServer() {
        Server server = new Server("localhost", 8080, "/websocket", WebSocketServer.class);
        try {
            server.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Press any key to stop the server.");
            reader.readLine();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            server.stop();
        }
    }
}
