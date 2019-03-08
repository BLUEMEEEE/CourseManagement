package util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimestampUtil {
    private static String dateFormat = "yyyy-MM-dd HH:mm:ss";
    private static SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
    public static Timestamp parse(String datetimeStr) {
//        Date date = null;
//        try {
//            date = sdf.parse(datetimeStr);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        Timestamp tStamp = new Timestamp(date.getTime());
        Timestamp datetime = Timestamp.valueOf(datetimeStr);
        return datetime;
    }

    public static String parse(Timestamp timestamp) {
        return sdf.format(timestamp);
    }

}
