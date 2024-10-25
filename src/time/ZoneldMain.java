package time;

import java.time.ZoneId;
import java.util.Set;

public class ZoneldMain {

    public static void main(String[] args) {
        for (String availableZoneId : ZoneId.getAvailableZoneIds()) {
            ZoneId zoneId = ZoneId.of(availableZoneId);
            System.out.println(zoneId + " | " + zoneId.getRules());
        }

        ZoneId zoneId = ZoneId.systemDefault(); // 현재 OS가 갖고있는 타임존
        System.out.println("ZoneId.systemDfault = "+ zoneId);

        ZoneId seoulZoneId = ZoneId.of("Asia/Seoul"); // 타임존을 입력받아 갖고옴
        System.out.println("seoulZoneId = " + seoulZoneId);
    }
}
