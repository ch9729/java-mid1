package lang.immutable.address;

public class MemberMainV2 {

    public static void main(String[] args) {
        ImmutableAddress address = new ImmutableAddress("서울");
        MemberV2 memberA = new MemberV2("회원1", address);
        MemberV2 memberB = new MemberV2("회원2", address);

        //회원1, 회원2의 주소는 서울
        System.out.println("memberA = " + memberA);
        System.out.println("memberB = " + memberB);
        
        //회원2의 주소를 변경해야함
        //memberB.getAddress().getValue("부산");    //컴파일 오류
        memberB.setAddress(new ImmutableAddress("부산"));
        System.out.println("memberA = " + memberA);
        System.out.println("memberB = " + memberB);
    }
}
