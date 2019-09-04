import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ServeRequest implements Runnable {
    Socket clientSocket = null;
    DictionaryManager dictionaryManager = null;
    public ServeRequest(Socket clientSocket,DictionaryManager dictionaryManager){
        this.clientSocket=clientSocket;
        this.dictionaryManager = dictionaryManager;
    }
    @Override
    public void run() {
        try{
            System.out.println("Begin to handle request!");
            String response = "";
            // Input stream
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            System.out.println("here");
            // Output Stream
            DataOutputStream output = new DataOutputStream(clientSocket.getOutputStream());
            System.out.println("there");
            String request = bufferedReader.readLine();
            System.out.println("Request:"+request);
            String[] requestList = request.split(" ");
            if (requestList[0].equals("add")){
                response = dictionaryManager.add(requestList[1],requestList[2]);
            }else if (requestList[0].equals(("query"))){
                response = dictionaryManager.query(requestList[1]);
            }else{
                response = dictionaryManager.remove(requestList[1]);
            }
            System.out.println("Response is:"+response);
            output.writeUTF(response);
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}