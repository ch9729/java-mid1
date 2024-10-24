package lang.string.test;

public class TestString10 {

    public static void main(String[] args) {
        String fruits = "apple,banana,mango";

        String[] regex = fruits.split(",");

        for (String s : regex) {
            System.out.println(s);
        }

        String join = String.join("->", regex);
        System.out.println(join);


    }
}
