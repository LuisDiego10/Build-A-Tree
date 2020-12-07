package BTree;

/**
 * Class BtreeNode
 * Declare attributes for Btree
 * @author Diego
 * @version 1.0
 * @since 25/11/2020
 */
public class BTreeNode {
    public int[] keys; // An array of keys
    public int t; // Minimum degree (defines the range for number of keys)
    public BTreeNode[] C; // An array of child pointers
    public int n; // Current number of keys
    public boolean leaf; // Is true when node is leaf. Otherwise false

    /**
     * BtreeNode constructor
     * Declare attributes for Btree
     * @author Diego
     * @version 1.0
     * @since 25/11/2020
     */
    public BTreeNode(int t, boolean leaf) {
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
        }if (!y.leaf){
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
