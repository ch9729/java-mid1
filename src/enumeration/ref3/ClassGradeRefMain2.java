package enumeration.ref3;

public class ClassGradeRefMain2 {

    public static void main(String[] args) {
        int price = 10000;

        int basic = Grade.BASIC.discount(price);
        int gold = Grade.GOLD.discount(price);
        int diamond = Grade.DIAMOND.discount(price);

        System.out.println("BASIC할인금액 : " + basic);
        System.out.println("GOLD할인금액 : " + gold);
        System.out.println("DIAMOND할인금액 : " + diamond);
    }
}
