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

    public MyDate withYear(int newYear){
        return new MyDate(newYear, month, day);
    }

    public MyDate withMonth(int newMonth){
        return new MyDate(year, newMonth, day);
    }

    public MyDate withDay(int newDay){
        return new MyDate(year, month, newDay);
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
