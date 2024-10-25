package lang.wrapper.test;

public class WrapperTest1 {

    public static void main(String[] args) {
        String str1 = "10";
        String str2 = "20";

        int i = Integer.parseInt(str1);
        int j = Integer.parseInt(str2);

        int sum = i+j;

        System.out.println("두 수의 합은: " + sum);
    }
}
