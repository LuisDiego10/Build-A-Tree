package BST;

public class BST {
    Node root;

    public BST (){
        this.root = null;
    }
    void insertBST(int key){
        root=insertRecursive(root,key);
    }
    Node insertRecursive(Node root,int key){
        //tree is empty
        if (root==null){
            root=new Node(key);
            return root;
        }
        //Traverse Tree
        if (key<=root.key)     //insert in the left subtree
            root.left=insertRecursive(root.left, key);
        else if (key>=root.key)    //insert in the right subtree
            root.right=insertRecursive(root.right, key);
        // return pointer
        return root;
    }
    void inorderBST() {
        inorderRecursive(root);
    }
    void inorderRecursive(Node root){
        if (root!=null){
            inorderRecursive(root.left);
            System.out.print(root.key+ " ");
            inorderRecursive(root.right);
        }
    }
    boolean searchBST(int key){
        root=searchRecursive(root,key);
        if (root!=null)
            return true;
        else
            return false;
    }
    Node searchRecursive(Node root, int key){
        if (root==null||root.key==key)
            return root;
        if (root.key>key)
            return searchRecursive(root.left,key);
        return searchRecursive(root.right,key);
    }
}
class Main{
    public static void main (String [] args){
        BST firtsBST=new BST();
        firtsBST.insertBST(45);
        firtsBST.insertBST(10);
        firtsBST.insertBST(7);
        firtsBST.insertBST(12);
        firtsBST.insertBST(90);
        firtsBST.insertBST(50);
        firtsBST.inorderBST();
        boolean returnTF= firtsBST.searchBST(50);
        System.out.println(returnTF);
    }
}