package Server;

import AVL.AVL;
import AVL.AVLNode;
import BST.BST;
import BST.NodeBST;
import BTree.BTreeNode;
import BTree.Btree;
import Challenge.Challenge;
import Challenge.Token;
import SPLAY.SPLAYTree;
import Socket.SocketServer;
import com.fasterxml.jackson.core.JsonProcessingException;
import guru.nidi.graphviz.attribute.Font;
import guru.nidi.graphviz.attribute.Rank;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.Graph;
import guru.nidi.graphviz.model.MutableGraph;
import guru.nidi.graphviz.model.Node;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import static guru.nidi.graphviz.attribute.Rank.RankDir.TOP_TO_BOTTOM;
import static guru.nidi.graphviz.model.Factory.*;

public class Server {
    private static Logger logger = LogManager.getLogger("Server");
    static BST playerOneTree;
    static BST playerTwoTree;
    static BST playerThreeTree;
    static BST playerfourthTree;
    static int playerOnePoints;
    static int playerTwoPoints;
    static int playerThreePoints;
    static int playerfourthPoints;
    static Token[] tokens= new Token[5];
    static Challenge actualChallenge;
    public static SocketServer serverSocket ;


    public static void main(String[] args) throws IOException {
        serverSocket = new SocketServer();
        System.out.print("asd");
        randomChallenge();
        for (int i = 0; i < 5; i++) {
            tokens[i]= newToken();
            serverSocket.sendMsg(Factory.Serializer(tokens[i]));
            try {
                Thread.sleep(25);
            } catch (InterruptedException e) {
                logger.error("Error waiting for tick"+e);
            }
        }
        serverSocket.sendMsg(Factory.Serializer(actualChallenge));
        boolean running=true;
        while (running){

            if(actualChallenge.timeleft<0){
                randomChallenge();
                System.out.print("\n  ");
            }
            try {
                Thread.sleep(15);
            } catch (InterruptedException e) {
                logger.error("Error waiting for tick"+e);
            }
            actualChallenge.timeleft--;

            for (int i = 0; i < 5; i++) {
                if (tokens[i]==null){
                    tokens[i]= newToken();
                    serverSocket.sendMsg(Factory.Serializer(tokens[i]));
                }
            }
        }
    }


    public static void randomChallenge(){
        Random randomizer= new Random();
        actualChallenge= new Challenge(randomizer.nextInt(5),randomizer.nextInt(4),randomizer.nextInt(3)+3);
    }
    public static Token newToken(){
        Random randomizer= new Random();
        String type="";
        switch(randomizer.nextInt(3)+1){
            case 1:
                type= String.valueOf(BST.class.getName());
                break;
            case 2:
                type= String.valueOf(AVL.class.getName());
                break;
            case 3:
                type= String.valueOf(SPLAYTree.class.getName());
                break;
            case 4:
                type= String.valueOf(Btree.class.getName());
                break;
        }
        Token newToken=new Token();
        newToken.type=type;
        newToken.value=randomizer.nextInt(40);
        return newToken;
    }
    public static void playToken(Token token){
        switch (token.player){
            case 1:
                playerOneTree=checkTree(playerOneTree,token);
                try{
                if (playerOneTree!=null){
                    TestGrafics grafics= new TestGrafics();
                    grafics.playerTree=playerOneTree;
                    grafics.points=playerOnePoints;
                    grafics.player ="playerOneTree";
                    grafics.start();

                }} catch (Exception e) {
                    logger.error("Error calling the grapich function"+e);
                }
                break;
            case 2:
                playerTwoTree=checkTree(playerTwoTree,token);
                try {
                    if (playerTwoTree != null) {
                        TestGrafics grafics= new TestGrafics();
                        grafics.playerTree = playerTwoTree;
                        grafics.points=playerTwoPoints;
                        grafics.player = "playerTwoTree";
                        grafics.start();
                    }
                } catch (Exception e) {
                    logger.error("Error calling the grapich function"+e);
                }
                break;
            case 3:
                playerThreeTree=checkTree(playerThreeTree,token);
                try {
                    if (playerThreeTree != null) {
                        TestGrafics grafics= new TestGrafics();
                        grafics.playerTree = playerThreeTree;
                        grafics.points=playerThreePoints;
                        grafics.player = "playerThreeTree";
                        grafics.start();
                    }
                } catch (Exception e) {
                    logger.error("Error calling the grapich function"+e);
                }
                break;
            case 4:
                playerfourthTree=checkTree(playerfourthTree,token);
                try {
                    if (playerfourthTree != null) {
                        TestGrafics grafics= new TestGrafics();
                        grafics.playerTree = playerfourthTree;
                        grafics.points=playerfourthPoints;
                        grafics.player = "playerFourthTree";
                        grafics.start();
                    }
                } catch (Exception e) {
                    logger.error("Error calling the grapich function"+e);
                }
                break;
        }
    }
    public static BST checkTree(BST playerTree, Token token){
        if (playerTree != null){
            if (playerTree.getClass().getName().equals(token.type)) {
                if(playerTree.getClass()==BST.class){
                    playerTree.insert(token.value);
                }else if(playerTree.getClass()== Btree.class){
                    playerTree.insert(token.value);
                }else if(playerTree.getClass()== AVL.class){
                    playerTree.insertAVL(token.value);
                }else{
                    playerTree.insertSplay(token.value);
                }

            }else{
                playerTree=null;
            }
        }else{
            if(token.type.equals(BST.class.getName())){
                playerTree=new BST();
                playerTree.insert(token.value);
            }else if(token.type.equals(Btree.class.getName())){
                playerTree=new Btree(token.value);
            }else if(token.type.equals(AVL.class.getName())){
                playerTree=new AVL();
                playerTree.insertAVL(token.value);
            }else{
                playerTree=new SPLAYTree(token.value);
            }
        }
        deleteToken(token);

        return playerTree;
    }
    public static void deleteToken(Token token){
        for (int a=0;a<5;a++) {
            if(tokens[a].value==token.value & tokens[a].type.equals(token.type)){
                tokens[a]=newToken();
                try {
                    serverSocket.sendMsg(Factory.Serializer(tokens[a]));
                } catch (JsonProcessingException e) {
                    logger.error("error serilizing a new token"+e);
                }
            }


        }
    }
}
