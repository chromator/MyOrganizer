package avisha.com.myorganizer.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by skulw on 1/2/17.
 */
public class Util {
    public static String toTextDate(int i, int i1, int i2) {
        String txtDate = null;
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, i);
        calendar.set(Calendar.MONTH, i1);
        calendar.set(Calendar.DAY_OF_MONTH, i2);
        SimpleDateFormat dateFormat = new SimpleDateFormat();
        dateFormat.applyPattern("EEE, MMM dd yyyy");
        txtDate = dateFormat.format(calendar.getTime());
        return txtDate;
    }

    public static String toTextTime(int i, int i1) {
        String textTime = null;
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, i);
        calendar.set(Calendar.MINUTE, i1);
        SimpleDateFormat dateFormat = new SimpleDateFormat();
        dateFormat.applyPattern("KK:mm a");
        textTime = dateFormat.format(calendar.getTime());
        return textTime;
    }

    public static String milisToTextDate(long time) {
        if(time <= 0) {
            return null;
        }
        String textTime = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat();
        dateFormat.applyPattern("EEE, MMM dd yyyy");
        textTime = dateFormat.format(time);
        return textTime;
    }

    public static String milisToTextTime(long time) {
        if(time <= 0) {
            return null;
        }
        String textTime = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat();
        dateFormat.applyPattern("KK:mm a");
        textTime = dateFormat.format(time);
        return textTime;
    }
}
