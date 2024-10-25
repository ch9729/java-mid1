package lang.wrapper;

public class WrapperUtilsMain {

    public static void main(String[] args) {
        Integer i1 = Integer.valueOf(10);   //숫자를 래퍼 객체 변환
        Integer i2 = Integer.valueOf("10"); //문자를 래퍼 객체 변환
        int i3 = Integer.parseInt("10");    //문자열을 기본형으로 변환

        //비교
        int compareResult = i1.compareTo(20);   //내 값과 인수로 넘어온 값을 비교 (내 값이 크면 1, 같으면 0, 작으면 -1 반환)
        System.out.println("compareResult = " + compareResult);

        //산술 연산
        System.out.println("sum : " + Integer.sum(10,20));
        System.out.println("min : " + Integer.min(10,20));
        System.out.println("max : " + Integer.max(10,20));
    }
}
