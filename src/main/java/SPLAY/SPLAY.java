package SPLAY;

import BST.BST;

public class SPLAY {
    static SPLAYNode newNode(int key){
        SPLAYNode node=new SPLAYNode();
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
     * @since 22/11/2020
     */
    //Rotation Right
    static SPLAYNode rightRotate(SPLAYNode x){
        SPLAYNode y=x.left;
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
     * @since 22/11/2020
     */
    //Rotation Left
    static SPLAYNode leftRotate(SPLAYNode x){
        SPLAYNode y=x.right;
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
     * @since 21/11/2020
     */
    static SPLAYNode searchSPLAY(SPLAYNode root,int key){
        return splay(root,key);
    }
    static SPLAYNode splay(SPLAYNode root,int key){
        if (root==null||root.key==key)
            return root;
        if (root.key>key){
            if (root.left==null)
                return root;
            //Zig-Zig
            if (root.left.key>key){
                root.left.left=splay(root.left.left,key);
                root=rightRotate(root);
            } else if (root.left.key<key){
                root.left.right = splay(root.left.right, key);
                if (root.left.right!=null)
                    root.left=leftRotate(root.left);
            }
            return (root.left==null)?root:rightRotate(root);
        } else{
            if (root.right==null)
                return root;
            //Zag-Zig
            if (root.right.key>key){
                root.right.left=splay(root.right.left,key);
                if (root.right.left!=null)
                    root.right=rightRotate(root.right);
            }else if (root.right.key < key){
                root.right.right = splay(root.right.right, key);
                root = leftRotate(root);
            }
            return (root.right == null) ? root : leftRotate(root);
        }
    }
    static void preOrder(SPLAYNode root)
    {
        if (root != null)
        {
            System.out.print(root.key + " ");
            preOrder(root.left);
            preOrder(root.right);
        }
    }
    /**
     * Contain the instance for run Tree
     * @author Diego
     * @version 1.0
     * @since 21/11/2020
     */
    // Driver code
    public static void main(String[] args)
    {
        SPLAYNode root = newNode(100);
        root.left = newNode(50);
        root.right = newNode(200);
        root.left.left = newNode(40);
        root.left.left.left = newNode(30);
        root.left.left.left.left = newNode(20);

        root = searchSPLAY(root, 20);
        System.out.print("Preorder traversal of the" + " modified Splay tree is \n");
        preOrder(root);
    }
}
