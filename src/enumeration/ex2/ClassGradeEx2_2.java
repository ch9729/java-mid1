package enumeration.ex2;

public class ClassGradeEx2_2 {

    public static void main(String[] args) {
        int price = 10000;

        DiscountService discountService = new DiscountService();
        //생성자를 막아서 외부에서 생성자 호출이 불가
        //ClassGrade newClassGrade = new ClassGrade();
        //int result = discountService.discount(newClassGrade, price);
        //System.out.println("newClassGrade등급의 할인 금액: " + result);
    }
}
