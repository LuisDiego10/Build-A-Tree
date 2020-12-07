package Server;

import AVL.AVL;
import BST.BST;
import BST.NodeBST;
import BTree.BTreeNode;
import BTree.Btree;
import Challenge.Challenge;
import Challenge.Token;
import AVL.AVLNode;
import Socket.SocketServer;
import guru.nidi.graphviz.attribute.Records;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.MutableGraph;
import guru.nidi.graphviz.model.MutableNode;
import guru.nidi.graphviz.model.Node;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static guru.nidi.graphviz.attribute.Records.rec;
import static guru.nidi.graphviz.model.Compass.*;
import static guru.nidi.graphviz.model.Factory.*;

import java.io.File;
import java.io.IOException;
import java.util.Random;

public class TestGrafics {
    private static Logger logger = LogManager.getLogger("Server");
    public static Btree playerTree;
    static Token[] tokens = new Token[5];
    static Challenge actualChallenge;
    public static SocketServer serverSocket;

    public static void main(String[] args) throws IOException {
        createTree();
//        Graph g = graph("example1").directed()
//                .graphAttr().with(Rank.dir(TOP_TO_BOTTOM))
//                .nodeAttr().with(Font.name("Arial"))
//                .linkAttr().with("class", "link-class")
//                .with(sortTree(playerTree)
//                );
//        Graphviz.fromGraph(g).height(150).render(Format.PNG).toFile(new File("example/ex1.png"));
        Node nodes[];
        MutableNode node11= mutNode("node11");

        for (int a=0;a<playerTree.root.keys.length;a++){
            BTreeNode root=playerTree.root;
            try {
                node11.applyTo(Records.of(String.valueOf(root.keys[1])));
                node11.add(Records.of(String.valueOf(root.keys[2])));
//                node11.add(Records.of(String.valueOf(root.keys[4])));
            }catch (NullPointerException e){
                e.printStackTrace();
            }

        }
        System.out.print(node11.attrs()+"\n");
        Node node0 = node("node0").with(Records.of(rec("f0", ""), rec("f1", "a"), rec("f2", "b"), rec("f3", "c"), rec("f4", "")));
        Node node1 = node("node1").with(Records.of(rec("asd"), rec("v", "719"), rec("")));
        Node node2 = node("node2").with(Records.of(rec("a1"), rec("805"), rec("p", "")));
        Node node3 = node("node3").with(Records.of(rec("i9"), rec("718"), rec("")));
        Node node4 = node("node4").with(Records.of(rec("e5"), rec("989"), rec("p", "")));
        Node node5 = node("node5").with(Records.of(rec("t2"), rec("v", "959"), rec("")));
        Node node6 = node("node6").with(Records.of(rec("o1"), rec("794"), rec("")));
        Node node7 = node("node7").with(Records.of("s7", "659", ""));
        System.out.print(node0.link(
                between(port("f0"), node1.port("v", SOUTH)),
                between(port("f1"), node11.port(WEST)),
                between(port("f2"), node3.port(WEST)),
                between(port("f3"), node4.port(WEST)),
                between(port("f4"), node5.port("v", NORTH))));
        MutableGraph g = mutGraph("example1").setDirected(true).add(

                        node0.link(
                                between(port("f0"), node1.port("v", SOUTH)),
                                between(port("f1"), node11.port(WEST)),
                                between(port("f2"), node3.port(WEST)),
                                between(port("f3"), node4.port(WEST)),
                                between(port("f4"), node5.port("v", NORTH))));
                g.add(
                        node11.addLink(between(port("p"), node6.port(NORTH_WEST))),
                        node4.link(between(port("p"), node7.port(SOUTH_WEST))));
        try {
            Graphviz.fromGraph(g).width(900).render(Format.PNG).toFile(new File("example/ex3.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static BST createTree(){
        Random randomizer= new Random();
        playerTree= new Btree(2);
        playerTree.insertB(1);
        playerTree.insertB(2);
        playerTree.insertB(3);
        playerTree.insertB(4);
        playerTree.insertB(5);
        playerTree.insertB(6);
        playerTree.insertB(7);

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

    public static Node createNodesB(NodeBST root){
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
}

