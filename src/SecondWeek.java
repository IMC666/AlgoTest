
import org.jetbrains.annotations.NotNull;

import java.util.Scanner;

public class SecondWeek {
    public static void main(String[] args){
       /*
        LinkedHeadList list1 =new LinkedHeadList();
        LinkedHeadList list2 =new LinkedHeadList();
        LinkedHeadList list3 =new LinkedHeadList();



        list1.listRead();
        list2.listRead();
        list3 =list3.mergeList(list1,list2);


        list1.printList(list3.getHead());
        */

       /*
       Transform transform =new Transform();
       LinkedHeadList list;
       transform.getInput();
       list =transform.transformList(transform.array, transform.addr);
       LinkedHeadList[] lists;
       lists =list.splitList(list,4);
       for (int i=0; i<lists.length; i++){
           if (lists[i].getListLength()==transform.K)
           lists[i] =list.reverseList(lists[i]);
       }
       int[] array =transform.transformArray(lists,transform.length);
       transform.printf(array);
       */
       /*
       TernaryLinkedList ternaryList1 =new TernaryLinkedList();
       TernaryLinkedList ternaryList2 =new TernaryLinkedList();
       TernaryLinkedList ternaryList3;
       int[] polynomial1 =TernaryLinkedList.getInput();
       int[] polynomial2 =TernaryLinkedList.getInput();
       for (int i =0; i <polynomial1.length; i=i+2){
           ternaryList1.addNode(polynomial1[i+1],polynomial1[i]);
       }
       for (int i=0; i <polynomial2.length; i=i+2){
           ternaryList2.addNode(polynomial2[i+1],polynomial2[i]);
       }
       ternaryList3 =TernaryLinkedList.mergeList(ternaryList1,ternaryList2);
       ternaryList3 =TernaryLinkedList.sortList(ternaryList3);
       ternaryList3.printList();
       */

       Scanner sc =new Scanner(System.in);
       int stackCapaticy =sc.nextInt();
       int sequenceSize =sc.nextInt();
       int N =sc.nextInt();
       int[][] array =new int[N][sequenceSize];
       for (int i =0; i <N; i++){
           for (int j=0; j <sequenceSize; j++){
               array[i][j] =sc.nextInt();
           }
       }
       Stack stack1 =new Stack(sequenceSize);
       Stack stack2 =new Stack(stackCapaticy);
       boolean flag;
       for (int i=0; i <N; i++){
           {
               flag = true;
               stack1.initStack();
               stack2.initStack();
               for (int k = stack1.getCapacity(); k > 0; k--) {
                   stack1.push(k);
               }
           }
           for (int j=0; j <sequenceSize; j++){
               if (stack2.getTop() !=array[i][j]) {
                   if (!stack2.Push(array[i][j], stack1)) {
                       flag = false;
                       break;
                   }
                   j--;
               }
               else
                   stack2.pull();
           }
           if (flag)
               System.out.printf("Yes\n");
           else
               System.out.printf("NO\n");
       }
    }
}

class LinkedHeadList {
    public static class Node {
        private int Date;
        private Node Next;

        public Node(int Date) {
            this.Date = Date;
            this.Next = null;
        }

        public Node(Node Next) {
            this.Next = Next;
        }

        public int getDate() {
            return Date;
        }

        public Node getNext(){
            return Next;
        }

        public void setNext(Node next){
            this.Next =next;
        }
    }

    private Node head;

    LinkedHeadList() {
        head = new Node(null);
    }

    Node getHead() {
        return head;
    }

    void addNode(int Date) {
        Node node = new Node(Date);
        Node tmp = head;
        while (tmp.Next != null) {
            tmp = tmp.Next;
        }
        tmp.Next = node;
    }

    int findValue(int Date, Node head) {
        Node tmp = head;
        int Kth = 0;
        while (tmp.Date != Date) {
            if(tmp.Next !=null){
                tmp =tmp.Next;
                Kth++;
            }
            else
                return -1;
        }
        return Kth;   //返回找到的第一个Value所在的Node索引,有错误，会陷入死循环。
    }

    Node findKthAdress(int Kth, Node head) {
        Node tmp = head;
        for (int i = 0; i < Kth; i++) {
            tmp = tmp.Next;
        }
        return tmp;
    }

    int findKth(int Kth, Node head) {
        Node tmp = head;
        for (int i = 0; i < Kth; i++) {
            tmp = tmp.Next;
            if (tmp == null) {
                return -1;
            }
        }
        return tmp.Date;
    }

    int getListLength() {
        int i = 0;
        Node tmp = head.Next;
        while (tmp != null) {
            tmp = tmp.Next;
            i++;
        }
        return i;

    }

    boolean insertKth(int Date, int Kth, Node head) {
        Node tmp = head;
        for (int i = 0; i < Kth - 1; i++) {
            tmp = tmp.Next;
            if (tmp == null)
                return false;
        }
        Node node = new Node(Date);
        node.Next = tmp.Next;
        tmp.Next = node;
        return true;
    }

    boolean deleteKth(int Kth, Node head) {
        Node tmp = head;
        for (int i = 0; i < Kth - 1; i++) {
            tmp = tmp.Next;
            if (tmp == null)
                return false;  //未防止非法输入
        }
        tmp.Next = tmp.Next.Next;
        return true;
    }

    void printList(Node head) {
        Node tmp = head.Next;
        while (tmp != null) {
            System.out.println(tmp.Date); //未考虑边界
            tmp = tmp.Next;
        }
    }

    void listRead() {
        Scanner S = new Scanner(System.in);
        int s = S.nextInt();
        int[] array = new int[s];
        for (int i = 0; i < s; i++) {
            array[i] = S.nextInt();
        }
        for (int i : array) {
            addNode(i);
        }
    }

    LinkedHeadList[] splitList(LinkedHeadList list, int K) {
        int num = list.getListLength() / K;
        Node[] listLast = new Node[num];
        for (int i = 0; i < listLast.length; i++) {
            listLast[i] = findKthAdress((i+1) * K, list.getHead());
        }
        Node[] listHead =new Node[num+1];
        for (int i =1; i <listHead.length; i++){
            listHead[i] =listLast[i-1].Next;
        }
        listHead[0] =list.getHead().Next;
        LinkedHeadList[] lists =new LinkedHeadList[num +1];
        for (int i =0; i <lists.length;i++){
            LinkedHeadList tmp =new LinkedHeadList();
            tmp.getHead().Next =listHead[i];
            lists[i]=tmp;
            if(i<listLast.length)
            listLast[i].Next =null;
        }
        return lists;
    }


    LinkedHeadList mergeList(LinkedHeadList list1, LinkedHeadList list2) {

        Node tmp1, tmp2;
        tmp1 = list1.getHead().Next;  //tmp1指向第一个元素
        while (tmp1 != null) {
            tmp2 = list2.getHead();  //tmp2指向第零个元素
            while (!((tmp1.Date == tmp2.Next.Date) || (tmp1.Date < tmp2.Next.Date))) {
                tmp2 = tmp2.Next;
            }
            Node node = new Node(tmp1.Date);
            node.Next = tmp2.Next;
            tmp2.Next = node;
            tmp1 = tmp1.Next;
        }
        return list2;
    }

    LinkedHeadList reverseList(LinkedHeadList list) {
        Node tmp = list.getHead().Next;
        Node pre, next;
        pre = null;
        while (tmp != null) {
            next = tmp.Next;
            tmp.Next = pre;
            pre = tmp;
            tmp = next;
        }
        LinkedHeadList tmpList =new LinkedHeadList();
        tmpList.getHead().Next =pre;
        return tmpList;
    }

    LinkedHeadList mergeListDirect(LinkedHeadList list1, LinkedHeadList list2){
        Node tmp =findKthAdress(list1.getListLength(),list1.getHead());
        tmp.Next =list2.getHead().Next;
        return list1;
    }

}

class Transform extends LinkedHeadList{

    int[] array,myAddr,date,nextAddr;
    int addr,length,K;

    void getInput(){
        Scanner s =new Scanner(System.in);
        addr =s.nextInt();
        length =s.nextInt();
        K =s.nextInt();
        array =new int[length*3];
        for (int i=0; i <array.length; i=i+3){
            array[i] =s.nextInt();
            array[i+1] =s.nextInt();
            array[i+2] =s.nextInt();
        }
    }

    LinkedHeadList transformList(int[] array, int addr){
        myAddr =new int[array.length/3];
        date =new int[array.length/3];
        nextAddr =new int[array.length/3];
        for (int i =0; i <array.length; i=i+3){
                myAddr[i/3] =array[i];
                date[i/3] =array[i+1];
                nextAddr[i/3] =array[i+2];
        }
        int tmpAddr =addr;
        LinkedHeadList list = new LinkedHeadList();
        for (int i =0; i <myAddr.length; i++) {
            int k = 0;
            while (myAddr[k] !=tmpAddr) {
                k++;
            }
            list.addNode(date[k]);
            if (nextAddr[k] !=-1){
            tmpAddr =nextAddr[k];
            }
        }
        return list;
    }

    int[]transformArray(LinkedHeadList[] lists, int length){
        int[] array =new int[length*3];
        for (int i=0; i<lists.length-1; i++){
            lists[i+1] =mergeListDirect(lists[i],lists[i+1]);
        }
        LinkedHeadList list =lists[lists.length-1];
        Node tmp=list.getHead().getNext();
        for (int i=0; i<list.getListLength(); i++){
                int kth =0;
                while (tmp.getDate() !=date[kth]){
                    kth++;
                }
                array[i*3] =myAddr[kth];
                array[i*3+1] =date[kth];
                tmp =tmp.getNext();
        }
        for(int i=1; i<length; i++){
            array[i*3-1] =array[i*3];
        }
        return array;
    }




    void printf(int[] array){
        for (int i:array){
            System.out.println(i);
        }
    }

    void printf(LinkedHeadList[] array){
        LinkedHeadList list =new LinkedHeadList();
        for (LinkedHeadList i:array){
            list.printList(i.getHead());
        }
    }




}

class TernaryLinkedList{
    public static class Node {
        private int Index;
        private int Date;
        private TernaryLinkedList.Node Next;

        public Node(TernaryLinkedList.Node Next) {
            this.Next = Next;
        }

        public Node(int Index,int Date){
            this.Index =Index;
            this.Date = Date;
        }
        public TernaryLinkedList.Node getNext(){
            return Next;
        }
    }

    private Node head;

    Node getHead() {
        return head;
    }


    TernaryLinkedList(){
        head =new Node(null);
    }

    void addNode(int Index, int Date) {
        Node node = new Node(Index,Date);
        Node tmp =head;
        while (tmp.Next != null) {
            tmp = tmp.Next;
        }
        tmp.Next = node;
    }

    static int[] getInput(){
        Scanner sc =new Scanner(System.in);
        int length1 =sc.nextInt();
        int[] polynomial =new int[length1*2];
        for (int i=0; i <polynomial.length; i++){
            polynomial[i] =sc.nextInt();
        }
        return polynomial;
    }

    void printList(){
        Node tmp =this.head.Next;
        while (tmp !=null){
            System.out.printf("%d %d  ",tmp.Date,tmp.Index);
            tmp =tmp.Next;
        }
    }

    static TernaryLinkedList mergeList(TernaryLinkedList list1, TernaryLinkedList list2){
        Node tmp =list1.head;
        while(tmp.Next != null){
            tmp =tmp.Next;
        }
        tmp.Next =list2.head.Next;   //没有考虑list2的非法输入
        return list1;
    }

    static TernaryLinkedList listMultiply(@NotNull TernaryLinkedList list1, @NotNull TernaryLinkedList list2){
        TernaryLinkedList list =new TernaryLinkedList();
        Node tmp1 =list1.head.Next;
        Node tmp2 =list2.head.Next;
        while (tmp1 !=null){
            while (tmp2 !=null){
                list.addNode(tmp1.Index+tmp2.Index,tmp1.Date*tmp2.Date);
                tmp2 =tmp2.Next;
            }
            tmp1 =tmp1.Next;
            tmp2 =list2.head.Next;
        }
        return list;
    }

    static TernaryLinkedList sortList(@NotNull TernaryLinkedList list){   //similar bubble sort
        Node tmp =list.head;
        while (tmp !=null){
            swapNode(list.head);
            tmp =tmp.Next;
        }
        return list;
    }

    static void swapNode(Node node){
        Node pre =node;
        while (pre.Next !=null && pre.Next.Next !=null){
            Node now =pre.Next;
            Node last =now.Next;
            if (last.Index >now.Index){
                pre.Next =last;
                now.Next =last.Next;
                last.Next =now;
            }
            else if (last.Index ==now.Index){
                now.Date =now.Date +last.Date;
                now.Next =last.Next;
            }
            pre =pre.Next;
        }

    }

}

class Stack extends LinkedHeadList{
    private int capacity;
    private int deepth;
    Stack(int size){
        super();
        this.capacity =size;
        deepth =0;
    }

    boolean push(int key){
        if (deepth <capacity){
            LinkedHeadList.Node node=new Node(key);
            node.setNext(super.getHead().getNext());
            super.getHead().setNext(node);
            deepth++;
            return true;
        }
        else
            return false;
    }

    int pull(){
        if (deepth !=0){
            deepth--;
            int key=super.getHead().getNext().getDate();
            super.getHead().setNext(super.getHead().getNext().getNext());
            return key;
        }
        else return -1;
    }

    int getCapacity(){
        return capacity;
    }

    int getDeepth(){
        return deepth;
    }

    int getTop(){
        if (super.getHead().getNext() !=null) {
            return super.getHead().getNext().getDate();
        }
        else
            return -1;
    }

    boolean Push(int key, Stack stack){
        int num =super.findValue(key, stack.getHead());
        if ((num !=-1 )&& (num <=this.getCapacity() -this.getDeepth())){
            for (int i =0; i <num; i++){
                this.push(stack.pull());
            }
            return true;
        }
        else
            return false;
    }

    void initStack(){
        this.getHead().setNext(null);
        this.deepth =0;
    }

}