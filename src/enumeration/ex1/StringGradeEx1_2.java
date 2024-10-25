package enumeration.ex1;

public class StringGradeEx1_2 {

    public static void main(String[] args) {
        int price = 10000;

        //상수를 활용하지 않고 바로 입력을 해도 오류
        DiscountService discountService = new DiscountService();
        //존재하지 않는 등급 입력
        int vip = discountService.discount("VIP", price);
        System.out.println("vip = " + vip);

        int basic = discountService.discount("BASIC", price);
        int gold = discountService.discount("GOLD1", price);
        int diamond = discountService.discount("DIAMOND", price);

        System.out.println("Basic: " + basic);
        System.out.println("Gold: " + gold);
        System.out.println("Diamond: " + diamond);
    }
}
