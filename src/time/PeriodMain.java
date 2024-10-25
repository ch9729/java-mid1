package time;

import java.time.LocalDate;
import java.time.Period;

public class PeriodMain {

    public static void main(String[] args) {
        // 생성
        Period period = Period.ofDays(10);
        System.out.println("period = " + period);

        // 계산에 사용
        LocalDate currentDate = LocalDate.of(2024, 4, 25);
        LocalDate plusDate = currentDate.plus(period);
        System.out.println("plusDate = " + plusDate);

        // 기간 차이
        LocalDate startDate = LocalDate.of(2024, 04, 25);
        LocalDate endDate = LocalDate.of(2024, 7, 29);

        Period between = Period.between(startDate, endDate);
        System.out.println("between = " + between);
        System.out.println("기간 : " + between.getMonths() + " 개월 " + between.getDays() + "일");
    }
}
