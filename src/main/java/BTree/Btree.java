package BTree;

import BST.BST;

/**
 * Class Btree
 * Declare attributes and contains all the methods to build the tree
 * @author Diego
 * @version 1.0
 * @since 25/11/2020
 */
public class Btree extends BST {
    public BTreeNode root;
    public int t;


    public Btree(int t) {
        this.root = null;
        this.t = t;
    }
    /**
     * Method traverse
     * Method to traverse the tree
     * @author Diego
     * @version 1.0
     * @since 25/11/2020
     */
    public void traverse() {
        if (this.root != null)
            this.root.traverse();
        System.out.println();
    }
    /**
     * Method search
     * Method to search a key in this tree
     * @author Diego
     * @version 1.0
     * @since 25/11/2020
     */
    public BTreeNode searchBTree(int k) {
        if (this.root == null)
            return null;
        else
            return this.root.search(k);
    }

    @Override
    public void insertB(int item) {
        insert(item);
    }

    /**
     * Method insert
     * Method to insert a key in this tree
     * @author Diego
     * @version 1.0
     * @since 25/11/2020
     */
    public void insert (int k){
        if (this.root==null){
            this.root=new BTreeNode(t,true);
            this.root.keys[0]=k;
            this.root.n=1;
        }else{
            if(this.root.n==2*t-1){
                BTreeNode s =new BTreeNode(t,false);
                s.C[0]=this.root;
                s.splitChild(0,this.root);
                int i=0;
                if (s.keys[0]<k)
                    i++;
                s.C[i].insertNonFull(k);
                this.root=s;
            }
            else{
                root.insertNonFull(k);
            }
        }
    }
    public static void main(String[] args) {
        Btree b = new Btree(3);
        b.insert(10);
        b.insert(20);
        b.insert(5);
        b.insert(6);
        b.insert(12);
        b.insert(30);
        b.insert(7);
        b.insert(17);
        System.out.println(b.searchBTree(1897));
        b.traverse();
    }
}


