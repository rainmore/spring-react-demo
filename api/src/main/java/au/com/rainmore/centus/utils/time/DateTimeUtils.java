package au.com.rainmore.centus.utils.time;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class DateTimeUtils {

    public static final String DATETIME_FORMAT       = "yyyy-MM-dd HH:mm:ss";
    public static final String DATETIME_SHORT_FORMAT = "yyyy-MM-dd HH:mm";

    public static Long toEpochMilli(LocalDateTime localDateTime) {
        return localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    public static Long toEpochSeconds(LocalDateTime localDateTime) {
        return localDateTime.atZone(ZoneId.systemDefault()).toEpochSecond();
    }

    public static Date asDate(LocalDateTime localDateTime)  {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static LocalDateTime asLocalDateTime(Instant instant) {
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }

    public static LocalDateTime asLocalDateTime(Date date) {
        return asLocalDateTime(Instant.ofEpochMilli(date.getTime()));
    }

    public static LocalDateTime asLocalDateTime(Long timestamp) {
        return asLocalDateTime(Instant.ofEpochMilli(timestamp));
    }

}
