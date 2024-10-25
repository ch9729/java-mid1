package lang.wrapper;

public class MyIntegerMethodMain0 {

    public static void main(String[] args) {
        int value = 10;
        int i = compareTo(value, 5);
        int i1 = compareTo(value, 10);
        int i2 = compareTo(value, 520);
        System.out.println("i = " + i);
        System.out.println("i1 = " + i1);
        System.out.println("i2 = " + i2);
    }

    public static int compareTo(int value, int target) {
        if(value < target) {
            return -1;
        }else if(value > target) {
            return 1;
        }else {
            return 0;
        }
    }
}
