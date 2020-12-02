package Socket;
import Challenge.Token;
import Server.Server;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;


public class SocketServer extends Thread {
    /**
     * Socket logger
     */
    private static Logger logger = LogManager.getLogger("SocketServer");
    public Socket ClientSocket;
    public ServerSocket socketServer;
    private DataOutputStream out;
    private DataInputStream in;
    private boolean run=true;
    static ObjectMapper mapp = new ObjectMapper();


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
    }

    /**
     * Start the lister of client socket as a new Thread.
     * recieve all the msg sent from server and send it to client.
     */
    public void run() {
        DataInputStream input = in;
        while (run) {
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
                Token token = null;
                System.out.print(msga+"\n");
                try {
                    token = mapp.readValue(msga, Token.class);
                } catch (JsonProcessingException e) {
                    logger.error("error creating the token from server"+e);
                }
                Server.playToken(token);


            } catch (IOException e) {
                logger.error("error recieving msg"+e);
                try {
                    this.socketServer.close();
                } catch (IOException ex) {
                    logger.error("error closing socket"+e);

                }
                Server.serverSocket= new SocketServer();
                this.run=false;
//                finish();
//                System.exit(-2);
            }
        }

    }

    public void finish(){
        run=false;
    }

    public void sendMsg(String msg){
        try {
            out.writeUTF(msg);
            System.out.print("msg:"+msg+"\n");
        } catch (IOException e) {
            logger.error("error sending msg");
        }
    }
}

