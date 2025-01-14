package common.test;

import android.app.Activity;
import mo.must.common.util.RobotiumUtils;


/**
 * Utility class for common assertion test methods.
 * This class provides methods to assert various conditions during Android GUI testing.
 *
 * @author cuichenhui
 * @date 2023-09-29
 */
public class TestAssert {
    public static void assertEquals(Object expected, Object actual) {
        if (!expected.equals(actual)) {
            throw new AssertionError("Expected: " + expected + ", but was: " + actual);
        }
    }

    public static void assertActivity(Class<? extends Activity> expected) {
        if (!expected.equals(RobotiumUtils.getCurrentActivity())) {
            throw new AssertionError("Expected: " + expected + ", but was: " + RobotiumUtils.getCurrentActivity());
        }
    }

    public static void assertNotActivity(Class<? extends Activity> expected) {
        if (expected.equals(RobotiumUtils.getCurrentActivity())) {
            throw new AssertionError("Expected: " + expected + ", but was: " + RobotiumUtils.getCurrentActivity());
        }
    }

    public static void assertNotEquals(Object notExpected, Object actual) {
        if (notExpected.equals(actual)) {
            throw new AssertionError("Not expected: " + notExpected + ", but was: " + actual);
        }
    }

    public static void assertTrue(boolean condition) {
        if (!condition) {
            throw new AssertionError("Expected: true, but was: false");
        }
    }

    public static void assertFalse(boolean condition) {
        if (condition) {
            throw new AssertionError("Expected: false, but was: true");
        }
    }

    public static void assertNull(Object object) {
        if (object != null) {
            throw new AssertionError("Expected: null, but was: " + object);
        }
    }

    public static void assertNotNull(Object object) {
        if (object == null) {
            throw new AssertionError("Expected: not null, but was: null");
        }
    }
}
