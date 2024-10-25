### 날짜와 시간
> 1. 날짜와 시간 라이브러리가 필요한 이유
> 2. 자바 날짜와 시간 라이브러리의 역사
> 3. 자바 날짜와 시간 라이브러리 소개
> 4. 기본 날짜와 시간 - LocalDateTime
> 5. 타임존 - ZonedDateTime
> 6. 기계 중심의 시간 - Instant
> 7. 기간, 시간의 간격 - Duration, Period
> 8. 날짜와 시간의 핵심 인터페이스
> 9. 시간의 단위와 시간 필드
> 10. 날짜와 시간 조회하고 조작하기
> 11. 날짜와 시간 문자열 파싱과 포맷팅

---
### 1. 날짜와 시간 라이브러리가 필요한 이유
#### 1. 날짜와 시간 차이 계산
- 특정 날짜에서 다른 날짜까지의 정확한 일수를 계산하는것은 생각보다 복잡하다. -> 윤년, 각 달의 일수등을 고려
#### 2. 윤년 계산
- **지구가 태양을 한 바퀴 도는데 걸리는 평균 시간은 대략 365.2425**일, 즉 365일 5시간 48분 45초 정도이다
- 우리가 **현재 사용하는 달력은 1년이 보통 365**일로 되어 있어 정확히 맞지 않다.
- 이런 문제를 해결하기 위해 **4년마다 하루(2월 29일)를 추가**하는 `윤년(leap year)`을 도입한다.
    - 윤년은 4년마다 한 번씩 발생하지만, _**100년 단위일 때는 윤년이 아니며, 400년 단위일 때는 다시 윤년이다.**_
#### 3. 일광 절약 시간(Daylight Saving Time, DST)변환
- 보통 3월에서 10월은 태양이 일찍 뜨고, 나머지는 태양이 상대적으로 늦게 뜬다.
- 시간도 여기에 맞추어 1시간 앞당기거나 늦추는 제도를 DST 또는 썸머타임이라 한다.
- 국가나 지역에 따라 적용 여부와 시작 및 종료 날짜가 다르다. 즉, 시간계산 시 1시간의 오차가 발생할 수 있다.
    - 참고로 대한민국에서는 1988년 이후로는 시행하지 않는다.
#### 타임존 계산
- 세계는 다양한 타임존으로 나뉘어 있으며, 각 타임존은 `UTC(협정 세계시)`로부터의 시간 차이로 정의된다.
    - `Europe`/`London`
    - `GMT`
    - `UTC`
    - `US/Arizona` -07:00
    - `America/New_York` -05:00
    - `Asia/Seoul` +09:00
    - `Asia/Dubai` +04:00
    - `Asia/Istanbul` +03:00
    - `Asia/Shanghai` +08:00
    - `Eruope/Paris` +01:00
    - `Europe/Berlin` +01:00
- **GMT, UTC** : `London` / `UTC` / `GMT` 는 **세계 시간의 기준**이 되는 00:00 시간대이다.
- **GMT(Greenwich Mean Time, 그리니치 평균시)**
    - 처음 세계 시간을 만들 때 영국 런던에 있는 그리니치 천문대를 기준으로 했다.
- **UTC(Universal Time Coordinated, 협정 세계시)**
    - GMT 를 대체하기 위해 UTC가 도입되었다.
    - 둘 다 경도 0에 위치한 영국의 그리니치 천문대를 기준으로 하지만 시간을 정의하고 유지하는 방법에서 차이가 있다.
    - `UTC`는 ***원자 시계를 사용하여 측정한 국제적으로 합의된 시간 체계***이다.
        - 원자시계는 지구의 자전 속도가 변화하는 것을 고려하여 윤초를 추가하거나 빼는 방식으로 시간을 조정하기 때문에 정확한 시간을 유지한다.
---
### 2. 자바 날짜와 시간 라이브러리의 역사
> Java 는 날짜와 시간 라이브러리를 지속해서 업데이트 했다.

#### JDK 1.0 - java.util.Date
- 문제점
    - **타임존 처리 부족**
    - **불편한 날짜 시간 연산**
    - **불변 객체 부재** : `Date` 객체는 변경 가능(mutable)하여, 데이터가 쉽게 변경되어 버그가 발생하기 쉬웠다.
- 해결책
    - JDK 1.1 에서 `java.util.Calender` 클래스 도입으로 **타임존 지원 개선**
    - 날짜 시간 **연산을 위한 추가 메서드 제공**

#### JDK 1.1 - java.util.Calender
- 문제점
    - **사용성 저하** : `Calender` 는 사용하기 복잡하고 직관적이지 않다.
    - **성능 문제**
    - **불변 객체 부재** : `Calender` 객체도 변경 가능하여, 사이드 이펙트, 스레드 안정성 문제가 있었다.
- 해결책 : `Joda-Time` 오픈소스 라이브러리의 도입으로 **사용성, 성능, 불변성 문제 해결**

#### Joda-Time
- 문제점 : 표준 라이브러리가 아니기 때문에 프로젝트에 별도로 추가해야 했다.
- 해결책 : Java8 에서 `java.time` 패키지(JSR-310)를 표준 API로 도입

#### JDK 8(JDK 1.8) - java.time 패키지
- `java.time` 패키지는 이전 API의 문제점을 해결하면서, ***사용성, 성능, 스레드 안정성, 타임존 처리등에서 크게 개선***되었다.
- 불변 객체 설계로 ***사이드 이펙트, 스레드 안정성 보장, 보다 직관적인 API 제공으로 날짜와 시간 연산을 단순화***했다.
- `LocalDate` , `LocalTime` , `LocalDateTime` , `ZonedDateTime` , `Instant` 등의 클래스를 포함
- Joda-Time 의 많은 기능을 표준 자바 플랫폼으로 가져왔다.

---
### 3. 자바 날짜와 시간 라이브러리 소개
> 자바 날짜와 시간 라이브러리는 자바 공식 문서가 제공하는 표 하나로 정리할 수 있다.

| Class or Enum  | Year | Month |Day|Hours|Minute|Seconds*|Zone Offset|Zone ID| toString Output                           |
|----------------|-----|-------|---|---|---|---|---|---|-------------------------------------------|
| LocalDate      | V   | V     |V| | | | | | 2013-08-20                                |
| LocalTime      |     |       | |V|V|V| | | 08:16:226.943                             |
| LocalDateTime  | V   | V     |V|V|V|V| | | 2013-08-20T08:16:26.937                   |
| ZonedDateTime  | V   | V     |V|V|V|V|V|V| 2013-08-20T08:16:26.941+09:00[Asia/Tokyo] |
| OffsetDateTime | V   | V     |V|V|V|V|V| | 2013-08-20T08:16:26.954-07:00             |
| OffsetTime     |     |       | |V|V|V|V| | 08:16:26.957-07:00                        |
| Year           | V   |       | | | | | | | 2013                                      |
| Month          |     | V     | | | | | | | AUGUST                                    |
| YearMonth      |V| V     | | | | | | | 2013-08                                   |
|MonthDay| |V|V| | | | | |--08-20|
|Instant| | | | | |V| | |2013-08-20T15:16:26.355Z|
|Period|V|V|V| | |***|***| |P10D (10 days)|
|Duration| | |**|**|**|V| | |PT20H (20 hours)|
- [원문](https://docs.oracle.com/javase/tutorial/datetime/iso/overview.html)

#### LocalDate, LocalTime, LocalDateTime
- `LocalDate` : **날짜**만 표현할 때 사용 (2013-11-21)
- `LocalTime` : **시간**만을 표현할 때 사용 (08:20:30.213)
- `LocalDateTime` : `LocalDate` 와 `LocalTime을` 합한 개념 (2013-11-21T08:20:30.213)
- `Local` 이 앞에 붙는 이유는 ***세계 시간대를 고려하지 않아서 타임존이 적용되지 않기 때문***이다. -> 국내 서비스만 고려할 때

#### ZonedDateTime, OffsetDateTime
- `ZonedDateTime` : 시간대를 고려한 날짜와 시간을 표현할 때 사용 -> _타임존 포함_
    - 예) `2013-11-21T08:20:30.213+9:00[Asia/Seoul]`
    - `+9:00` 는 UTC(협정 세계시)로 부터 시간대 차이다. -> 오프셋이라 한다.
    - `Asia/Seoul` 은 타임존이다.
    - 일광 절약 시간제(썸머타임)가 적용되지 않는다.
- `OffsetDateTime` : 시간대를 고려한 날짜와 시간을 표현할 때 사용 -> _타임좀 미포함_
    - 예) `2013-11-21T08:20:30.213+9:00`
    - 타임존이 없고 오프셋만 있기 때문에 썸머타임을 알 수 없다.

#### Year, Month, YearMonth, MonthDay
- 년, 월, 년월, 달일을 각각 다룰 때 사용. 자주 사용하지는 않는다.

#### Instant
- `Instant` 는 1970년 1월 1일 0시 0분 0초(UTC)기준으로 경과한 시간으로 계산된다.
- 즉, ***Instant 내부에는 초 데이터***만 들어있다.

#### Period, Duration
- 시간의 개념은 크게 2가지로 표현할 수 있다.
    1. `특정 시점의 시간` -> 예) 이 프로젝트는 2013년 8월 16일 까지 완료해야해!
    2. `시간의 간격` -> 예) 앞으로 4년은 더 공부해야 해
- `Period` : 두 날짜 사이의 간격을 **년, 월, 일 단위**로 나타낸다.
- `Duration` : 두 날짜 사이의 간격을 **시, 분, 초(나노초)** 단위로 나타낸다.\

---
### 4. 기본 날짜와 시간 - LocalDateTime
> 가장 기본이 되는 날짜와 시간 클래스는 `LocalDate` , `LocalTime` , `LocalDateTime` 이다.

#### LocalDate
- `now()` : **현재 시간을 기준**으로 생성
- `of(year,month,day)` : **특정 날짜를 기준**으로 생성(년, 월, 일 입력)
- `plusDays(day)` : **특정 일**을 더한다.
  > 모든 날짜 클래스는 불변객체이다. 반드시 반환값을 받아서 사용 !!
  ```java
  import java.time.LocalDate;
  
  public class LocalDateMain {
      public static void main(String[] args) {
          LocalDate nowDate = LocalDate.now();
          LocalDate ofDate = LocalDate.of(2024, 4, 22);
          System.out.println("오늘 날짜 = " + nowDate);
          System.out.println("지정 날짜 = " + ofDate);
  
          // 계산(불변)
          LocalDate ofDate2 = ofDate.plusDays(10);
          System.out.println("지정 날짜+10d = " + ofDate2);
      }
  }
  ```

#### LocalTime
- 메서드들은 `LocalDate` 와 똑같으며 불변이다 !
    ```java
    public class LocalTimeMain {
        public static void main(String[] args) {
            LocalTime nowTime = LocalTime.now();
            LocalTime ofTime = LocalTime.of(16, 7, 30);
    
            System.out.println("현재 시간 = " + nowTime);
            System.out.println("지정 시간 = " + ofTime);
    
            // 계산(불변)
            LocalTime ofTime2 = ofTime.plusSeconds(30);
            System.out.println("지정 시간+30s = " + ofTime2);
        }
    }
    ```

#### LocalDateTime
- `LocalDateTime` 클래스 내부를 보면 `LocalTime` 과 `LocalDate` 를 가지고 있다.
    ```java
    public class LocalDateTime {
    private final LocalDate date;
    private final LocalTime time;
    }
    ```
- 이로인해 많은 메서드들을 갖고 있다.
    - `now()`, `of()` : 현재 또는 특정 날짜와 시간을 기준으로 생성
    - `LocalDate` 와 `LocalTime` 을 `toXxx()` 메서드로 분리 할 수 있다.
    - `LocalDateTime.of(localDate, localTime)` 으로 새로운 LocalDateTime을 만든다.
    - `plusXxx()` : 특정 날짜와 시간을 더한다.
    - `isBefore()` , `isAfter()` : 서로 다른 날짜시간을 비교한다.(true/false  반환)
    - `isEquals()` : 시간을 계산해서 시간으로만 둘을 비교 -> _**타임존이 달라도 시간적으로 같으면**_ `true`
        - 예) 서울의 9시와 UTC 의 0시는 시간적으로 같기 때문에 둘을 비교하면 true
    - equals() : _**객체의 타입, 타임존 등등 내부 데이터의 모든 구성요소가 같아야**_ `true` 반환
        - 예) 서울의 9시와 UTC의 0시는 시간적으로 같다. 하지만 타임존 데이터가 다르기 때문에 false 반환
          ```java
          import java.time.LocalDate;
          import java.time.LocalDateTime;
          import java.time.LocalTime;
        
          public class LocalDateTimeMain {
              public static void main(String[] args) {
                  LocalDateTime nowDt = LocalDateTime.now();
                  LocalDateTime ofDt = LocalDateTime.of(2017, 1, 23, 9, 0, 0);
        
                  System.out.println("현재 날짜시간 = " + nowDt);
                  System.out.println("지정 날짜시간 = " + ofDt);
        
                  // 날짜와 시간 분리
                  LocalDate localDate = ofDt.toLocalDate();
                  LocalTime localTime = ofDt.toLocalTime();
        
                  System.out.println("localDate = " + localDate);
                  System.out.println("localTime = " + localTime);
        
                  // 날짜와 시간 합치기
                  LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);
                  System.out.println("localDateTime = " + localDateTime);
        
                  // 계산(불변)
                  LocalDateTime ofDtPlus = ofDt.plusDays(1000);
                  System.out.println("지정 날짜시간+1000d = " + ofDtPlus);
                  LocalDateTime ofDtPlus1Year = ofDt.plusYears(1);
                  System.out.println("지정 날짜시간+1y = " + ofDtPlus1Year);
        
                  // 비교
                  System.out.println("현재 날짜시간이 지정 날짜시간보다 이전인가 ? " + nowDt.isBefore(ofDt));
                  System.out.println("현재 날짜시간이 지정 날짜시간보다 이후인가 ? " + nowDt.isAfter(ofDt));
                  System.out.println("현재 날짜시간과 지정 날짜시간이 같은가 ? " + nowDt.isEqual(ofDt));
              }
          }
          ```

---
### 5. 타임존 - ZonedDateTime
- `Asia/Seoul` 같은 타임존 안에는 **일광절약 시간제(썸머타임)**에 대한 정보와 `UTC+9:00`와 같은 `UTC` 로 부터 시간차이인 **오프셋** 정보를 모두 포함하고 있다.
- `Europe/London` , `GMT` , `UTC` , `Asia/Seoul +09:00` 등등이 있다.
#### ZoneId
- `ZoneId.systemDefault()` : **시스템이 사용하는 기본 ZoneId 를 반환**한다.
    - 각 PC 환경마다 다를 수 있음.
- `ZoneId.of()` : 타임존을 직접 제공해서 ZoneId 를 반환한다.
- `ZoneId` 내부에 ***썸머타임 정보, UTC와의 오프셋 정보를 포함***한다.
    ```java
    import java.time.ZoneId;
    import java.util.Set;
    
    public class ZoneIdMain {
        public static void main(String[] args) {
            Set<String> availableZoneIds = ZoneId.getAvailableZoneIds();
    
            for (String availableZoneId : availableZoneIds) {
                ZoneId zoneId = ZoneId.of(availableZoneId);
                System.out.println(zoneId + " | " + zoneId.getRules());
            }
    
            ZoneId zoneId = ZoneId.systemDefault(); // 현재 OS가 갖고있는 타임존
            System.out.println("ZoneId.systemDfault = "+ zoneId);
    
            ZoneId seoulZoneId = ZoneId.of("Asia/Seoul"); // 타임존을 입력받아 갖고옴
            System.out.println("seoulZoneId = " + seoulZoneId);
        }
    }
    ```

#### ZonedDateTime
- `ZonedDateTime` 은 `LocalDateTime` 에 시간대 정보인 `ZoneID` 가 합쳐진 것이다.
    ```java
    public class ZonedDateTime {
        private final LocalDateTime dateTime;
        private final ZoneOffset offset;
        private final ZoneId zone;
    }
    ```
- `ZonedDateTime` 은 시간대를 고려한 날짜와 시간을 표현할 때 사용한다. 시간대를 표현하는 **타임존이 포함**된다.
    - `now()` : 현재 날짜와 시간을 기준으로 생성, ZoneId 는 현재 시스템을 따른다
    - `of(...)` : 특정 날짜와 시간을 기준으로 생성한다. ZoneId 를 추가해야 한다.
        - `LocalDateTime` 에 `ZoneId`를 추가해서 생성할 수 있다.
      ```java
      public class ZonedDateTimeMain {
          public static void main(String[] args) {
              ZonedDateTime nowZdt = ZonedDateTime.now(); // 시스템 디폴즈 ZoneId 사용
              System.out.println("nowZdt = " + nowZdt);
      
              LocalDateTime ldt = LocalDateTime.of(2017, 1, 23, 9, 0, 0);
              ZonedDateTime zdt1 = ZonedDateTime.of(ldt, ZoneId.of("Asia/Seoul")); // LocalDateTime 와 ZonedID 를 사용하여 만들 수 있다.
              System.out.println("zdt1 = " + zdt1);
      
              ZonedDateTime zdt2 = ZonedDateTime.of(2018, 10, 21, 9, 0, 0, 0, ZoneId.of("Asia/Seoul"));
              System.out.println("zdt2 = " + zdt2);
      
              ZonedDateTime utcZdt = zdt2.withZoneSameInstant(ZoneId.of("UTC"));
              System.out.println("utcZdt = " + utcZdt);
          }
      }
      ```

#### OffsetDateTime
- 위에있는 `ZonedDateTime` 에서 `ZoneId` 만 빠진 클래스이다.
    ```java
    public class OffsetDateTime {
        private final LocalDateTime dateTime;
        private final ZoneOffset offset;
    }
    ```
- `OffsetDateTime` : 시간대를 고려한 날짜와 시간을 표현할 때 사용한다. **타임존이 없고 UTC로 부터의 시간차인 오프셋만 포함**된다.
    ```java
    import java.time.LocalDateTime;
    import java.time.OffsetDateTime;
    import java.time.ZoneOffset;
    
    public class OffsetDateTimeMain {
        public static void main(String[] args) {
            OffsetDateTime nowOdt = OffsetDateTime.now();
            System.out.println("nowOdt = " + nowOdt);
    
            LocalDateTime ldt = LocalDateTime.of(2024, 4, 23, 16, 20, 0);
            System.out.println("ldt = " + ldt);
    
            OffsetDateTime odt = OffsetDateTime.of(ldt, ZoneOffset.of("+02:00"));
            System.out.println("odt = " + odt);
        }
    }
    ```

#### ZonedDateTime vs OffsetDateTime
- `ZonedDateTime` 은 **구체적인 지역 시간대를 다룰 때 사용**하며, **자동으로 썸머타임(일광 절약 시간)을 처리**한다.
    - 사용자 지정 시간대에 따른 시간 계산이 필요할 때 적합하다.
- `OffsetDateTime` 은 **UTC와의 시간 차이만을 나타낼 때 사용**하며, **지역 시간대의 복잡성을 고려하지 않는다.**
    - 시간대 변환 없이 로그를 기록하고, 데이터를 저장하고 처리할 때 적합하다.
> 참고로 글로벌 서비스를 하지 않는다면 `ZonedDateTime` 과 `OffsetDateTime`을 잘 사용하지 않는다. 달달 외울 필요는 없다.

---
### 6. 기계 중심의 시간 - Instant
- `Instant` 는 UTC 기준으로 ***시간의 한 지점***을 나타낸다.
- 쉽게말해 1970년 1월 1일 0시 0분 0초(UTC기준)를 기준으로 경과한 시간을 말한다.1
- `Instant` 내부에는 초 데이터(나노초 포함)만 들어있기 때문에 _**날짜와 시간을 계산할 때는 적합하지 않다.**_
  ```java
  public class Instant {
      private final long seconds;
      private final int nanos;
      // ....
  }
  ```
#### 참고 - Epoch 시간
- `Epoch time(체포크 시간)` 또는 `Unix timestamp` 는 컴퓨터 시스템에서 시간을 나타내는 방법 중 하나이다.
- 이는 **1970년 1월 1일 00:00:00 UTC부터 현재까지 경과된 시간**을 초 단위로 표현한 것.
- `Instant` 는 바로 이 `Epoch time`을 다루는 클래스이다.

#### Instant 장단점
- 장점
    1. **시간대 독립성** : `UTC`를 기준으로 하기 때문에 **시간대에 영향을 받지 않는다.**
    2. **고정된 기준점** : `Instant` 는 1970년 1월 1일 UTC를 기준으로 하기 때문에 **시간 계산 및 비교가 명확하고 일관**된다.
- 단점
    1. **사용자 친화적이지 않다** : 기계적인 시간 처리에는 적합하지만, **사람이 읽고 이해하기에는 직관적이지 않다.**
    2. **시간대 정보 부재** : `Instant` 에는 **시간대 정보가 포함되어 있지않아 추가적인 작업이 필요**하다.

#### 사용예시
- `now()` : **UTC 기준으로 현재** 시간의 Instant 를 생성
- `from()` : 다른 타입의 날짜와 시간을 기준으로 Instant 생성(`LocalDateTime` 은 사용할 수 없다.)
- `ofEpochSecond()` : **에포크 시간을 기준**으로 Instant를 생성. 0초를 선택하면 1970년1월1일 0시0분0초 생성
- `plusSeconds()` : 에포크 시간에서 초를 더한다.
- `getEpochSecond()` : 에포크 시간으로 부터 흐른 초를 반환
  ```java
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
  ```

---
### 7. 기간, 시간의 간격 - Duration, Period
- 시간의 개념은 `특정 시점의 시간` 과 `시간의 간격`으로 표현할 수 있다
    - 예) 앞으로 4년은 더 공부해야 해 , 이 프로젝트트 3개월 남았어 등등
- `Duration` , `Period` 는 시간의 간격을 표현하는데 사용하는 클래스이다.

  |구분|`Period`|`Duration`|
    |---|---|---|
  |단위|년, 월, 일|시간, 분, 초, 나노초|
  |사용 대상|날짜|시간|
  |주요 메서드|`getYears()` , `getMonths()` , `getDays()`|`toHours()` , `toMinutes()` , `getSeconds()` , `getNano()`|

#### Period
- **두 날짜 사이의 간격을 년, 월, 일 단위**로 나타낸다.
  ```java
  public class Period {
      private final int years;
      private final int months;
      private final int days;
  }
  ```
- `of()` : 특정 기간을 지정해서 Period 를 생성
- `between()` : 특정 기간의 차이를 알고싶을 때는 특정 `날짜 객체(LocalDate)` 를 인자로 넣으면 `Period` 가 반환된다.
- `LocalDate` 객체에 특정 기간을 더할 때도 `Period` 객체를 이용할 수 있다.
  ```java
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
  ```

#### Duration
- ***두 시간 사이의 간격을 시, 분, 초(나노초) 단위***로 나타낸다.
  ```java
  public class Duration {
      private final long seconds;
      private final int nanos;
  }
  ```
- `of()` : 특정 시간을 지정해서 `Duration` 생성
- 특정 시간의 차이를 구할 때는 `Period` 와 같이 `between()` 메서드 사용
- 계산에 사용할 때는 `시간객체(LocalTime)`의 사용된다.
  ```java
  public class DurationMain {
      public static void main(String[] args) {
          Duration duration = Duration.ofMinutes(30);
          System.out.println("duration = " + duration);
  
          LocalTime lt = LocalTime.of(16, 0);
          System.out.println("lt = " + lt);
  
          // 계산에 사용
          LocalTime plusTime = lt.plus(duration);
          System.out.println("더한 시간: " + plusTime);
  
          // 시간 차이
          LocalTime startTime = LocalTime.of(9, 0);
          LocalTime endTime = LocalTime.of(17, 30);
          Duration between = Duration.between(startTime, endTime);
          System.out.println("차이: " + between.getSeconds() + "초");
          System.out.println("근무 시간: " + between.toHours() + "시간 " + between.toMinutesPart() + "분");
      }
  }
  ```
  
---
### 8. 날짜와 시간의 핵심 인터페이스

> 시간의 개념은 `특정 시점의 시간(시각)` 과 `시간의 간격(기간)`으로 나눌 수 있다.


- **특정 시점의 시간** : `Temporal`(`TemporalAccessor` 포함) 인터페이스를 구현한다.
    - `LocalDateTime` , `LocalDate`, `LocalTime` , `ZonedDateTime`, `OffsetDateTime` , `Instant` 등이 있다.
- **시간의 간격** : `TemporalAmount` 인터페이스를 구현한다.
    - 구현으로 `Period` , `Duration` 이 있다.

### `TemporalAccessor` 인터페이스

- 날짜와 시간을 읽기 위한 기본 인터페이스
- ***특정 시점의 날짜와 시간 정보를 읽을 수 있는 최소한의 기능을 제공***한다.

### `Temporal` 인터페이스

- `TemporalAccessor` 인터페이스의 하위 인터페이스로 날짜와 시간을 조작(추가,빼기 등)하기 위한 기능을 제공한다.
- 쉽게 말해 `TemporalAccessor` 는 **읽기 전용 접근**을, `Temporal` 은 **읽기와 쓰기(조작)** 모두를 지원

### `TemporalAmount` 인터페이스

- 시간의 간격을 나타내며 **특정 날짜에 일정 기간을 더하거나 빼는 데 사용**한다.

---

### 9. 시간의 단위와 시간 필드

> `TemporalUnit(ChronoUnit)` 인터페이스는 **시간의 단위**를 뜻하고, `TemporalField(ChronoField)` 는 **시간의 각 필드**를 뜻한다.

### 시간의 단위 - TemporalUnit

- `TemporalUnit` 인터페이스는 **날짜와 시간을 측정하는 단위**를 나타내며, 주로 사용되는 구현체는 `java.time.temporal.ChronoUnit` 열거형으로 구현되어 있다.
- `ChoronoUnit` 은 다양한 시간 단위를 제공한다(`Unit`은 단위를 뜻한다)

#### 시간단위

| ChronoUnit | 설명       |
|------------|----------|
| `NANOS`    | 나노초 단위   |
| `MICROS`   | 마이크로초 단위 |
| `MILLIS`   | 밀리초 단위   |
| `SECONDS`  | 초 단위     |
| `MINUTES`  | 분 단위     |
| `HOURS`    | 시간 단위    |

#### 날짜 단위

| ChronoUnit  | 설명     |
|-------------|--------|
| `DAYS`      | 일 단위   |
| `WEEKS`     | 주 단위   |
| `MONTHS`    | 월 단위   |
| `YEARS`     | 년 단위   |
| `DECADES`   | 10년 단위 |
| `CENTURIES` | 세기 단위  |
| `MILLENNIA` | 천년 단위  |

#### 기타 단위

| ChronoUnit | 설명        |
|------------|-----------|
| `ERAS`     | 시대 단위     |
| `FOREVER`  | 무한대의 시간단위 |

#### ChronoUnit의 주요 메서드

| 메서드 이름                        | 설명                                                 |
|-------------------------------|----------------------------------------------------|
| `between(Temporal, Temporal)` | 두 Temporal 객체 사이의 시간을 ChronoUnit 단위로 측정하여 반환한다.    |
| `isDateBased()`               | 현재 ChronoUnit이 날짜 기반 단위인지 여부를 반환한다.                |
| `isTimeBased()`               | 현재 ChronoUnit이 시간 기반 단위인지 여부를 반환한다.                |
| `isSupportedBy(Temporal)`     | 주어진 Temporal 객체가 현재 ChronoUnit 단위를 지원하는지 여부를 반환한다. |
| `getDuration()`               | 현재 ChronoUnit의 기간을 Duration 객체로 반환한다.              |

#### ChronoUnit 예제

- **두 날짜 또는 시간 사이의 차이를 해당 단위로 쉽게 계산**할 수 있다.
- 예를 들면 두 `LocalTime(시간)`과의 차이를 초, 분 단위로 구한다.

```java
  import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class ChronoUnitMain {
    public static void main(String[] args) {
        ChronoUnit[] values = ChronoUnit.values();
        for (ChronoUnit value : values) {
            System.out.println("value = " + value); // 내부적으로 toString() 오버라이딩이 되어있다.
        }

        System.out.println("HOURS = " + ChronoUnit.HOURS);
        System.out.println("HOURS.duration = " + ChronoUnit.HOURS.getDuration().getSeconds());
        System.out.println("DAYS = " + ChronoUnit.DAYS);
        System.out.println("DAYS.duration = " + ChronoUnit.DAYS.getDuration().getSeconds());

        // 차이 구하기
        LocalTime startTime = LocalTime.of(9, 0, 0);
        LocalTime endTime = LocalTime.of(17, 30, 0);

        long secondsBetween = ChronoUnit.SECONDS.between(startTime, endTime);
        System.out.println("secondsBetween = " + secondsBetween);

        long minutesBetween = ChronoUnit.MINUTES.between(startTime, endTime);
        System.out.println("minutesBetween = " + minutesBetween);
    }
}
  ```

### 시간 필드 - ChronoField

- `ChronoField` 는 **날짜 및 시간을 나타내는데 사용**되는 열거형이다.
- 이 열거형은 ***다양한 필드를 통해 날짜와 시간의 특정 부분***을 나타낸다.
- 여기서 `Field` 는 날짜와 시간 중에 있는 특정 필드들을 뜻한다.
    - 2024년 8월 16일이면 필드는 다음과 같다.
        - `YEAR` : 2024
        - `MONTH_OF_YEAR` : 8
        - `DAY_OF_MONTH` : 16
    - 단순히 시간의 단위 하나하나를 뜻하는 `ChronoUnit` 과는 다르다. `ChronoField` 를 사용해야 **날짜와 시간의 각 필드 중에 원하는 데이터를 조회**할 수 있다.

#### 연도 관련 필드

| 필드 이름         | 설명                |
|---------------|-------------------|
| `ERA`         | 연대(서기 또는 기원전)     |
| `YEAR_OF_ERA` | 연대 내의 연도          |
| `YEAR`        | 연도                |
| `EPOCH_DAY`   | 1970-01-01 부터의 일수 |

#### 월 관련 필드

| 필드 이름             | 설명           |
|-------------------|--------------|
| `MONTH_OF_YEAR`   | 월(1월 = 1)    |
| `PROLEPTIC_MONTH` | 연도를 월로 확장한 값 |

#### 주 및 일 관련 필드

| 필드 이름                          | 설명                       |
|--------------------------------|--------------------------|
| `DAY_OF_WEEK`                  | 요일(월요일 = 1)              |
| `ALIGNED_DAY_OF_WEEK_IN_MONTH` | 월의 첫 번째 요일을 기준으로 정렬된 요일  |
| `ALIGNED_DAY_OF_WEEK_IN_YEAR`  | 연의 첫 번째 요일을 기준으로 정렬된 요일  |
| `DAY_OF_MONTH`                 | 월의 일(1일 = 1)             |
| `DAY_OF_YEAR`                  | 연의 일(1월 1일 = 1)          |
| `EPOCH_DAY`                    | 유닉스 에폭(1970-01-01)부터의 일수 |

#### 시간 관련 필드

| 필드 이름                | 설명                    |
|----------------------|-----------------------|
| `HOUR_OF_DAY`        | 시간(0-23)              |
| `CLOCK_OF_DAY`       | 시계 시간(1-24)           |
| `HOUR_OF_AMPM`       | 오전/오후 시간(0-11)        |
| `CLOCK_HOUR_OF_AMPM` | 오전/오후 시계 시간(1-12)     |
| `MINUTE_OF_DAY`      | 분(0-59)               |
| `SECOND_OF_DAY`      | 초(0-59)               |
| `NANO_OF_DAY`        | 초의 나노초(0-999,999,999) |
| `MICRO_OF_DAY`       | 초의 마이크로초(0-999,999)   |
| `MILLI_OF_DAY`       | 초의 밀리초(0-999)         |

#### 기타 필드

| 필드 이름             | 설명                   |
|-------------------|----------------------|
| `AMPM_OF_DAY`     | 하루의 AM/PM 부분         |
| `INSTANT_SECONDS` | 초를 기준으로 한 시간         |
| `OFFSET_SECONDSD` | UTC/GMT 에서의 시간 오프셋 초 |

#### 주요 메서드

| 메서드              | 반환 타입          | 설명                                                               |
|------------------|----------------|------------------------------------------------------------------|
| `getBaseUnit()`  | `TemporalUnit` | 필드의 기본 단위를 반환한다.(분 필드의 기본 단위는 `ChronoUnit.MINUTES` 이다.)          |
| `getRangeUnit()` | `TemporalUnit` | 필드의 범위 단위를 반환한다.(`MONTH_OF_YEAR`의 범위 단위는 `ChronoUnit.YEARS` 이다.) |
| `isDataBased()`  | `boolean`      | 필드가 주로 날짜를 기반으로 하는지 여부(`YEAR` 와 같은 날짜 기반 필드는 `true` 반환)          |
| `isTimeBased()`  | `boolean`      | 필드가 주로 시간을 기반으로 하는지 여부(`HOUR_OF_DAY`와 같은 시간 기반 필드는 `ture` 반환     |
| `range()`        | `ValueRange`   | 필드가 가질 수 있는 값의 유효 범위를 `ValueRange` 객체로 반환(최소값과 최대값을 제공)          |

#### 예제 코드

- `TemporalUnit` 과 `TemporalField` 는 단독으로 사용하지 않고 날짜와 시간을 조회하거나 조작할 때 같이 사용한다.
    ```java
    public class ChronoFieldMain {
        public static void main(String[] args) {
            ChronoField[] values = ChronoField.values();
            for (ChronoField value : values) {
                System.out.println("value.range() = " + value.range());
            }
    
            System.out.println("MONTH_OF_YEAR.range() = " + ChronoField.MONTH_OF_YEAR.range());
    
            System.out.println("DAY_OF_MONTH.range() = " + ChronoField.DAY_OF_MONTH.range());
        }
    }
    ```

---

### 10. 날짜와 시간 조회하고 조작하기

#### 날짜와 시간 조회하기

- 날짜와 시간이 있는 객체(`LocalDateTime`)의 특정 시간이나 날짜같은 필드를 조회할 때는 `ChronoField` 가 사용된다.
    ```java
    import java.time.LocalDateTime;
    import java.time.temporal.ChronoField;
    
    public class GetTimeMain {
        public static void main(String[] args) {
            LocalDateTime dt = LocalDateTime.of(2017, 1, 23, 9, 30, 23);
            System.out.println("YEAR = " + dt.get(ChronoField.YEAR));
            System.out.println("MONTH_OF_YEAR = " + dt.get(ChronoField.MONTH_OF_YEAR));
            System.out.println("DAY_OF_MONTH = " + dt.get(ChronoField.DAY_OF_MONTH));
            System.out.println("HOUR_OF_DAY = " + dt.get(ChronoField.HOUR_OF_DAY));
            System.out.println("MINUTE_OF_HOUR = " + dt.get(ChronoField.MINUTE_OF_HOUR));
            System.out.println("SECOND_OF_MINUTE = " + dt.get(ChronoField.SECOND_OF_MINUTE));
    
            System.out.println("\n==== 편의 메서드 제공 ====");
            System.out.println("YEAR = " + dt.getYear());
            System.out.println("MONTH_OF_YEAR = " + dt.getMonthValue());
            System.out.println("DAY_OF_MONTH = " + dt.getDayOfMonth());
            System.out.println("HOUR_OF_DAY = " + dt.getHour());
            System.out.println("MINUTE_OF_HOUR = " + dt.getMinute());
            System.out.println("SECOND_OF_MINUTE = " + dt.getSecond());
    
            System.out.println("\n==== 편의 메서드 없는 경우 ====");
            System.out.println("MINUTE_OF_DAY = " + dt.get(ChronoField.MINUTE_OF_DAY));
            System.out.println("SECOND_OF_DAY = " + dt.get(ChronoField.SECOND_OF_DAY));
        }
    }
    ```
    - 위 코드처럼 `get()` 메서드의 `ChoronoField` 를 사용하였는데, `get()` 메서드는 `TemporalAccessor` 인터페이스의 메서드를 `LocalDateTime` 이 오버라이딩
      한것이다.
    - 그래서 `get()` 을 호출하면서 `TemporalAccessor` 의 구현체인 `ChronoField` 를 인자로 넘겨준 것이다.
    - 하지만 많은 코드를 짜다보면 `get()` 을 호출하면 코드가 길어지고 불편하다. 그래서 **편의메서드**를 제공한다.
    - 편의메서드에 없는 기능은 그 때 get() 을 사용하면 된다.

### 날짜와 시간 조작하기

- 날짜와 시간을 조작하려면 **어떤 시간 단위(Unit)을 변경**할 지 선택해야 한다. 이 때 `ChronoUnit` 이 사용된다.
    ```java
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
    ```
    - `LocalDateTime` 을 포함한 특정 시점의 시간을 제공하는 클래스는 모두 **Temporal 인터페이스를 구현**한다.
    - `plus()` 메서드를 호출할 때는 **더할 숫자**와 **시간의 단위(Unit)**을 전달해주면 된다.
    - 똑같이 편의메서드도 제공된다, `plus(10, ChronoUnit.YEARS)` -> `plusYears(10)`
    - `Period` 객체를 통해서 조작이 가능하다.

#### 주의점

- `TemporalAccessor` 와 `Temporal` 인터페이스의 구현체가 `LcoalDateTime`, `LocalDate` 등등 이기 때문에 `get()`, `plus()` 와 같은 메서드를 통해 조회,
  조작을 쉽게 할 수 있다.
- 하지만 모든 시간 필드를 조회할 수 있는 것은 아니다.
    - `LocalDate` 에는 날**짜정보만 있기 때문에 시간 정보를 조회할려고하면 예외**가 터진다.
        ```java
        import java.time.LocalDate;
        import java.time.temporal.ChronoField;
      
        public class IsSupportedMain1 {
            public static void main(String[] args) {
                LocalDate now = LocalDate.now();
                int minute = now.get(ChronoField.SECOND_OF_MINUTE);
                System.out.println("minute = " + minute);
            }
        }
        ```

- 이런 경우를 대비하여 `TemporalAccessor` 와 `Temporal` 인터페이스는 **현재 타입에서 특정 시간 단위나 필드를 사용할 수 있는지 확인할 수 있는 메서드를 제공**한다.
  ```java
  public class IsSupportedMain2 {
      public static void main(String[] args) {
          LocalDate now = LocalDate.now();
          if (now.isSupported(ChronoField.SECOND_OF_MINUTE)) { // 사용가능한 경우 조회하여 출력
              int minute = now.get(ChronoField.SECOND_OF_MINUTE);
              System.out.println("minute = " + minute);
          }
      }
  }
  ```

### 날짜와 시간 조작하기 2

- `Temporal.with()` 메서드는 **날짜와 시간의 특정 필드의 값만 변경**할 수 있다.
- 똑같이 편의메서드가 제공된다(`withYear()` 등등)
- `TemporalAdjuster` 를 사용하면 복잡한 날짜 계산도 가능하다.(예를 들면 다음주 금요일, 이번달 마지막 일요일 등등)
  ```java
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
  ```

#### TemporalAdjuster 인터페이스

```java
public interface TemporalAdjuster {
    Temporal adjustInto(Temporal temporal);
}
```

- Java 에서는 `TemporalAdjuster` 인터페이스 구현체들을 `TemporalAdjusters` 에 다 만들어 두었다.
    - `TemporalAdjusters.next(DayOfWeek.FRIDAY)` : 다음주 금요일
    - `TemporalAdjusters.lastInMonth(DayOfWeek.SUNDAY)` : 이번 달의 마지막 일요일

#### TemporalAdjusters 클래스가 제공하는 주요 메서드

| 메서드                   | 설명                                                         |
|-----------------------|------------------------------------------------------------|
| `dayOfWeekInMonth`    | 주어진 요일이 몇 번째인지에 따라 날짜를 조정한다.                               |
| `firstDayOfMonth`     | 해당 월의 첫째 날로 조정한다.                                          |
| `firstDayOfNextMonth` | 다음 달의 첫째 날로 조정한다.                                          |
| `firstDayOfNextYear`  | 다음 해의 첫째 날로 조정한다.                                          |
| `firstDayOfYear`      | 해당 해의 첫째 날로 조정한다.                                          |
| `firstInMonth`        | 주어진 요일 중 해당 월의 첫 번째 요일로 조정한다.                              |
| `lastDayOfMonth`      | 해당 월의 마지막 날로 조정한다.                                         |
| `lastDayOfNextMonth`  | 다음 달의 마지막 날로 조정한다.                                         |
| `lastDayOfNextYear`   | 다음 해의 마지막 날로 조정한다.                                         |
| `lasyDayOfYear`       | 해당 해의 마지막 날로 조정한다.                                         |
| `lastInMonth`         | 주어진 요일 중 해당 월의 마지막 요일로 조정한다.                               |
| `next`                | 주어진 요일 이후의 가장 가까운 요일로 조정한다.                                |
| `nextOrSame`          | 주어진 요일 이후의 가장 가까운 요일로 조정하되, 현재 날짜가 주어진 요일인 경우 현재 날짜를 반환한다. |
| `previous`            | 주어진 요일 이전의 가장 가까운 요일로 조정한다.                                |
| `previousOrSame`      | 주어진 요일 이전의 가장 가까운 요일로 조정하되, 현재 날짜가 주어진 요일인 경우 현재 날짜를 반환한다. |

---
### 11. 날짜와 시간 문자열 파싱과 포맷팅
> **포맷팅** : 날짜와 시간 데이터를 원하는 포맷의 문자열로 변경하는 것. `Date` -> `String`<br>
> **파싱** : 문자열을 날짜와 시간 데이터로 변경하는 것. `String` -> `Date`

#### 예제 코드
- `LocalDate` 객체를 문자열 데이터로 **포맷팅** 할때는 `DateTimeFormatter` 클래스의 `ofPattern()` 메서드를 호출하여 날짜 형식을 정해주면 된다.
- 반대로 문자열 데이터를 날짜 객체로 **파싱** 할때는 Loc`alDate 클래스의 `parse()` 메서드를 호출하여 문자열과, 포맷형식을 인자로 전달해준다.
    ```java
    import java.time.LocalDate;
    import java.time.format.DateTimeFormatter;
    
    public class FormattingMain1 {
        public static void main(String[] args) {
            // 포맷팅 : 날짜 -> 문자
            LocalDate date = LocalDate.of(2024, 4, 27);
            System.out.println("date = " + date); // 기본 출력은 YYYY-MM-dd 형식으로 출력(ISO 표준)
    
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일"); // 대소문자 구분해야함
            String formattedDate = date.format(formatter);
            System.out.println("날짜와 시간 포맷팅 = " + formattedDate);
    
            // 파싱 : 문자 -> 날짜
            String input = "2018년 10월 23일";
            LocalDate parsedDate = LocalDate.parse(input, formatter);
            System.out.println("문자열 파싱 날짜와 시간 : " + parsedDate);
        }
    }
    ```

#### DateTimeFormatter 패턴
[공식사이트](https://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html#patterns)

### 문자열을 날짜와 시간으로 파싱
- 파싱을 할 때에는 문자열의 어떤 부분이 년이고,월이고 일인지 각각의 위치를 정해서 읽어야 한다.
- 똑같이 포맷팅을 할 때는 `DateTimeFormatter.ofPattern()` 을 이용하고 파싱할 때는 `LocalDateTime.parse()` 를 이용하면 된다.
  ```java
  import java.time.LocalDateTime;
  import java.time.format.DateTimeFormatter;
  
  public class FormattingMain2 {
      public static void main(String[] args) {
          // 포맷팅: 날짜와 시간을 문자로
          LocalDateTime now = LocalDateTime.of(2024, 04, 27, 16, 23, 30);
          DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); // 가장 많이 쓰는 형식
          String formattedDateTime = now.format(formatter);
          System.out.println("기본 출력 형식: " + now);
          System.out.println("날짜와 시간 포맷팅: " + formattedDateTime);
  
          // 파싱: 문자를 날짜와 시간으로
          String dateTimeString = "2018-10-23 10:30:12";
          LocalDateTime parsedDateTime = LocalDateTime.parse(dateTimeString, formatter);
          System.out.println("문자열 파싱 날짜와 시간: " + parsedDateTime);
      }
  }
  ```

---