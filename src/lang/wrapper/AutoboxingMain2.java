package lang.wrapper;

public class AutoboxingMain2 {

    public static void main(String[] args) {
        //Primitive -> Wrapper 기본형을 참조타입으로
        int value = 7;
        Integer boxedValue = value; // 오토 박싱(Auto-boxing)
        System.out.println(boxedValue);

        //Wrapper -> Primitive 참조타입을 다시 기본형으로
        int unboxedValue = boxedValue;  // 오토 언박싱(Auto-unboxing)
        System.out.println(unboxedValue);
    }
}
