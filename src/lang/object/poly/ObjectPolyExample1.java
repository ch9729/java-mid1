package lang.object.poly;

public class ObjectPolyExample1 {

    public static void main(String[] args) {
        Dog dog = new Dog();
        Car car = new Car();

        action(dog);
        action(car);
    }
    //모든 객체는 Object를 담울수 있다.
    private static void action(Object obj) {
        // obj.sound();     obj는 sound 메서드가 없다.

        //객체에 맞는 다운캐스팅 필요
        if(obj instanceof Dog dog) {
            dog.sound();
        }else if(obj instanceof Car car) {
            car.move();
        }
    }
}
