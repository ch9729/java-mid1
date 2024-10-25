package time;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;

public class ChangeTimePlusMain {

    public static void main(String[] args) {
        LocalDateTime dt = LocalDateTime.of(2018, 10, 20, 9, 30, 20);
        System.out.println("dt = " + dt);

        LocalDateTime plusDt = dt.plus(10, ChronoUnit.YEARS); // 10년후
        System.out.println("plusDt = " + plusDt);

        LocalDateTime plusDt2 = dt.plusYears(10); // 편의 메서드
        System.out.println("plusDt2 = " + plusDt2);

        Period period = Period.ofYears(10);
        LocalDateTime plusDt3 = dt.plus(period); // Period 객체를 통해 조작가능
        System.out.println("plusDt3 = " + plusDt3);
    }
}
