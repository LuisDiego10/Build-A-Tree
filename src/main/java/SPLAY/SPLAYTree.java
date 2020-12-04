package SPLAY;

import BST.BST;
import BST.NodeBST;

/**
 * Class SPLAY
 * Declare attributes and contains all the methods to build the tree
 * @author Diego
 * @version 1.0
 * @since 24/11/2020
 */
public class SPLAYTree extends BST {

    public SPLAYTree(int x){
        root=newNode(x);
    }

    public static NodeBST newNode(int key){
        NodeBST node=new NodeBST(key);
        node.key= key;
        node.left=node.right=null;
        return node;
    }
    /**
     * Method rightRotate
     * Method for execute right rotation
     * @param x
     * @return Tree rotation;
     * @author Diego
     * @version 1.0
     * @since 24/11/2020
     */
    //Rotation Right
    public static NodeBST rightRotate(NodeBST x){
        NodeBST y=x.left;
        x.left=y.right;
        y.right=x;
        return y;
    }
    /**
     * Method leftRotate
     * Method for execute left rotation
     * @param x
     * @return Tree rotation;
     * @author Diego
     * @version 1.0
     * @since 24/11/2020
     */
    //Rotation Left
    public static NodeBST leftRotate(NodeBST x){
        NodeBST y=x.right;
        x.right=y.left;
        y.left=x;
        return y;
    }
    /**
     * Method searchSPLAY
     * Call Recursive method for Search fact
     * @param key (search)
     * @return boolean;
     * @author Diego
     * @version 1.0
     * @since 24/11/2020
     */
    public NodeBST searchSPLAY(int key){
        return root=SPLAY(root,key);
    }

    public NodeBST SPLAY(NodeBST node,int key){
        if (node==null||node.key==key)
            return node;
        if (node.key>key){
            if (node.left==null)
                return node;
            //Zig-Zig
            if (node.left.key>key){
                node.left.left=SPLAY(node.left.left,key);
                node=rightRotate(node);
            } else if (node.left.key<key){
                node.left.right = SPLAY(node.left.right, key);
                if (node.left.right!=null)
                    node.left=leftRotate(node.left);
            }
            return (node.left==null)?node:rightRotate(node);
        } else{
            if (node.right==null)
                return node;
            //Zag-Zig
            if (node.right.key>key){
                node.right.left=SPLAY(node.right.left,key);
                if (node.right.left!=null)
                    node.right=rightRotate(node.right);
            }else if (node.right.key < key){
                node.right.right = SPLAY(node.right.right, key);
                node = leftRotate(node);
            }
            return (node.right == null) ? node : leftRotate(node);
        }
    }
    /**
     * Method insert for herency
     * Used to insert value only whit the value
     * @param item
     * @return NodeBST;
     * @author Isaac
     * @version 1.0
     * @since 24/11/2020
     */
    public NodeBST insertSplay( int item) {
        return insert(root,item);
    }
    /**
     * Method insert
     * Insert fact in the SplayTree
     * @param node and item
     * @return newnode;
     * @author Diego
     * @version 1.0
     * @since 24/11/2020
     */
    public NodeBST insert(NodeBST node,int item){
        if (node==null)
            return newNode(item);
        node=SPLAY(node,item);
        if (node.key==item)
            return node;
        NodeBST newnode=newNode(item);
        if (node.key>item){
            newnode.right=node;
            newnode.left=node.left;
            node.left=null;
        } else{
            newnode.left=node;
            newnode.right=node.right;
            node.right=null;
        }
        searchSPLAY(item);
        return root=newnode;
    }
    /**
     * Method preOrder
     * Call Recursive method preOrder for show the Facts
     * @author Diego
     * @version 1.0
     * @since 24/11/2020
     */

    public static void preOrder(NodeBST node)
    {
        if (node != null)
        {
            System.out.print(node.key + " ");
            preOrder(node.left);
            preOrder(node.right);
        }
    }


}
