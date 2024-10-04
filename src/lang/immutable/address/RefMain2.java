package lang.immutable.address;

public class RefMain2 {

    public static void main(String[] args) {
        //참조형 변수는 하나의 인스턴스를 공유할 수 있다.
        ImmutableAddress a = new ImmutableAddress("서울");
        ImmutableAddress b = a; //참조값 대입은 막을 방법이 없다.

        System.out.println("a = " + a);
        System.out.println("b = " + b);

        //setValue는 없다. final Value를 씀으로써 Value는 변경이 불가
        b = new ImmutableAddress("부산");
        System.out.println("a = " + a);
        System.out.println("b = " + b);
    }
}
