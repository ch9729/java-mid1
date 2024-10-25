package lang.wrapper;

public class AutoboxingMain1 {

    public static void main(String[] args) {
        //Primitive -> Wrapper 기본형을 참조타입으로
        int value = 7;
        Integer boxedValue = Integer.valueOf(value);
        System.out.println(boxedValue);

        //Wrapper -> Primitive 참조타입을 다시 기본형으로
        int unboxedValue = boxedValue.intValue();
        System.out.println(unboxedValue);
    }
}
