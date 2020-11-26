
import SPLAY.SPLAYNode;
import SPLAY.SPLAYTree;
import static SPLAY.SPLAYTree.*;

public class SPLAYtest {
    /**
     * Contain the instance for run Tree
     * @author Diego
     * @version 1.0
     * @since 24/11/2020
     */
    // Driver code
    public static void main(String[] args)
    {
        SPLAYTree splay= new SPLAYTree(100);
        splay.root.left = newNode(50);
        splay.root.right = newNode(200);
        splay.root.left.left = newNode(40);
        splay.root.left.left.left = newNode(30);
        splay.root.left.left.left.left = newNode(20);
        splay.searchSPLAY(40);
        preOrder(splay.root);
        splay.insert(splay.root,25);
        System.out.print("\n Preorder traversal of the" + " modified Splay tree is \n");
        preOrder(splay.root);
    }
}
