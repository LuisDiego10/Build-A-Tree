package Server;

import AVL.AVL;
import BST.BST;
import BST.NodeBST;
import BTree.Btree;
import Challenge.Challenge;
import Challenge.Token;
import AVL.AVLNode;
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
                .with(sortTree(playerTree)
                );
        Graphviz.fromGraph(g).height(150).render(Format.PNG).toFile(new File("example/ex1.png"));
    }

    public static BST createTree(){
        Random randomizer= new Random();
        playerTree= new SPLAYTree(1);
        playerTree.insertSplay(randomizer.nextInt(10));
        playerTree.insertSplay(randomizer.nextInt(10));
        playerTree.insertSplay(randomizer.nextInt(10));
        playerTree.insertSplay(randomizer.nextInt(10));
        playerTree.insertSplay(randomizer.nextInt(10));
        playerTree.insertSplay(randomizer.nextInt(10));
        playerTree.insertSplay(randomizer.nextInt(10));
        return playerTree;
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
}

