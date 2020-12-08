package Server;

import AVL.AVL;
import AVL.AVLNode;
import BST.BST;
import BST.NodeBST;
import BTree.BTreeNode;
import BTree.Btree;
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

import java.io.File;
import java.io.IOException;

import static guru.nidi.graphviz.attribute.Rank.RankDir.TOP_TO_BOTTOM;
import static guru.nidi.graphviz.attribute.Records.rec;
import static guru.nidi.graphviz.model.Compass.NORTH;
import static guru.nidi.graphviz.model.Factory.*;

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
        b.insert(8);
        b.insert(12);
        b.insert(30);
        b.insert(7);
        b.insert(17);

        TestGrafics g= new TestGrafics();
        g.playerTree=b;
        g.player="isriom";
        g.points=0;
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
        gr.add(node(playerTree.getClass().getSimpleName()),node(String.valueOf((points)+" points")));
        try {
            Graphviz.fromGraph(gr).width(900).render(Format.PNG).toFile(new File("src/main/python/Interface/" + player + ".png"));
        } catch (IOException e) {
            logger.error("Error creating the Btree image of player: "+player+"\n error: "+e);
        }

    }

    public void addBTreeDisplay(BST playertree){
        if (((Btree) playertree).t == 2) {
            createBNode1(((Btree) playertree).root, 0);

        } else if (((Btree) playertree).t == 3) {
            createBNode2(((Btree) playertree).root, 0);
        } else if (((Btree) playertree).t == 4) {
            createBNode3(((Btree) playertree).root, 0);
        }
    }

    public Node createBNode1(BTreeNode node, int num) {
        String a = "";
        String c = "";
        String b = "";
        try {
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
        Node node0 = node("node" + num).with(Records.of(rec("f0", ""), rec("f1", a),
                rec("f2", ""), rec("f3", b), rec("f4", ""), rec("f5", c),
                rec("f6", "")));
        gr.add(node0);
        if(node.leaf){
            return node0;
        }
        if(node.C[0]!=null) {
            Node node1 = createBNode1(node.C[0], num + 1);
            gr.add(node1);
            gr.add(node0.link(between(port("f0"), node1.port(NORTH))));
        }
        if(node.C[1]!=null) {
            Node node1 = createBNode1(node.C[1], num + 150);
            gr.add(node1);
            gr.add(node0.link(between(port("f2"), node1.port(NORTH))));
        }
        if(node.C[2]!=null) {
            Node node1 = createBNode1(node.C[2], num + 300);
            gr.add(node1);
            gr.add(node0.link(between(port("f4"), node1.port(NORTH))));
        }
        if (node.C[3] != null) {
            Node node1 = createBNode1(node.C[3], num + 450);
            gr.add(node1);
            gr.add(node0.link(between(port("f6"), node1.port(NORTH))));
        }

        return node0;

    }

    public Node createBNode2(BTreeNode node, int num) {
        String a = "";
        String b = "";
        String c = "";
        String d = "";
        String e = "";
        try {
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
                case 4:
                    a = String.valueOf(node.keys[0]);
                    b = String.valueOf(node.keys[1]);
                    c = String.valueOf(node.keys[2]);
                    e = String.valueOf(node.keys[3]);
                    break;
                case 5:
                    a = String.valueOf(node.keys[0]);
                    b = String.valueOf(node.keys[1]);
                    c = String.valueOf(node.keys[2]);
                    e = String.valueOf(node.keys[3]);
                    d = String.valueOf(node.keys[4]);
                    break;
            }
        } catch (Exception m) {
            logger.warn("keys not full or unknown error:" + m);
        }
        Node node0 = node("node" + num).with(Records.of(
                rec("f0", ""), rec("f1", a), rec("f2", ""), rec("f3", b),
                rec("f4", ""), rec("f5", c), rec("f6", ""), rec("f7", d),
                rec("f8", ""), rec("f9", e), rec("f10", "")));
        gr.add(node0);
        if (node.leaf) {
            return node0;
        }
        if (node.C[0] != null) {
            Node node1 = createBNode2(node.C[0], num + 1);
            gr.add(node1);
            gr.add(node0.link(between(port("f0"), node1.port(NORTH))));
        }
        if (node.C[1] != null) {
            Node node1 = createBNode2(node.C[1], num + 150);
            gr.add(node1);
            gr.add(node0.link(between(port("f2"), node1.port(NORTH))));
        }
        if (node.C[2] != null) {
            Node node1 = createBNode2(node.C[2], num + 300);
            gr.add(node1);
            gr.add(node0.link(between(port("f4"), node1.port(NORTH))));
        }
        if (node.C[3] != null) {
            Node node1 = createBNode2(node.C[3], num + 450);
            gr.add(node1);
            gr.add(node0.link(between(port("f6"), node1.port(NORTH))));
        }
        if (node.C[4] != null) {
            Node node1 = createBNode2(node.C[4], num + 600);
            gr.add(node1);
            gr.add(node0.link(between(port("f8"), node1.port(NORTH))));
        }
        if (node.C[5] != null) {
            Node node1 = createBNode2(node.C[5], num + 750);
            gr.add(node1);
            gr.add(node0.link(between(port("f10"), node1.port(NORTH))));
        }

        return node0;

    }

    public Node createBNode3(BTreeNode node, int num) {
        String a = "";
        String b = "";
        String c = "";
        String d = "";
        String e = "";
        String f = "";
        String g = "";
        try {
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
                case 4:
                    a = String.valueOf(node.keys[0]);
                    b = String.valueOf(node.keys[1]);
                    c = String.valueOf(node.keys[2]);
                    e = String.valueOf(node.keys[3]);
                    break;
                case 5:
                    a = String.valueOf(node.keys[0]);
                    b = String.valueOf(node.keys[1]);
                    c = String.valueOf(node.keys[2]);
                    e = String.valueOf(node.keys[3]);
                    d = String.valueOf(node.keys[4]);
                    break;
                case 6:
                    a = String.valueOf(node.keys[0]);
                    b = String.valueOf(node.keys[1]);
                    c = String.valueOf(node.keys[2]);
                    e = String.valueOf(node.keys[3]);
                    d = String.valueOf(node.keys[4]);
                    f = String.valueOf(node.keys[4]);
                    break;
                case 7:
                    a = String.valueOf(node.keys[0]);
                    b = String.valueOf(node.keys[1]);
                    c = String.valueOf(node.keys[2]);
                    e = String.valueOf(node.keys[3]);
                    d = String.valueOf(node.keys[4]);
                    f = String.valueOf(node.keys[4]);
                    g = String.valueOf(node.keys[4]);
                    break;
            }
        } catch (Exception m) {
            logger.warn("keys not full or unknown error:" + m);
        }
        Node node0 = node("node" + num).with(Records.of(
                rec("f0", ""), rec("f1", a), rec("f2", ""), rec("f3", b),
                rec("f4", ""), rec("f5", c), rec("f6", ""), rec("f7", d),
                rec("f8", ""), rec("f9", e), rec("f10", ""), rec("f11", f)
                , rec("f12", ""), rec("f13", g), rec("f14", "")));
        gr.add(node0);
        if (node.leaf) {
            return node0;
        }
        if (node.C[0] != null) {
            Node node1 = createBNode3(node.C[0], num + 1);
            gr.add(node1);
            gr.add(node0.link(between(port("f0"), node1.port(NORTH))));
        }
        if (node.C[1] != null) {
            Node node1 = createBNode3(node.C[1], num + 150);
            gr.add(node1);
            gr.add(node0.link(between(port("f2"), node1.port(NORTH))));
        }
        if (node.C[2] != null) {
            Node node1 = createBNode3(node.C[2], num + 300);
            gr.add(node1);
            gr.add(node0.link(between(port("f4"), node1.port(NORTH))));
        }
        if (node.C[3] != null) {
            Node node1 = createBNode3(node.C[3], num + 450);
            gr.add(node1);
            gr.add(node0.link(between(port("f6"), node1.port(NORTH))));
        }
        if (node.C[4] != null) {
            Node node1 = createBNode3(node.C[4], num + 600);
            gr.add(node1);
            gr.add(node0.link(between(port("f8"), node1.port(NORTH))));
        }
        if (node.C[5] != null) {
            Node node1 = createBNode3(node.C[5], num + 750);
            gr.add(node1);
            gr.add(node0.link(between(port("f10"), node1.port(NORTH))));
        }
        if (node.C[6] != null) {
            Node node1 = createBNode3(node.C[6], num + 900);
            gr.add(node1);
            gr.add(node0.link(between(port("f12"), node1.port(NORTH))));
        }
        if (node.C[7] != null) {
            Node node1 = createBNode3(node.C[7], num + 1050);
            gr.add(node1);
            gr.add(node0.link(between(port("f14"), node1.port(NORTH))));
        }

        return node0;

    }
}

