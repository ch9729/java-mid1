package time;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAdjusters;

public class ChangeTimeWithMain {

    public static void main(String[] args) {
        LocalDateTime dt = LocalDateTime.of(2024, 4, 26, 19, 5, 10);
        System.out.println("dt = " + dt);

        LocalDateTime changeDt = dt.with(ChronoField.YEAR, 2025); // 기존의 2017을 2020으로 변경
        System.out.println("changeDt = " + changeDt);

        LocalDateTime changeDt2 = dt.withYear(2026); // 편의 메서드 제공
        System.out.println("changeDt2 = " + changeDt2);

        // TemporalAdjusters 사용
        // 앞서 with() 는 특정 필드 1개의 데이터를 변경할 떄 썻지만 복잡하게 변경하는 경우가 있다.
        LocalDateTime with1 = dt.with(TemporalAdjusters.next(DayOfWeek.FRIDAY));
        System.out.println("기준 날짜 : " + dt);
        System.out.println("다음주 금요일 : " + with1);

        // 이번 달의 마지막 일요일
        LocalDateTime with2 = dt.with(TemporalAdjusters.lastInMonth(DayOfWeek.SUNDAY));
        System.out.println("같은 달의 마지막 일요일 : " + with2);
    }
}
