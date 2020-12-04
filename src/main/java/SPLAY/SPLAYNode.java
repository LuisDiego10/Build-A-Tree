package SPLAY;

import BST.NodeBST;

/**
 * Class SPLAYNode
 * Declare attributes for SPLAY tree
 * @author Diego
 * @version 1.0
 * @since 24/11/2020
 */
public class SPLAYNode extends NodeBST {
    public SPLAYNode left, right;

    /**
     * Constructor NodeBST
     * Declare standard attributes assign standard values
     *
     * @param item
     * @author Diego
     * @version 1.0
     * @since 21/11/2020
     */
    public SPLAYNode(int item) {
        super(item);
    }
}
