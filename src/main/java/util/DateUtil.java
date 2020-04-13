package util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Util class for working with dates
 * @author linet
 */
public class DateUtil {

    // receives date in YYYY-MM-DD format
    public static Date getDate(String receivedDate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(false);

        Date date = sdf.parse(receivedDate);

        return date;
    }

    public static String dateToString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }


}
