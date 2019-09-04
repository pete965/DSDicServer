import javax.net.ServerSocketFactory;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class DictionaryServer implements ActionListener {
    public static int port = 0;
    public static String dic = "";
    public static int numberOfWorkes = 10;
    JFrame myFrame=new JFrame();
    JTextArea jTextArea=new JTextArea();
    JLabel msg=new JLabel("Click Refresh to get the newest status of the dictionary");
    public static void main(String[] args) {

        port = Integer.parseInt(args[0]);
        dic = args[1];
        DictionaryManager dictionaryManager = new DictionaryManager(dic);
//        Returns a copy of the environment's default socket factory.
        ServerSocketFactory factory = ServerSocketFactory.getDefault();
        Executor executor = Executors.newFixedThreadPool(numberOfWorkes);
        DictionaryServer dictionaryServer = new DictionaryServer();
        dictionaryServer.initGui();
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
    public void initGui(){
        myFrame.setSize(1000,700);
        myFrame.setVisible(true);
        myFrame.setTitle("The Best Dictionary");
        myFrame.setLocation(100,100);
        JButton bt1=new JButton("Refresh!");
        JPanel pan=new JPanel();
        JPanel pan1=new JPanel();
        JPanel pan2=new JPanel();
        JPanel pan3=new JPanel();
        pan.setLayout(new BoxLayout(pan,BoxLayout.Y_AXIS));
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pan1.add(msg);
        pan2.add(jTextArea);
        pan3.add(bt1);
        pan.add(pan1);
        pan.add(pan2);
        pan.add(pan3);
        myFrame.add(pan);
        pan1.setLayout(new FlowLayout());
        pan2.setLayout(new FlowLayout());
        bt1.addActionListener(this);
        DictionaryManager dictionaryManager=new DictionaryManager(dic);
        jTextArea.setText(dictionaryManager.refresh());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        DictionaryManager dictionaryManager=new DictionaryManager(dic);
        jTextArea.setText(dictionaryManager.refresh());
    }
}
