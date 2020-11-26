package Challenge;

import AVL.AVL;
import BST.BST;
import SPLAY.SPLAYTree;
import BTree.Btree;

public class Challenge {
    public String treeType;
    public int deep;
    public int order;
    public int timeleft;
    public int reward;

    public Challenge(int tree,int deeper, int grade){
        if(tree==0){
            treeType= String.valueOf(BST.class);
        }else if(tree==1){
            treeType= String.valueOf(AVL.class);
        }else if(tree==2){
            treeType= String.valueOf(SPLAYTree.class);
            order=grade;
        }else{
            treeType= String.valueOf(Btree.class);
        }
        deep=deeper;
        timeleft=2000;
        reward=5000;
    }
    public void Tick(){
        timeleft=-1;
    }
}

