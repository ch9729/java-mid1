package time;

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
