package Server;

import AVL.AVL;
import BST.BST;
import BST.NodeBST;
import BTree.BTreeNode;
import BTree.Btree;
import AVL.AVLNode;
import guru.nidi.graphviz.attribute.Font;
import guru.nidi.graphviz.attribute.Rank;
import guru.nidi.graphviz.attribute.Records;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.Graph;
import guru.nidi.graphviz.model.MutableGraph;
import guru.nidi.graphviz.model.MutableNode;
import guru.nidi.graphviz.model.Node;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static guru.nidi.graphviz.attribute.Rank.RankDir.TOP_TO_BOTTOM;
import static guru.nidi.graphviz.attribute.Records.rec;
import static guru.nidi.graphviz.model.Compass.*;
import static guru.nidi.graphviz.model.Factory.*;
import java.io.File;
import java.io.IOException;

public class TestGrafics extends Thread{
    private static Logger logger = LogManager.getLogger("Server");
    public BST playerTree;
    public String player;
    public MutableGraph gr;
    public Graph ga;

    public static void main(String[] args) throws IOException {

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
                between(port("f1"), node2.port(WEST)),
                between(port("f2"), node3.port(WEST)),
                between(port("f3"), node4.port(WEST)),
                between(port("f4"), node5.port("v", NORTH))));
        MutableGraph g = mutGraph("example1").setDirected(true).add(

                        node0.link(
                                between(port("f0"), node1.port("v", SOUTH)),
                                between(port("f1"), node2.port(WEST)),
                                between(port("f2"), node3.port(WEST))),
                        node0.link(
                        between(port("f3"), node4.port(WEST)),
                        between(port("f4"), node5.port("v", NORTH))));

                g.add(
                        node2.link(between(port("p"), node6.port(NORTH_WEST))),
                        node4.link(between(port("p"), node7.port(SOUTH_WEST))));
        try {
            Graphviz.fromGraph(g).width(900).render(Format.PNG).toFile(new File("example/ex3.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        displayTree(playerTree,player);
        System.out.print("player"+playerTree.toString());
    }

    public void displayTree(BST playerTree, String player){
        if(playerTree.getClass()!=Btree.class) {
            ga = graph("example1").directed()
                    .graphAttr().with(Rank.dir(TOP_TO_BOTTOM))
                    .nodeAttr().with(Font.name("Arial"))
                    .linkAttr().with("class", "link-class")
                    .with(sortTree(playerTree)
                    );
            try {
                Graphviz.fromGraph(ga).height(150).render(Format.PNG).toFile(new File("src/main/python/Interface/" + player + ".png"));
            } catch (IOException e) {
                logger.error("error trying to create the tree display of player: "+player+"\n"+e);
            }
        }else{
            displayBtree(playerTree,player);
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


    public void displayBtree(BST playerTree, String player){
        gr = mutGraph("example1").setDirected(true);
        addBTreeDisplay(playerTree);
        try {
            Graphviz.fromGraph(gr).width(900).render(Format.PNG).toFile(new File("src/main/python/Interface/" + player + ".png"));
        } catch (IOException e) {
            logger.error("Error creating the Btree image of player: "+player+"\n error: "+e);
        }

    }

    public static void addBTreeDisplay(BST playertree){
        if(((Btree)playertree).t==2){
            auxAddBTreeDisplay2(((Btree)playertree).root);
        }else if(((Btree)playertree).t==3){
            auxAddBTreeDisplay3(((Btree)playertree).root);
        }
        else if(((Btree)playertree).t==4){
            auxAddBTreeDisplay4(((Btree)playertree).root);
        }
    }
    public static void 

    public static void auxAddBTreeDisplay2(BTreeNode playertree){

    }

    public static void auxAddBTreeDisplay3(BTreeNode playertree){

    }

    public static void auxAddBTreeDisplay4(BTreeNode playertree){

    }

}

