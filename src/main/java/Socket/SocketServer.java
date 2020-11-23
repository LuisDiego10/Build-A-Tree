package Socket;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;


public class SocketServer extends Thread {
    /**
     * Socket logger
     */
    private static Logger logger = LogManager.getLogger("SocketServer");
    ;
    static ObjectMapper mapp = new ObjectMapper();
    public Socket ClientSocket;
    public ServerSocket socketServer;
    private DataOutputStream out;
    private DataInputStream in;

    public static void main(String[] args) throws IOException {
        SocketServer x;
        x = new SocketServer();


    }

    public SocketServer() {
        try {
            socketServer = new ServerSocket(996);
            ClientSocket = socketServer.accept();
            out = new DataOutputStream(ClientSocket.getOutputStream());
            in = new DataInputStream(ClientSocket.getInputStream());
        } catch (IOException e) {
            logger.error("error creating the socket" + e);
        }
        this.start();
        this.sendMsg("hiClient");
    }

    /**
     * Start the lister of client socket as a new Thread.
     * recieve all the msg sent from server and send it to client.
     */
    public void run() {
        DataInputStream input = in;
        while (true) {
            try {
                int size;
                System.out.print("reading msg"+"\n");
                size = Byte.valueOf(input.readByte()).intValue();
                System.out.print("reading msg"+"\n");
                int i=0;
                byte[] msg= new byte[size];
                while(size>i){
                    msg[i]= input.readByte();
                    i++;
                }
                String msga= new String(msg, StandardCharsets.UTF_8);
                System.out.print(msga+"\n");

            } catch (IOException e) {
                logger.error("error recieving msg"+e);
            }
        }

    }


    public void sendMsg(String msg){
        try {
            out.writeUTF(msg);
            System.out.print(msg+"\n");
        } catch (IOException e) {
            logger.error("error sending msg");
        }
    }
}

