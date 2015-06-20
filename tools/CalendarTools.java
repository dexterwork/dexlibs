package studio.dexter.sortout;

import java.util.Calendar;

/**
 * Created by dexter on 2015/6/17.
 */
public class CalendarTools {

    public static String getStrTime() {
        Calendar calendar = Calendar.getInstance();
        String date = String.valueOf(calendar.get(Calendar.YEAR));
        date += Tools.strLen(calendar.get(Calendar.MONTH) + 1, 2);
        date += Tools.strLen(calendar.get(Calendar.DAY_OF_MONTH), 2);
        date += "_" + Tools.strLen(calendar.get(Calendar.HOUR_OF_DAY), 2);
        date += Tools.strLen(calendar.get(Calendar.MINUTE), 2);
        date += Tools.strLen(calendar.get(Calendar.SECOND), 2);
        return date;
    }
}
