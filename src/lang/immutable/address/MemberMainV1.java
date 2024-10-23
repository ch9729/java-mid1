package lang.immutable.address;

public class MemberMainV1 {

    public static void main(String[] args) {
        Address address = new Address("서울");

        MemberV1 memberA = new MemberV1("회원A", address);
        MemberV1 memberB = new MemberV1("회원B", address);

        //회원A,B 모두 처음 주소는 모두 서울
        System.out.println("memberA = " + memberA);
        System.out.println("memberB = " + memberB);

        //회원B의 주소는 부산으로 변경
        //memberB.getAddress().setValue("부산");
        //Address address1 = memberB.getAddress();  16번코드와 동일
        //address1.setValue("부산");  16번코드와 동일

        System.out.println("부산 -> memberB.address");
        System.out.println("memberA = " + memberA);
        System.out.println("memberB = " + memberB);
    }
}
