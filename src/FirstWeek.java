import java.util.Scanner;

public class FirstWeek{
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int length = scan.nextInt();
        int[] Array = new int[length];
        for (int i = 0; i < length; i++) {
            Array[i] = scan.nextInt();
        }FirstWeek firstweek = new FirstWeek();
        int[] out= firstweek.MaxsegmentSum5(Array,length);
        System.out.println(out[0]);
        System.out.println(out[1]);
        System.out.println(out[2]);
    }

    private int MaxsegmentSum(int[] a, int length) {
        int Maxsum = 0;
        for (int i = 0; i < length; i++) {
            for (int j = i; j < length; j++) {
                int thisSum =0;
                for (int k = i; k <= j; k++) {
                    thisSum += a[k];
                }
                if (thisSum > Maxsum)
                    Maxsum = thisSum;
            }
        }
        return Maxsum;  //创建i到j的子列，再依次求出这些子列的和。
    }

    private int MaxsegmentSum2(int[] a, int length) {
        int Maxsum = 0;
        for (int i = 0; i < length; i++) {
            int thisSum =0;
            for (int j = i; j < length; j++) {
                thisSum += a[j];
                if (thisSum > Maxsum)
                    Maxsum = thisSum;
            }
        }
        return Maxsum;  //创建i到j的子列，再依次求出这些子列的和。
    }

    private int MaxsegmentSum3(int[] a, int length){
        return DivideAndConquer(a, 0, length-1);
    }
    private int DivideAndConquer(int[] a, int left, int right){
        int MaxLeftSum, MaxRightSum;
        int MaxLeftBorderSum, MaxRightBorderSum;
        int LeftBorderSum,RightBorderSum;
        if (left == right){
            if (a[left] >0)
                return a[left];
            else
                return 0;
        }
        int center = (left + right) /2;
        MaxLeftSum = DivideAndConquer(a, left, center);
        MaxRightSum = DivideAndConquer(a, center +1, right);
        MaxLeftBorderSum = LeftBorderSum =0;
        for (int i =center; i >=left; i--){
            LeftBorderSum += a[i];
            if (LeftBorderSum >MaxLeftBorderSum)
                MaxLeftBorderSum =LeftBorderSum;
        }
        MaxRightBorderSum = RightBorderSum =0;
        for (int i =center+1; i <=right; i++) {
            RightBorderSum += a[i];
            if (RightBorderSum > MaxRightBorderSum)
                MaxRightBorderSum =RightBorderSum;
        }
        return Max3(MaxLeftSum, MaxRightSum, MaxLeftBorderSum +MaxRightBorderSum);

    }
    private int Max3(int a, int b, int c){
        if (a >b && a >c)
            return a;
        else if (b >a && b >c)
            return b;
        else
            return c;
    }

    private int MaxsegmentSum4(int[] a, int length){
        int Maxsum, thisSum;
        Maxsum = thisSum =0;
        for (int i =0; i < length; i++){
            thisSum += a[i];
            if (thisSum >Maxsum)
                Maxsum =thisSum;
            else if (thisSum <0)
                thisSum =0;
        }
        return Maxsum;
    }

    private int[] MaxsegmentSum5(int[] a, int length){
        int Maxsum, thisSum, lastNum, count;
        Maxsum = thisSum = lastNum= count=0;
        for (int i =0; i <length; i++){
            thisSum += a[i];
            if(thisSum >Maxsum){
                Maxsum =thisSum;
                lastNum =i;
            }
            else if (thisSum <0)
                thisSum =0;
        }
        int[] b={0, 0, 0};
        b[0] =Maxsum;
        b[1] =a[lastNum];
        int sum =0;
        for (int i = lastNum; i >=0; i--){
            sum +=a[i];
            count++;
            if (sum == Maxsum)
                break;
        }
        b[2] =a[lastNum-count+1];
        return b;
    }
}