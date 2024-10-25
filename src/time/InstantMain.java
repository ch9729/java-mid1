package time;

import java.time.Instant;
import java.time.ZonedDateTime;

public class InstantMain {

    public static void main(String[] args) {
        // 생성
        Instant now = Instant.now();
        System.out.println("now = " + now); // UTC 기준이기 때문에 현재시간에서 9시간 뺸 시간이 출력

        ZonedDateTime zdt = ZonedDateTime.now(); // 타임존 정보를 이용하여 변환
        Instant from = Instant.from(zdt);
        System.out.println("from = " + from);

        Instant epochStart = Instant.ofEpochSecond(0); // 에포크시간에서 0초 후
        System.out.println("epochStart = " + epochStart);

        // 계산
        Instant later = epochStart.plusSeconds(3600); //  3600초(1시간) 더하기
        System.out.println("later = " + later);

        // 조회
        long laterEpochSecond = later.getEpochSecond();
        System.out.println("laterEpochSecond = " + laterEpochSecond);
    }
}
