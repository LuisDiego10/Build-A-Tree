package AVL;

import BST.BST;

/**
 *Class AVL TREE
 * Declare node root and contains all the methods to build the tree
 * @author Diego
 * @version 1.0
 * @since 22/11/2020
 */

public class AVL extends BST {
    public AVLNode root;
    //Heigh of tree
    int height(AVLNode node){
        if (node==null){
            return 0;
        }
        return node.height;
    }
    /**
     * Method getBalance
     * Method for calculate the roll factor
     * @param node
     * @return roll factor;
     * @author Diego
     * @version 1.0
     * @since 22/11/2020
     */
    int getBalance(AVLNode node){
        if (node==null){
            return 0;
        }
        return height(node.left)-height(node.right);
    }
    /**
     * Function for calcule max
     * @param a and b
     * @return max;
     * @author Diego
     * @version 1.0
     * @since 22/11/2020
     */

    int max (int a, int b){
        return Math.max(a,b);
    }
    /**
     * Method rightRotate
     * Method for execute right rotation
     * @param y
     * @return Tree rotation;
     * @author Diego
     * @version 1.0
     * @since 22/11/2020
     */
    //Rotation Right
    AVLNode rightRotate(AVLNode y){
        AVLNode x=y.left;
        AVLNode T2=x.right;
        x.right=y;
        y.left=T2;

        y.height=max(height(y.left),height(y.right))+1;
        x.height=max(height(x.left),height(x.right))+1;
        return x;
    }
    /**
     * Method leftRotate
     * Method for execute left rotation
     * @param x
     * @return Tree rotation;
     * @author Diego
     * @version 1.0
     * @since 22/11/2020
     */
    //Rotation Left
    AVLNode leftRotate(AVLNode x){
        AVLNode y=x.right;
        AVLNode T2=y.left;
        y.left=x;
        x.right=T2;
        x.height=max(height(x.left),height(x.right))+1;
        y.height=max(height(y.left),height(y.right))+1;
        return y;
    }

    public AVLNode insertAVL(int key){
        return root=insert(root,key);
    }

    /**
     * Method insert
     * Method for execute right rotation
     * @param node and key
     * @return The insertion and balance Tree;
     * @author Diego
     * @version 1.0
     * @since 22/11/2020
     */
    public AVLNode insert(AVLNode node, int key){
        if (node==null){
            return new AVLNode(key);
        }
        if (key< node.key)
            node.left=insert(node.left,key);
        else if (key> node.key)
            node.right=insert(node.right,key);
        else
            return node;
        node.height=1+max(height(node.left),height(node.right));
        int balance=getBalance(node);
        //Cases of imbalance
        //Left-Left
        if (balance>1&&key<node.left.key){
          return rightRotate(node);
        }
        //Right-Right
        if (balance<-1&&key>node.right.key){
            return leftRotate(node);
        }
        //Left-Right
        if (balance>1&&key>node.left.key) {
            node.left=leftRotate(node.left);
            return rightRotate(node);
        }
        //Right-Left
        if (balance<-1&&key<node.right.key){
            node.right=rightRotate(node.right);
            return leftRotate(node);
        }
        return node;
    }
    /**
     * Main Class
     * Contain the instance for run Tree
     * @author Diego
     * @version 1.0
     * @since 22/11/2020
     */
    public static void main (String args[]) {
        AVL tree = new AVL();
        tree.root = tree.insert(tree.root, 10);
        tree.root = tree.insert(tree.root, 20);
        tree.root = tree.insert(tree.root, 30);
        tree.root = tree.insert(tree.root, 40);
        tree.root = tree.insert(tree.root, 50);
        tree.root = tree.insert(tree.root, 25);
        System.out.println("END");
    }
}