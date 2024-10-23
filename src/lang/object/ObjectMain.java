package lang.object;

public class ObjectMain {

    public static void main(String[] args) {
        Child child = new Child();
        child.childMethod();
        child.parentMethod();
        
        //toString()은 Object클래스의 메서드
        //toString() = 객체에 대한 정보 출력
        String string = child.toString();
        System.out.println(string);
    }
}
