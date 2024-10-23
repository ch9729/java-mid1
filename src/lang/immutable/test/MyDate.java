package lang.immutable.test;

public class MyDate {

    private final int year;
    private final int month;
    private final int day;

    public MyDate(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public int getYear() {
        return year;
    }

    public MyDate withYear(int changeYear) {
        MyDate myDate = new MyDate(changeYear, month, day);
        return myDate;
    }


    public MyDate withMonth(int changeMonth) {
        MyDate myDate = new MyDate(year, changeMonth, day);
        return myDate;
    }


    public MyDate withDay(int changeDay) {
        MyDate myDate = new MyDate(year, month, changeDay);
        return myDate;
    }

    @Override
    public String toString() {
        return "MyDate{" +
                "year=" + year +
                ", month=" + month +
                ", day=" + day +
                '}';
    }
}
