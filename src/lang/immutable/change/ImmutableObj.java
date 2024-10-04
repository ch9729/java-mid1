package lang.immutable.change;

public class ImmutableObj {

    private final int value;

    public ImmutableObj(int value) {
        this.value = value;
    }

    public ImmutableObj add(int addValue){
        int result = value + addValue;
        ImmutableObj immutableObj = new ImmutableObj(result);    //예) value값이 10이고 add 값이 20일경우 result 30인 객체를 만든다
        return immutableObj;
    }

    public int getValue() {
        return value;
    }
}
