package common.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TestUtils {
    public static String getCurrentTime() {
        return getCurrentTimeWithFormat("yyyy-MM-dd HH:mm:ss");
    }

    public static String getCurrentTimeWithFormat(String format) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return now.format(formatter);
    }

}
