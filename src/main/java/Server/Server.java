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
        randomChallenge(false);
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
                randomChallenge(true);
                serverSocket.sendMsg(Factory.Serializer(actualChallenge));
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
    public static void randomChallenge(boolean flag){
        if(flag){
            BST[] trees={playerOneTree,playerTwoTree,playerThreeTree,playerfourthTree};
            int[] puntajes={playerOnePoints,playerTwoPoints,playerThreePoints,playerfourthPoints};
            int goal=0;
            for(int x=0;x<3;x++){
                if (trees[x]!=null&&trees[x+1]!=null){
                    if (trees[x].getClass()==Btree.class){
                        if(trees[x+1].getClass()==Btree.class) {
                            if (((Btree) trees[x + 1]).root.n >= ((Btree) trees[x]).root.n) {
                                goal = x + 1;
                            }
                        }
                        else if(trees[x+1].getClass()==AVL.class){
                            if (((AVL)trees[x+1]).root.height >= ((Btree)trees[x]).root.n) {
                                goal=x+1;
                            }
                        }
                        else{
                            if ((trees[x+1].maxDepth(trees[x+1].root) >= ((Btree)trees[x]).root.n)) {
                                goal=x+1;
                            }
                        }
                    }
                    else if(trees[x].getClass()==AVL.class){
                        if(trees[x+1].getClass()==Btree.class) {
                            if (((Btree) trees[x + 1]).root.n >= ((AVL) trees[x]).root.height) {
                                goal = x + 1;
                            }
                        }
                        else if(trees[x+1].getClass()==AVL.class){
                            if (((AVL)trees[x+1]).root.height >= ((AVL)trees[x]).root.height) {
                                goal=x+1;
                            }
                        }
                        else{
                            if ((trees[x+1].maxDepth(trees[x+1].root) >= ((AVL)trees[x]).root.height)) {
                                goal=x+1;
                            }
                        }
                    }
                    else {
                        if(trees[x+1].getClass()==Btree.class) {
                            if (((Btree) trees[x + 1]).root.n >= (trees[x].maxDepth(trees[x].root))) {
                                goal = x + 1;
                            }
                        }
                        else if(trees[x+1].getClass()==AVL.class){
                            if (((AVL)trees[x+1]).root.height >= (trees[x].maxDepth(trees[x].root))) {
                                goal=x+1;
                            }
                        }
                        else{
                            if ((trees[x+1].maxDepth(trees[x+1].root) >= (trees[x].maxDepth(trees[x].root)))) {
                                goal=x+1;
                            }
                        }
                    }
                }
            }
            if(goal==0){
                playerOnePoints+=actualChallenge.reward;
            }else if (goal==1){
                playerTwoPoints+=actualChallenge.reward;
            }else if(goal==2){
                playerThreePoints+=actualChallenge.reward;
            }else{
                playerfourthPoints+=actualChallenge.reward;
            }
        }
        Random randomizer= new Random();
        actualChallenge= new Challenge(randomizer.nextInt(5),randomizer.nextInt(4),randomizer.nextInt(3)+3);
    }
    public static Token newToken(){
        Random randomizer= new Random();
        String type="";
        switch(randomizer.nextInt(4)+1){
            case 1:
                type= String.valueOf(BST.class.getSimpleName());
                break;
            case 2:
                type= String.valueOf(AVL.class.getSimpleName());
                break;
            case 3:
                type= String.valueOf(SPLAYTree.class.getSimpleName());
                break;
            case 4:
                type= String.valueOf(Btree.class.getSimpleName());
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
                    if (playerOneTree.getClass()==Btree.class){
                        if (actualChallenge.deep == ((Btree)playerOneTree).root.n & !((Btree)playerOneTree).root.leaf ) {
                            playerOnePoints = +actualChallenge.reward;
                            randomChallenge(false);

                        }
                    }else if(playerOneTree.getClass()==AVL.class){
                        if (actualChallenge.deep == ((AVL)playerOneTree).root.height) {
                            playerOnePoints = +actualChallenge.reward;
                            randomChallenge(false);
                        }
                    }else {
                        if (actualChallenge.deep == playerOneTree.maxDepth(playerOneTree.root)) {
                            playerOnePoints = +actualChallenge.reward;
                            randomChallenge(false);
                        }
                    }
                    TestGrafics grafics= new TestGrafics();
                    grafics.playerTree=playerOneTree;
                    grafics.points=playerOnePoints;
                    grafics.player ="playerOneTree";
                    grafics.start();
                    }

                } catch (Exception e) {
                    logger.error("Error calling the grapich function"+e);
                }
                break;
            case 2:
                playerTwoTree=checkTree(playerTwoTree,token);
                try {
                    if (playerTwoTree != null) {
                        if (playerTwoTree.getClass()==Btree.class){
                            if (actualChallenge.deep == ((Btree)playerTwoTree).root.n & !((Btree)playerTwoTree).root.leaf ) {
                                playerTwoPoints = +actualChallenge.reward;
                                randomChallenge(false);
                            }
                        }else if(playerThreeTree.getClass()==AVL.class){
                            if (actualChallenge.deep == ((AVL)playerTwoTree).root.height) {
                                playerTwoPoints = +actualChallenge.reward;
                                randomChallenge(false);
                            }
                        }else {
                            if (actualChallenge.deep == playerTwoTree.maxDepth(playerTwoTree.root)) {
                                playerTwoPoints = +actualChallenge.reward;
                                randomChallenge(false);
                            }
                        }
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
                        if (playerThreeTree.getClass()==Btree.class){
                            if (actualChallenge.deep == ((Btree)playerThreeTree).root.n & !((Btree)playerThreeTree).root.leaf ) {
                                playerThreePoints = +actualChallenge.reward;
                                randomChallenge(false);
                            }
                        }else if(playerThreeTree.getClass()==AVL.class){
                            if (actualChallenge.deep == ((AVL)playerThreeTree).root.height) {
                                playerThreePoints = +actualChallenge.reward;
                                randomChallenge(false);
                            }
                        }else {

                            if (actualChallenge.deep == playerThreeTree.maxDepth(playerThreeTree.root)) {
                                playerThreePoints = +actualChallenge.reward;
                                randomChallenge(false);
                            }
                        }
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
                        if (playerfourthTree.getClass()==Btree.class){
                            if (actualChallenge.deep == ((Btree)playerfourthTree).root.n & !((Btree)playerfourthTree).root.leaf ) {
                                playerfourthPoints = +actualChallenge.reward;
                                randomChallenge(false);
                            }
                        }else if(playerfourthTree.getClass()==AVL.class){
                            if (actualChallenge.deep == ((AVL)playerfourthTree).root.height) {
                                playerfourthPoints = +actualChallenge.reward;
                                randomChallenge(false);
                            }
                        }else {
                            if (actualChallenge.deep == playerfourthTree.maxDepth(playerfourthTree.root)) {
                                playerfourthPoints = +actualChallenge.reward;
                                randomChallenge(false);
                            }
                        }
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
            if (playerTree.getClass().getSimpleName().equals(token.type)) {
                if(playerTree.getClass()==BST.class){
                    playerTree.insert(token.value);

                }else if(playerTree.getClass()== Btree.class){
                    playerTree.insertB(token.value);
                }else if(playerTree.getClass()== AVL.class){
                    playerTree.insertAVL(token.value);
                }else{
                    playerTree.insertSplay(token.value);

                }

            }else{
                playerTree=null;
            }
        }else{
            if(token.type.equals(BST.class.getSimpleName())){
                playerTree=new BST();
                playerTree.insert(token.value);
            }else if(token.type.equals(Btree.class.getSimpleName())){
                playerTree=new Btree(2);
                playerTree.insertB(token.value);
            }else if(token.type.equals(AVL.class.getSimpleName())){
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
