package Server;

import AVL.AVL;
import BST.BST;
import BTree.Btree;
import Challenge.Challenge;
import Challenge.Token;
import SPLAY.SPLAYTree;
import Socket.SocketServer;
import java.util.Random;

import java.io.IOException;

public class Server {
    BST playerOneTree;
    BST playerTwoTree;
    BST playerThreeTree;
    BST playerfourthTree;
    Token[] tokens= new Token[5];
    Challenge actualChallenge;
    static SocketServer serverSocket ;

    public static void main(String[] args) throws IOException {
        serverSocket = new SocketServer();

    }
    public void randomChallenge(){
        Random randomizer= new Random();
        actualChallenge= new Challenge(randomizer.nextInt(5),randomizer.nextInt(4),randomizer.nextInt(3)+3);
    }
    public void playToken(Token token){
        if (token.player==1){
            checkTree(playerOneTree,token);
        }else if (token.player==2){
            checkTree(playerTwoTree,token);
        }else if (token.player==3){
            checkTree(playerThreeTree,token);
        }else if (token.player==4){
            checkTree(playerfourthTree,token);
        }
    }
    public void checkTree(BST playerTree, Token token){
        if (playerTree != null){
            if (playerTree.getClass().getName().equals(token.type)) {
                if(playerTree.getClass()==BST.class){
                    playerTree.insert(token.value);
                }else if(playerTree.getClass()== Btree.class){
                    playerTree.insert(token.value);
                }else if(playerTree.getClass()== AVL.class){
                    playerTree.insertAVL(token.value);
                }else{
                    playerTree.insertSplay(token.value);
                }

            }else{
                playerTree=null;
            }
        }
    }
}
