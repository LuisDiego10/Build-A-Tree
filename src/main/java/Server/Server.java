package Server;

import AVL.*;
import BST.*;
import BTree.*;
import Challenge.Challenge;
import Challenge.Token;
import SPLAY.SPLAYTree;
import Socket.SocketServer;
import com.fasterxml.jackson.core.JsonProcessingException;
import guru.nidi.graphviz.attribute.Font;
import guru.nidi.graphviz.attribute.Rank;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import guru.nidi.graphviz.attribute.Records;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static guru.nidi.graphviz.attribute.Rank.RankDir.TOP_TO_BOTTOM;
import static guru.nidi.graphviz.attribute.Records.rec;
import static guru.nidi.graphviz.model.Compass.*;
import static guru.nidi.graphviz.model.Factory.*;

import java.io.File;
import java.io.IOException;
import java.util.Random;


import java.util.Random;

import java.io.IOException;

import static guru.nidi.graphviz.model.Factory.node;

public class Server {
    private static Logger logger = LogManager.getLogger("Server");
    static BST playerOneTree;
    static BST playerTwoTree;
    static BST playerThreeTree;
    static BST playerfourthTree;
    static Token[] tokens= new Token[5];
    static Challenge actualChallenge;
    public static SocketServer serverSocket ;
    public static MutableGraph gr;
    public static Graph ga;


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
                if (playerOneTree!=null){
                    displayTree(playerOneTree,"playerOneTree");
                }
                break;
            case 2:
                playerTwoTree=checkTree(playerTwoTree,token);
                if (playerTwoTree!=null){
                    displayTree(playerTwoTree,"playerTwoTree");
                }
                break;
            case 3:
                playerThreeTree=checkTree(playerThreeTree,token);
                if (playerThreeTree!=null){
                    displayTree(playerThreeTree,"playerThreeTree");
                }
                break;
            case 4:
                playerfourthTree=checkTree(playerfourthTree,token);
                if (playerfourthTree!=null){
                    displayTree(playerfourthTree,"playerfourthTree");
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


    public static void displayTree(BST playerTree,String player){
        if(playerTree.getClass()!=Btree.class) {
            ga = graph("example1").directed()
                    .graphAttr().with(Rank.dir(TOP_TO_BOTTOM))
                    .nodeAttr().with(Font.name("Arial"))
                    .linkAttr().with("class", "link-class")
                    .with(sortTree(playerTree)
                    );
            try {
                Graphviz.fromGraph(ga).height(150).render(Format.PNG).toFile(new File("src/main/python/Interface" + player + ".png"));
            } catch (IOException e) {
                logger.error("error trying to create the tree display of player: "+player+"\n"+e);
            }
        }else{

        }
    }
    public static Node sortTree(BST playerTree){
        if (playerTree.getClass()==AVL.class){
            return createNodesAVL(((AVL)playerTree).root);
        }
        if (playerTree.getClass()== Btree.class){
            ;
        }
        return createNodes(playerTree.root);
    }
    public static Node createNodes(NodeBST root){
        if (root == null){
            return node("");
        }
        if (root.left==null &root.right==null ){
            return node(String.valueOf(root.key));
        }

        if (root.left==null &root.right!=null ){
            return node(String.valueOf(root.key)).link(createNodes(root.right));
        }

        if (root.left!=null & root.right==null ){
            return node(String.valueOf(root.key)).link(createNodes(root.left));
        }

        return node(String.valueOf(root.key)).link(createNodes(root.left)).link(createNodes(root.right));
    }
    public static Node createNodesAVL(AVLNode root){
        if (root == null){
            return node("");
        }
        if (root.left==null &root.right==null ){
            return node(String.valueOf(root.key));
        }

        if (root.left==null &root.right!=null ){
            return node(String.valueOf(root.key)).link(createNodesAVL(root.right));
        }

        if (root.left!=null & root.right==null ){
            return node(String.valueOf(root.key)).link(createNodesAVL(root.left));
        }

        return node(String.valueOf(root.key)).link(createNodesAVL(root.left)).link(createNodesAVL(root.right));
    }
    public static void displayBtree(Btree playerTree,String player){
        gr = mutGraph("example1").setDirected(true);
            addBTreeDisplay(playerTree);
        try {
            Graphviz.fromGraph(gr).width(900).render(Format.PNG).toFile(new File("src/main/python/Interface" + player + ".png"));
        } catch (IOException e) {
            logger.error("Error creating the Btree image of player: "+player+"\n error: "+e);
        }

    }
    public static void addBTreeDisplay(Btree playertree){
        if(playertree.t==2){
            auxAddBTreeDisplay2(playertree.root);
        }else if(playertree.t==3){
            auxAddBTreeDisplay3(playertree.root);
        }
        else if(playertree.t==4){
            auxAddBTreeDisplay4(playertree.root);
        }
    }
    public static void auxAddBTreeDisplay2(BTreeNode playertree){

    }
    public static void auxAddBTreeDisplay3(BTreeNode playertree){

    }
    public static void auxAddBTreeDisplay4(BTreeNode playertree){

    }
}
