package AVL;
/**
 * Class AVLNode
 * Declare attributes for AVL tree
 * @author Diego
 * @version 1.0
 * @since 21/11/2020
 */
public class AVLNode {
    int key,height;
    AVLNode left,right;
    /**
     * Constructor AVLNode
     * Declare standard attributes and assign standard values
     * @param item
     * @author Diego
     * @version 1.0
     * @since 21/11/2020
     */
    public AVLNode(int item){
        this.key=item;
        left=right=null;
        height=1;
    }
}
