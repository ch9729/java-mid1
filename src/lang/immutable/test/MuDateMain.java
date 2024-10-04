package lang.immutable.test;

public class MuDateMain {

    public static void main(String[] args) {
        MyDate date1 = new MyDate(2024,1,1);
        MyDate date2 = date1;
        System.out.println("date1 = " + date1);
        System.out.println("date2 = " + date2);

        System.out.println("date1 2025년으로 변경");
        date1 =date1.withYear(2025);

        System.out.println("date1 = " + date1);
        System.out.println("date2 = " + date2);
    }
}
