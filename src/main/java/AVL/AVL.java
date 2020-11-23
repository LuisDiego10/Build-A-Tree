package AVL;

public class AVL {
    AVLNode root;
    //Heigh of tree
    int height(AVLNode node){
        if (node==null){
            return 0;
        }
        return node.height;
    }
    //Calculate the roll factor
    int getBalance(AVLNode node){
        if (node==null){
            return 0;
        }
        return height(node.left)-height(node.right);
    }
    //Function for calcule max
    int max (int a, int b){
        return Math.max(a,b);
    }
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
    AVLNode insertAVL(AVLNode node, int key){
        if (node==null){
            return new AVLNode(key);
        }
        if (key< node.key)
            node.left=insertAVL(node.left,key);
        else if (key> node.key)
            node.right=insertAVL(node.right,key);
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

    public static void main (String args[]) {
        AVL tree = new AVL();
        tree.root = tree.insertAVL(tree.root, 10);
        tree.root = tree.insertAVL(tree.root, 20);
        tree.root = tree.insertAVL(tree.root, 30);
        tree.root = tree.insertAVL(tree.root, 40);
        tree.root = tree.insertAVL(tree.root, 50);
        tree.root = tree.insertAVL(tree.root, 25);
        System.out.println("END");
    }
}