package BST;

import AVL.AVLNode;

/**
 *Class Node
 * Declare attributes for BST tree
 * @author Diego
 * @version 1.0
 * @since 21/11/2020
 */
public class NodeBST {
    public int key ;
    public NodeBST left;
    public NodeBST right;
    /**
     * Constructor NodeBST
     * Declare standard attributes assign standard values
     * @param item
     * @author Diego
     * @version 1.0
     * @since 21/11/2020
     */
    public NodeBST(int item){
        this.key=item;
        left=right=null;
    }
}
