package niffler.utils;

import lombok.experimental.UtilityClass;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;

@UtilityClass
public class DateUtils {

    public static String getDateAsString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yy", Locale.ENGLISH);
        return sdf.format(date);
    }

    public static Date fromString(String dateAsString) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yy", Locale.ENGLISH);
        try {
            return sdf.parse(dateAsString);
        } catch (ParseException e) {
            throw new RuntimeException();
        }
    }
}
