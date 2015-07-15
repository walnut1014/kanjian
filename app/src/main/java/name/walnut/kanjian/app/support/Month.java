package name.walnut.kanjian.app.support;

/**
 * 月
 */
public enum Month {
    January(1, "JAN"),
    February(2, "FEB"),
    March(3, "MAR"),
    April(4, "APR"),
    May(5, "MAY"),
    June(6, "JUN"),
    July(7, "JUL"),
    August(8, "AUG"),
    September(9, "SEP"),
    October(10, "OCT"),
    November(11, "NOV"),
    December(12, "DEC")
    ;

    Month(int monthNum, String jane) {
        this.monthNum = monthNum;
        this.jane = jane;
    }

    public String getJane() {
        return jane;
    }

    public int getMonthNum() {
        return monthNum;
    }

    public static Month getJane(int monthNum) {
        Month[] months = Month.values();
        for (Month m : months) {
            if (monthNum == m.monthNum) {
                return m;
            }
        }
        return null;
    }

    private int monthNum;
    private String jane;

}
