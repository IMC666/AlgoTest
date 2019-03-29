import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.Stack;


public class ThirdWeek {
    public static void main(String[] args){
        //BinaryTree bt1 =GetInput.getInput();
        // BinaryTree bt2 =GetInput.getInput();
        //boolean flag =Solution.judgement(bt1, bt2);
        //System.out.printf("%b", flag);

        //BinaryTree bt =GetInput.getInput();
        //Solution.searchLeaves(bt);

        char[] in =GetInput.getOperation();
        char[] pre =new char[in.length];
        for (int i =0; i <pre.length; i++){
            pre[i] =(char) (i+49);  //the dependence is ASCII.
        }
        Solution.printfPostOrder(pre,in,6);





    }
}

class BinaryTree{
    public static class Node{
        private char value;
        private Node left,right;


        Node(char value){
            this.value =value;
        }
        public char getValue(){
            return this.value;
        }
        public Node getLeft(){
            return this.left;
        }
        public Node getRight(){
            return this.right;
        }
    }

    private Node root;

    Node getRoot(){
        return this.root;
    }

    BinaryTree(char value){
        root =new Node(value);
    }
    BinaryTree(){   }

    void addLeftNode(char value, Node node){
        Node tmp =new Node(value);
        node.left =tmp;
    }
    void addRightNode(char value, Node node){
        Node tmp =new Node(value);
        node.right =tmp;
    }

    void addLeftSubtree(BinaryTree subtree){
        this.root.left =subtree.root;
    }
    void addRightSubtree(BinaryTree subtree){
        this.root.right =subtree.root;
    }

    BinaryTree getLeftSubtree(){
        BinaryTree leftSubtree =new BinaryTree();
        leftSubtree.root =this.root.left;
        return leftSubtree;
    }
    BinaryTree getRightSubtree(){
        BinaryTree rightSubtree =new BinaryTree();
        rightSubtree.root =this.root.right;
        return rightSubtree;
    }


}



class GetInput{
    static BinaryTree getInput(){
        BinaryTree bt =transform(getAnotherArray());
        return bt;
    }

    static private char[] getArray(){
        Scanner sc =new Scanner(System.in);
        int length =sc.nextInt()*3;
        char[] array =new char[length];
        for (int i=0; i<length; i++){
            array[i] =sc.next().charAt(0);   //receive the first position.  Array's length must less than 10.
        }
        return array;
    }

    static private char[] getAnotherArray(){
        Scanner sc =new Scanner(System.in);
        int Num =sc.nextInt();
        char[] array =new char[Num *2];
        for (int i=0; i<array.length; i++){
            array[i] =sc.next().charAt(0);
        }
        char[] Array =new char[Num *3];
        int shift =1;
        for (int i=0; i<array.length; i++){
            Array[i+shift] =array[i];
            if ((i +1)%2 ==0){
                char tmp =(char)(shift -1 + 'A');    //get the char with the value of the number of node.
                Array[i+shift-2] =tmp;
                shift++;
            }
        }
        return Array;
    }

    static BinaryTree transform(@NotNull char[] array){
        int[] transNum =new int[array.length];
        int root =0;
        int flag;
        for (int i=0; i <array.length; i++){
            transNum[i] =Character.getNumericValue(array[i]);  //char '-' will translate in -1; chars 'A''B' will translate in arabic number >10
        }
        for (int i=0; i <array.length/3; i++){
            flag =0;
            for (int tmp:transNum
                 ) {
                    if (tmp ==i)    flag =1;
                }
            if (flag ==0)   root =i;
        }
        BinaryTree bt =creatTree(root, array);
        return bt;
    }

    static BinaryTree creatTree(int index, @NotNull char[] array){
        BinaryTree bt =new BinaryTree(array[index*3]);
        if (Character.getNumericValue(array[index*3 +1]) !=-1){
            bt.addLeftSubtree(creatTree(Character.getNumericValue(array[index*3 +1]),array));
        }
        if (Character.getNumericValue(array[index*3 +2]) !=-1){
            bt.addRightSubtree(creatTree(Character.getNumericValue(array[index*3 +2]),array));
        }
        return bt;
    }

    static char[] getOperation(){
        Scanner sc =new Scanner(System.in).useDelimiter("\n");
        int length =sc.nextInt();
        char[] array =new char[length];
        Stack<Character> stack =new Stack();
        String s;
        int j =0;
        for (int i =0; i <array.length*2; i++){
            s =sc.next();
            if (s.contains("Push")){
                stack.push(s.charAt(5));
            }
            else{
                array[j] =stack.pop();
                j++;
            }

        }
        return array;
    }
}

class Solution{
    static boolean judgement(BinaryTree T1, BinaryTree T2) {
        if (T1.getRoot().getValue() != T2.getRoot().getValue()) return false;
        if (T1.getRoot().getLeft() == null && T1.getRoot().getRight() == null && T2.getRoot().getLeft() == null && T2.getRoot().getRight() == null) {
            return true;
        }
        if (T1.getRoot().getLeft() == null && T2.getRoot().getLeft() == null && T1.getRoot().getRight().getValue() == T2.getRoot().getRight().getValue()) {
            return judgement(T1.getRightSubtree(), T2.getRightSubtree());
        }
        if (T1.getRoot().getLeft() == null && T2.getRoot().getRight() == null && T1.getRoot().getRight().getValue() == T2.getRoot().getLeft().getValue()) {
            return judgement(T1.getRightSubtree(), T2.getLeftSubtree());
        }
        if (T1.getRoot().getRight() == null && T2.getRoot().getLeft() == null && T1.getRoot().getLeft().getValue() == T2.getRoot().getRight().getValue()) {
            return judgement(T1.getLeftSubtree(), T2.getRightSubtree());
        }
        if (T1.getRoot().getRight() == null && T2.getRoot().getRight() == null && T1.getRoot().getLeft().getValue() == T2.getRoot().getLeft().getValue()) {
            return judgement(T1.getLeftSubtree(), T2.getLeftSubtree());
        }
        if (T1.getRoot().getLeft() != null && T1.getRoot().getRight() != null && T2.getRoot().getLeft() != null && T2.getRoot().getRight() != null) {
            if (T1.getRoot().getLeft().getValue() == T2.getRoot().getLeft().getValue() && T1.getRoot().getRight().getValue() == T2.getRoot().getRight().getValue()) {
                return judgement(T1.getLeftSubtree(), T2.getLeftSubtree()) && judgement(T1.getRightSubtree(), T2.getRightSubtree());

            }
            if (T1.getRoot().getLeft().getValue() == T2.getRoot().getRight().getValue() && T1.getRoot().getRight().getValue() == T2.getRoot().getLeft().getValue()) {
                return judgement(T1.getLeftSubtree(), T2.getRightSubtree()) && judgement(T1.getRightSubtree(), T2.getLeftSubtree());
            }
        }
        return false;
    }

    static void searchLeaves(@NotNull BinaryTree T){            //
        Queue<BinaryTree.Node> queue =new LinkedList<>();
        queue.offer(T.getRoot());
        while (!queue.isEmpty()){
            BinaryTree.Node tmp =queue.poll();
            if (tmp.getRight() ==null && tmp.getLeft() ==null) System.out.print(tmp.getValue());
            if (tmp.getLeft() !=null) queue.offer(tmp.getLeft());
            if (tmp.getRight() !=null) queue.offer(tmp.getRight());
        }

    }


    static private int searchRoot(char index, char[] in, int n){ //n is the length of detected array named in.
        for (int i=0; i <n; i++){
            if (in[i] ==index) return i;
        }
        return -1;
    }

    static void printfPostOrder(char[] pre, char[] in, int n) {
        int root = searchRoot(pre[0], in, n);
        if (root != 0) {
            printfPostOrder(Arrays.copyOfRange(pre, 1, n), in, root); //variable also can be changed into Arrays(in,0,n-1).
        }
        if (root != n - 1) {
            printfPostOrder(Arrays.copyOfRange(pre, root + 1, n), Arrays.copyOfRange(in, root + 1, n), n - root - 1);
        }
        System.out.print(pre[0]);
    } //the value n ensures subtree's integrity when recursive.

    /*static private int searchRoot(char index, char[] in){ //n is the length of detected array named in.
        for (int i=0; i <in.length; i++){
            if (in[i] ==index) return i;
        }
        return -1;
    }

    static void printfPostOrder(char[] pre, char[] in){
        int root =searchRoot(pre[0], in);
        if (root !=0){
            printfPostOrder(Arrays.copyOfRange(pre,1,root),in);
        }
        if (root !=in.length){
            printfPostOrder(Arrays.copyOfRange(pre,root+1,in.length),Arrays.copyOfRange(in,root+1,in.length));
        }
        System.out.print(pre[0]);


    }*/
    //This is wrong for when getting left-subtree from top subtree, top subtree is broken.
    //So, when the process about left was done, the process about right was wrong.


}



