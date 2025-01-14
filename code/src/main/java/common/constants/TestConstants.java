package common.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * This class contains constants and utility methods that are used for testing purposes.
 */
public class TestConstants {

    // Tag for logging
    public static final String TAG = "LLM-TAG";

    // Model map for storing various models and their corresponding codes
    public static final Map<String, String> modelMap = new HashMap<>();

    static {
        modelMap.put("gpt-3.5", "0");
        modelMap.put("gpt-4", "2");
        modelMap.put("baichuan-2", "6");
    }

    /**
     * Causes the current thread to sleep for the specified number of milliseconds.
     *
     * @param timeoutMillis the length of time to sleep in milliseconds
     */
    public static void sleep(long timeoutMillis) {
        try {
            Thread.sleep(timeoutMillis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Converts a given time in seconds to milliseconds.
     *
     * @param seconds the time in seconds
     * @return the equivalent time in milliseconds
     */
    public static int getMillisForSeconds(int seconds) {
        return seconds * 1000;
    }

    /**
     * Converts a number to its ordinal representation.
     * For example, 1 becomes "1st", 2 becomes "2nd", 3 becomes "3rd", and so on.
     *
     * @param number the number to be converted
     * @return the ordinal representation of the number as a String
     */
    public static String convertToOrdinal(int number) {
        if (number < 0) {
            return Integer.toString(number);
        }
        String suffix;
        if (number % 100 >= 11 && number % 100 <= 13) {
            suffix = "th";
        } else {
            switch (number % 10) {
                case 1:
                    suffix = "st";
                    break;
                case 2:
                    suffix = "nd";
                    break;
                case 3:
                    suffix = "rd";
                    break;
                default:
                    suffix = "th";
            }
        }
        return String.format("%d%s", number, suffix);
    }
}