import javax.net.ServerSocketFactory;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class DictionaryServer {
    public static int port = 0;
    public static String dic = "";
    public static int numberOfWorkes = 10;
    public static void main(String[] args) {
        port = Integer.parseInt(args[0]);
        dic = args[1];
        DictionaryManager dictionaryManager = new DictionaryManager(dic);
//        Returns a copy of the environment's default socket factory.
        ServerSocketFactory factory = ServerSocketFactory.getDefault();
        Executor executor = Executors.newFixedThreadPool(numberOfWorkes);
        System.out.println("Server Starts!");
        try{
//            Returns an unbound server socket. The socket is configured with the socket options (such as accept timeout) given to this factory.
            ServerSocket server = factory.createServerSocket(port);
            while(true){
//                Listens for a connection to be made to this socket and accepts it.
                Socket clientSocket = server.accept();
                System.out.println("Request Received");
                executor.execute(new ServeRequest(clientSocket,dictionaryManager));
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
