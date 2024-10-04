package lang.immutable.address;

public class MemberMainV1 {

    public static void main(String[] args) {
        Address address = new Address("서울");
        MemberV1 memberA = new MemberV1("회원1", address);
        MemberV1 memberB = new MemberV1("회원2", address);

        //회원1, 회원2의 주소는 서울
        System.out.println("memberA = " + memberA);
        System.out.println("memberB = " + memberB);
        
        //회원2의 주소를 변경해야함
        Address address1 = memberB.getAddress();
        address1.setValue("부산");
        //memberB.getAddress().setValue("부산");   위와 동일

        System.out.println("memberA = " + memberA);
        System.out.println("memberB = " + memberB);
    }
}
