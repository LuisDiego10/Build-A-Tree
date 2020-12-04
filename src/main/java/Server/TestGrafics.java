package Server;

import AVL.AVL;
import BST.BST;
import BST.NodeBST;
import Challenge.Challenge;
import Challenge.Token;
import SPLAY.SPLAYTree;
import Socket.SocketServer;
import guru.nidi.graphviz.attribute.Font;
import guru.nidi.graphviz.attribute.Rank;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.Graph;
import guru.nidi.graphviz.model.Node;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static guru.nidi.graphviz.attribute.Rank.RankDir.LEFT_TO_RIGHT;
import static guru.nidi.graphviz.attribute.Rank.RankDir.TOP_TO_BOTTOM;
import static guru.nidi.graphviz.model.Factory.*;

import java.io.File;
import java.io.IOException;
import java.util.Random;

public class TestGrafics {
    private static Logger logger = LogManager.getLogger("Server");
    public static BST playerTree;
    static Token[] tokens = new Token[5];
    static Challenge actualChallenge;
    public static SocketServer serverSocket;

    public static void main(String[] args) throws IOException {
        createTree();
        Graph g = graph("example1").directed()
                .graphAttr().with(Rank.dir(TOP_TO_BOTTOM))
                .nodeAttr().with(Font.name("Arial"))
                .linkAttr().with("class", "link-class")
                .with(createNodes()
                );
        Graphviz.fromGraph(g).height(150).render(Format.PNG).toFile(new File("example/ex1.png"));
    }

    public static BST createTree(){
        Random randomizer= new Random();
        playerTree= new BST();
        playerTree.insert(randomizer.nextInt(10));
        playerTree.insert(randomizer.nextInt(10));
        playerTree.insert(randomizer.nextInt(10));
        playerTree.insert(randomizer.nextInt(10));
        playerTree.insert(randomizer.nextInt(10));
        playerTree.insert(randomizer.nextInt(10));
        playerTree.insert(randomizer.nextInt(10));
        return playerTree;
    }

    public static Node createNodes(){

        if (playerTree.root == null){
            return node("");
        }
        if (playerTree.root.left==null &playerTree.root.right==null ){
            return node(String.valueOf(( playerTree.root.key)));
        }

        if (playerTree.root.left==null &playerTree.root.right!=null ){
            return node(String.valueOf(playerTree.root.key)).link(auxCreateNodes(playerTree.root.right));
        }

        if (playerTree.root.left!=null & playerTree.root.right==null ){
            return node(String.valueOf(playerTree.root.key)).link(auxCreateNodes(playerTree.root.left));
        }

        return node(String.valueOf(playerTree.root.key)).link(auxCreateNodes(playerTree.root.left)).link(auxCreateNodes(playerTree.root.right));
    }
    public static Node auxCreateNodes(NodeBST root){
        if (root == null){
            return node("");
        }
        if (root.left==null &root.right==null ){
            return node(String.valueOf(root.key));
        }

        if (root.left==null &root.right!=null ){
            return node(String.valueOf(root.key)).link(auxCreateNodes(root.right));
        }

        if (root.left!=null & root.right==null ){
            return node(String.valueOf(root.key)).link(auxCreateNodes(root.left));
        }

        return node(String.valueOf(root.key)).link(auxCreateNodes(root.left)).link(auxCreateNodes(root.right));
    }
}

