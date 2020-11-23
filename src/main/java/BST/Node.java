package BST;
/**
 *Class Node
 * Declare attributes for BST tree
 * @author Diego
 * @version 1.0
 * @since 21/11/2020
 */
public class Node {
    int key ;
    Node left, right;
    /**
     * Constructor NodeBST
     * Declare standard attributes assign standard values
     * @param item
     * @author Diego
     * @version 1.0
     * @since 21/11/2020
     */
    public Node (int item){
        this.key=item;
        left=right=null;
    }
}
