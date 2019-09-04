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
            // Output Stream
            DataOutputStream output = new DataOutputStream(clientSocket.getOutputStream());
            String request = bufferedReader.readLine();
            System.out.println("Request:"+request);
            String[] requestList = request.split(" ");
            if (requestList[0].equals("add")){
                String value = "";
                for(int i=2;i<requestList.length;i++){
                    value+=requestList[i]+" ";
                }
                response = dictionaryManager.add(requestList[1],value);
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