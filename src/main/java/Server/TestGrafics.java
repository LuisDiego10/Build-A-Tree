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
    public int points;
    public MutableGraph gr;
    public Graph ga;

    public static void main(String[] args) throws IOException {
        Btree b = new Btree(2);
        b.insert(10);
        b.insert(20);
        b.insert(5);
        b.insert(6);
        b.insert(12);
        b.insert(30);
        b.insert(7);
        b.insert(17);
        TestGrafics g= new TestGrafics();
        g.playerTree=b;
        g.player="isriom";
        g.displayTree(b,"isriom");



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
                    .with(sortTree(playerTree),node(playerTree.getClass().getSimpleName()),node(String.valueOf((points)+" points"))
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

    public void addBTreeDisplay(BST playertree){
        if(((Btree)playertree).t==2){
            createBNode(((Btree)playertree).root);

        }else if(((Btree)playertree).t==3){
            auxAddBTreeDisplay3(((Btree)playertree).root);
        }
        else if(((Btree)playertree).t==4){
            auxAddBTreeDisplay4(((Btree)playertree).root);
        }
    }
    public Node createBNode(BTreeNode node){
        String a="";
        String c="";
        String b="";
        try{
            switch (node.n) {
                case 0:
                    break;
                case 1:
                    a = String.valueOf(node.keys[0]);
                    break;
                case 2:
                    a = String.valueOf(node.keys[0]);
                    b = String.valueOf(node.keys[1]);
                    break;
                case 3:
                    a = String.valueOf(node.keys[0]);
                    b = String.valueOf(node.keys[1]);
                    c = String.valueOf(node.keys[2]);
                    break;
            }
        } catch (Exception e) {
            logger.warn("keys not full or unknown error:"+e);
        }
        Node node0 = node("node0").with(Records.of(rec("f0", ""), rec("f1", a),
                rec("f2", ""), rec("f3", b), rec("f4", ""),rec("f5", c),
                rec("f6", "")));
        gr.add(node0);
        if(node.leaf){
            return node0;
        }
        if(node.C[0]!=null){
            gr.add(node0.link(between(port("f0"), createBNode(node.C[0]).port(NORTH))));
        }
        if(node.C[1]!=null){
            gr.add(node0.link(between(port("f2"), createBNode(node.C[1]).port(NORTH))));
        }
        if(node.C[2]!=null){
            gr.add(node0.link(between(port("f4"), createBNode(node.C[2]).port(NORTH))));
        }
        if(node.C[3]!=null){
            gr.add(node0.link(between(port("f6"), createBNode(node.C[3]).port(NORTH))));
        }

        return node0;

    }

    public static void auxAddBTreeDisplay2(BTreeNode playertree){

    }

    public static void auxAddBTreeDisplay3(BTreeNode playertree){

    }

    public static void auxAddBTreeDisplay4(BTreeNode playertree){

    }

}

