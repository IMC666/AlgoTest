import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class ForthWeek {

    public static void main(String[] args){
        /*
        int[] set =Input.getInput();
        BinarySearchTree bst =Input.creatBST(set);
        int k =1;
        while (k <=set[1]){
            int[] array =new int[set[0]];
            for (int i =0; i <set[0]; i++){
                array[i] =set[i+k*set[0]+2];
            }
            if (Method.isTheSame(bst,array))    System.out.print("true\n");
            else System.out.print("false\n");
            BinarySearchTree.initFlag(bst.getRoot());
            k++;
        }
        */
        /*
        int[] array =Input.obtainInput();
        BinarySearchTree.Node node =null;
        for (int i=0; i <array.length; i++){
            node =BinarySearchTree.insert(node,array[i]);
        }
        BinarySearchTree avl =new BinarySearchTree(node);
        System.out.print(avl.getRoot().getValue());
        */
        int[] array =Input.obtainInput();
        BinarySearchTree bst =new BinarySearchTree(Method.createCBT(array));
        BinarySearchTree bst1 =new BinarySearchTree(BinarySearchTree.delete(bst.getRoot(),7));
        Method.levelTraverse(bst1);



    }
}

class BinarySearchTree{
    public static class Node{
        private int value;
        private Node left,right;
        private boolean flag;
        private int height;

        Node(int value){
            this.value =value;
            this.height =1;
        }

        int getValue(){
            return this.value;
        }
        Node getLeft(){
            return this.left;
        }
        Node getRight(){
            return this.right;
        }
        boolean getFlag(){
            return this.flag;
        }

        void setLeft(Node tmp){
            this.left =tmp;
        }
        void setRight(Node tmp){
            this.right =tmp;
        }
        void setFlag(boolean value){
            this.flag =value;
        }

    }

    BinarySearchTree(int value){
        root =new BinarySearchTree.Node(value);
        root.height =1;
    }
    BinarySearchTree(Node tmp){
        root =tmp;
    }
    BinarySearchTree(){

    }

    private Node root;
    public Node getRoot() {
        return root;
    }
    public void setRoot(Node node){
        this.root =node;
    }


    void addLeftNode(int value){
        Node tmp =new Node(value);
        this.root.left =tmp;
    }
    void addRightNode(int value){
        Node tmp =new Node(value);
        this.root.right =tmp;
    }

    static void initFlag(Node root){
        if (root != null) {
            root.setFlag(false);
            initFlag(root.getLeft());
            initFlag(root.getRight());
        }
    }

    @Contract(pure = true)
    static private int max(int a, int b){
        return (a >b)?a:b;
    }
    @Contract(pure = true)
    static private int getHeight(Node node){
        if(node ==null) return 0;
        else return node.height;
    }
    static private int getBF(Node node){
        if (node ==null)    return 0;
        return getHeight(node.left) -getHeight(node.right);
    }
    static private Node rightRotate(@NotNull Node y){
        Node x =y.left;
        Node T2 =x.right;

        x.right =y;
        y.left =T2;

        x.height =max(getHeight(x.left), getHeight(x.right)) +1;
        y.height =max(getHeight(y.left), getHeight(y.right)) +1;
        return x;
    }
    static private Node leftRotate(@NotNull Node x){
        Node y =x.right;
        Node T2 =y.left;

        y.left =x;
        x.right =T2;

        x.height =max(getHeight(x.left), getHeight(x.right)) +1;
        y.height =max(getHeight(y.left), getHeight(y.right)) +1;
        return y;
    }



    @Contract("null, _ -> new")
    static Node insert(Node node, int value){
        if(node ==null)     return (new Node(value));
        if(value <node.value)   node.left =insert(node.left,value);
        else if (value >node.value)     node.right =insert(node.right,value);
        else return node;

        node.height =1 +max(getHeight(node.left),getHeight(node.right));
        int bf =getBF(node);

        if (bf >1 && value <node.left.value)   return rightRotate(node);
        if (bf <-1 && value >node.right.value)  return leftRotate(node);
        if (bf >1 && value >node.left.value){
            node.left =leftRotate(node.left);
            return rightRotate(node);
        }
        if (bf <-1 && value <node.right.value){
            node.right =rightRotate(node.right);
            return leftRotate(node);
        }
        return node;
    }
    static Node searchValue(@NotNull BinarySearchTree bst, int value){  //No consider this situation don't have it.
        Node tmp =bst.root;
        while (tmp.getValue() !=value){
            if (tmp.getValue() >value){
                tmp.setFlag(true);
                tmp =tmp.getLeft();
            }
            else{
                tmp.setFlag(true);
                tmp =tmp.getRight();
            }
        }
        return tmp;
    }
    static Node findMin(@NotNull BinarySearchTree bst){
        Node node =bst.root;
        while (true){
            if (node.left !=null){
                node =node.left;
            }
            else break;
        }
        return node;
    }
    static Node findMax(@NotNull BinarySearchTree bst){
        Node node =bst.root;
        while (true){
            if (node.right != null) {
                node = node.right;
            }
            else break;
        }
        return node;
    }
    static Node delete(Node node, int value){
            if(value >node.value)   node.right =delete(node.right,value);
            else if (value <node.value) node.left =delete(node.left,value);
            else{
                if (node.left !=null && node.right !=null){
                    Node tmp =findMax(new BinarySearchTree(node));
                    node.value =tmp.value;
                    node.right =delete(node.right, tmp.value);
                }
                else if(node.left !=null && node.right ==null){
                    return node.left;
                }
                else if(node.right !=null && node.left ==null){
                    return node.right;
                }
                else return node.right;
            }
            return node;
    }






}
class Input{
    static int[] getInput(){
        Scanner sc =new Scanner(System.in);
        int N =sc.nextInt();
        int L =sc.nextInt();
        int[] array =new int[N*(L+1)+2];
        array[0] =N;
        array[1] =L;
        for (int i=2; i <array.length; i++){
            array[i] =sc.nextInt();
        }
        return array;
    }

    static BinarySearchTree creatBST(@NotNull int[] Array){
        int[] array =new int[Array[0]];
        for (int i =0; i <array.length; i++){
            array[i] =Array[i+2];
        }
        BinarySearchTree.Node root=new BinarySearchTree.Node(array[0]);
        BinarySearchTree bst =new BinarySearchTree(root);
        for(int i =1; i <array.length; i++){
            while (true){
                if (array[i] <root.getValue()) {
                    if (root.getLeft() == null){
                        root.setLeft(new BinarySearchTree.Node(array[i]));
                        break;
                    }
                    root = root.getLeft();
                }
                else {
                    if (root.getRight() ==null){
                        root.setRight(new BinarySearchTree.Node(array[i]));
                        break;
                    }
                    root = root.getRight();
                }
            }
        }
        return bst;
    }

    static int[] obtainInput(){
        Scanner sc =new Scanner(System.in);
        int length =sc.nextInt();
        int[] array =new int[length];
        for (int i=0; i <length; i++){
            array[i] =sc.nextInt();
        }
        return array;
    }

}

class Method{
    static boolean isTheSame(BinarySearchTree bst, int[] array){
        BinarySearchTree.Node tmp;
        for (int i=0; i <array.length; i++){
            tmp =BinarySearchTree.searchValue(bst,array[i]);
            if (tmp.getFlag() ==false)  tmp.setFlag(true);
            else return false;
        }
        return true;
    }

    static BinarySearchTree.Node createCBT(int[] array){
        int M =array.length;
        if (M ==1)  return (new BinarySearchTree.Node(array[0]));

        int N =(int)(Math.log(M) /Math.log(2));
        int D =(int)(M -Math.pow(2,N) +1);
        int half =(int)Math.pow(N-1,2);
        int root;

        if(D >half) root= (int)(Math.pow(2,N) -1);
        else root =M -(int)Math.pow(2,N-1);
        BinarySearchTree.Node node =new BinarySearchTree.Node(array[root]);
        node.setLeft(createCBT(Arrays.copyOfRange(array,0,root)));
        if(Arrays.copyOfRange(array,root+1,M).length !=0){  //CBT may don't have right-leaves-node.
            node.setRight(createCBT(Arrays.copyOfRange(array,root+1,M)));
        }
        return node;
    }

    static void levelTraverse(@NotNull BinarySearchTree T){
        Queue<BinarySearchTree.Node> queue =new LinkedList<>();
        queue.offer(T.getRoot());
        while (!queue.isEmpty()){
            BinarySearchTree.Node tmp =queue.poll();
            System.out.print(tmp.getValue());
            //if (tmp.getRight() ==null && tmp.getLeft() ==null) System.out.print(tmp.getValue());
            if (tmp.getLeft() !=null)  queue.offer(tmp.getLeft());
            if (tmp.getRight() !=null) queue.offer(tmp.getRight());
        }

    }
}

