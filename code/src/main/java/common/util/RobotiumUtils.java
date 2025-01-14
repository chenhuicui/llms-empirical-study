package common.util;


import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import com.robotium.solo.Condition;
import com.robotium.solo.Solo;
import mo.must.common.constants.TestConstants;

import java.util.HashMap;
import java.util.Map;


/**
 * Utility class for Robotium-based Android UI testing.
 * This class provides methods for interacting with UI elements using Robotium.
 */
public class RobotiumUtils {

    private static Solo solo;

    public static void init(Solo soloInstance) {
        solo = soloInstance;
    }

    public static boolean waitForActivity(Class<? extends Activity> clazz, long timeoutMillis) {
        return solo.waitForActivity(clazz, (int) timeoutMillis);
    }

    public static boolean waitForViewWithId(int viewId, long timeoutMillis) {
        return solo.waitForView(viewId, 1, (int) timeoutMillis);
    }

    public static boolean waitForText(String text, long timeoutMillis) {
        return solo.waitForText(text, 1, timeoutMillis);
    }

    public static boolean waitForText(String text) {
        return solo.waitForText(text, 1, TestConstants.getMillisForSeconds(8));
    }

    public static void clickButtonWithId(int buttonId) {
        View button = solo.getCurrentActivity().findViewById(buttonId);

        if (button != null) {
            // 单击按钮
            solo.clickOnView(button);
        } else {
            throw new IllegalArgumentException("未找到 ID 为 " + buttonId + " 的按钮。");
        }
    }

    public static View findViewByID(int viewId) {
        return solo.getCurrentActivity().findViewById(viewId);
    }

    public static void clickButtonWithText(String text) {
        if (solo.searchButton(text)) {
            solo.clickOnButton(text);
        } else {
            throw new IllegalArgumentException("未找到文本为 '" + text + "' 的按钮。");
        }
    }


    public static void fillTextInTextView(View textView, String textToFill) {
        solo.clearEditText((EditText) textView);
        solo.enterText((EditText) textView, textToFill);
    }

    public interface FillerStrategy {
        void fill(View view, String text);
    }

    public static void fillTextInTextView(int textViewId, String textToFill) {
        View textView = solo.getView(textViewId);
        FillerStrategy strategy = getStrategyForView(textView);
        assert strategy != null;
        strategy.fill(textView, textToFill);
    }

    public static void fillTextInTextViewByIndex(int index, String textToFill) {
        View textView = solo.getEditText(index);
        FillerStrategy strategy = getStrategyForView(textView);
        assert strategy != null;
        strategy.fill(textView, textToFill);
    }

    private static FillerStrategy getStrategyForView(View view) {
        for (Class<? extends View> viewClass : strategies.keySet()) {
            if (viewClass.isInstance(view)) {
                return strategies.get(viewClass);
            }
        }
        return null;
    }

    private static final Map<Class<? extends View>, FillerStrategy> strategies = new HashMap<>();

    static {
        strategies.put(EditText.class, (view, text) -> fillEditText((EditText) view, text));
    }

    private static void fillEditText(EditText editText, String textToFill) {
        solo.clearEditText(editText);
        solo.enterText(editText, textToFill);
    }


    public static boolean waitFor(Condition condition) {
        return waitFor(condition, TestConstants.getMillisForSeconds(8)); // 默认超时时间为 8 秒

    }


    public static boolean waitFor(Condition condition, long timeout) {
        return solo.waitForCondition(condition, (int) timeout);
    }


    public static Class<? extends Activity> getCurrentActivity() {
        return solo.getCurrentActivity().getClass();
    }


    public static String getCurrentActivitySimpleName() {
        return getCurrentActivity().getSimpleName();
    }

    public static String getAppName() {
        Activity currentActivity = solo.getCurrentActivity();
        PackageManager pm = currentActivity.getPackageManager();
        ApplicationInfo ai;
        try {
            ai = pm.getApplicationInfo(currentActivity.getPackageName(), 0);
        } catch (final PackageManager.NameNotFoundException e) {
            ai = null;
        }
        return (String) (ai != null ? pm.getApplicationLabel(ai) : "unknown");
    }


    public static Context getContext() {
        return solo.getCurrentActivity().getApplicationContext();
    }


    public static View getViewWithIntId(int id) {
        return solo.getView(id);
    }

    public static View getViewWithStringId(String id) {
        int viewId = EspressoUtils.convertStringIdToInt(id);
        return solo.getView(viewId);
    }

    public static ViewGroup getParentByViewId(int id) {
        View view = solo.getView(id);
        if (view == null) {
            return null;
        }
        return (view.getParent() instanceof ViewGroup) ? (ViewGroup) view.getParent() : null;
    }
}
