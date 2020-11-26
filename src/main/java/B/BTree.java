package B;
/**
 * Class Btree
 * Declare attributes and contains all the methods to build the tree
 * @author Diego
 * @version 1.0
 * @since 25/11/2020
 */
class Btree {
    public BTreeNode root;
    public int t;


    Btree(int t) {
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
    /**
     * Method insertBTree
     * Method to insert a key in this tree
     * @author Diego
     * @version 1.0
     * @since 25/11/2020
     */
    public void insertBTree (int k){
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
        b.insertBTree(10);
        b.insertBTree(20);
        b.insertBTree(5);
        b.insertBTree(6);
        b.insertBTree(12);
        b.insertBTree(30);
        b.insertBTree(7);
        b.insertBTree(17);
        System.out.println(b.searchBTree(1897));
        b.traverse();
    }
}

/**
 * Class BtreeNode
 * Declare attributes for Btree
 * @author Diego
 * @version 1.0
 * @since 25/11/2020
 */
class BTreeNode {
    int[] keys; // An array of keys
    int t; // Minimum degree (defines the range for number of keys)
    BTreeNode[] C; // An array of child pointers
    int n; // Current number of keys
    boolean leaf; // Is true when node is leaf. Otherwise false

    /**
     * BtreeNode constructor
     * Declare attributes for Btree
     * @author Diego
     * @version 1.0
     * @since 25/11/2020
     */
    BTreeNode(int t, boolean leaf) {
        this.t = t;
        this.leaf = leaf;
        this.keys = new int[2 * t - 1];
        this.C = new BTreeNode[2 * t];
        this.n = 0;
    }
    /**
     * Method search
     * Method to search a key in this tree
     * @author Diego
     * @version 1.0
     * @since 25/11/2020
     */
    public void traverse() {


        int i = 0;
        for (i = 0; i < this.n; i++) {

            if (!this.leaf) {
                C[i].traverse();
            }
            System.out.print(keys[i] + " ");
        }

        if (!leaf)
            C[i].traverse();
    }
    /**
     * Method search
     * Method to search a key in this tree
     * @return searchNode
     * @author Diego
     * @version 1.0
     * @since 24/11/2020
     */
    BTreeNode search(int k) {
        int i = 0;
        while (i < n && k > keys[i])
            i++;
        if (keys[i] == k)
            return this;
        if (leaf == true)
            return null;
        return C[i].search(k);

    }
    /**
     * Method insertNonFull
     * Method to insert a key in this tree if leaf is not full
     * @author Diego
     * @version 1.0
     * @since 25/11/2020
     */
    void insertNonFull(int k){
        int i=this.n-1;
        if (leaf){
            while (i>=0&&keys[i]>k){
                keys[i+1]=keys[i];
                i--;
            }
            keys[i+1]=k;
            this.n=this.n+1;
        }else{
            while (i>=0&&keys[i]>k)
                i--;
            if (C[i+1].n==2*t-1) {
                splitChild(i+1,C[i+1]);
                if(keys[i+1]<k){
                    i++;
                }
            }
            C[i+1].insertNonFull(k);
        }
    }
    /**
     * Method splitChild
     * Method to split the child
     * @author Diego
     * @version 1.0
     * @since 25/11/2020
     */
    void splitChild(int i, BTreeNode y){
        BTreeNode z=new BTreeNode(y.t,y.leaf);
        z.n=t-1;
        for (int j=0;j<t-1;j++){
            z.keys[j]=y.keys[j+t];
        }if (y.leaf==false){
            for (int j=0;j<t;j++){
                z.C[j]=y.C[j+t];
            }
        }
        y.n=t-1;
        for (int j=n;j>=i+1;j--){
            C[j+1]=C[j];
        }
        C[i+1]=z;
        for (int j = n-1; j >= i; j--) {
            keys[j+1] = keys[j];
        }
        keys[i] = y.keys[t-1];
        n = n + 1;
    }
}


