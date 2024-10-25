package lang.wrapper;

public class MyIntegerNullMain1 {

    public static void main(String[] args) {
        MyInteger[] inArr = {new MyInteger(-1), new MyInteger(0), new MyInteger(1)};


        System.out.println(findValue(inArr, -1));
        System.out.println(findValue(inArr, 0));
        System.out.println(findValue(inArr, 1));
        System.out.println(findValue(inArr, 100));
    }
    
    static MyInteger findValue(MyInteger[] inArr, int target) {
        for (MyInteger myInteger : inArr) {
            if(myInteger.getValue() == target) {
                return myInteger;
            }
        }
        return null;
    }
}
