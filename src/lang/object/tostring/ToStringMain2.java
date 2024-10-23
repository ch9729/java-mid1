package lang.object.tostring;

public class ToStringMain2 {

    public static void main(String[] args) {
        Car car = new Car("modelY");
        Dog dog1 = new Dog("멍멍이1", 2);
        Dog dog2 = new Dog("멍멍이", 3);

        System.out.println("단순 toString 호출");
        System.out.println(car.toString());
        System.out.println(dog1.toString());
        System.out.println(dog2.toString());

        System.out.println("2. println 내부에서 toString 호출");
        System.out.println(car);
        System.out.println(dog1);
        System.out.println(dog2);

        System.out.println("3. Object 다형성 활용");
        ObjectPrinter.print(car);
        ObjectPrinter.print(dog1);
        ObjectPrinter.print(dog2);

        //dog1,2 인스턴스는 toString()을 오버라이딩 하여 참조값이 나오지 않는다.
        //이걸 나오도록 해주는 법
        String result1 = Integer.toHexString(System.identityHashCode(dog1));
        System.out.println(result1);
        String result2 = Integer.toHexString(System.identityHashCode(dog2));
        System.out.println(result2);

    }
}
