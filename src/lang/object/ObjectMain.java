package lang.object;

public class ObjectMain {

    public static void main(String[] args) {
        Child child = new Child();
        child.childMethod();
        child.parentMethod();
        
        //toString() = Object의 메서드 : 객체의 정보를 제공
        String string = child.toString();
        System.out.println(string);
    }
}
