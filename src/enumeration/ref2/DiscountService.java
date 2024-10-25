package enumeration.ref2;

public class DiscountService {

    public int discount(Grade grade, int price) {
        int discountPercent = grade.getDiscountPercent();

        return discountPercent * price / 100;
    }

}
