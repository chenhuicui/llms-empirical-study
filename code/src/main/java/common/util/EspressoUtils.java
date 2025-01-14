package common.util;


import android.view.View;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.platform.app.InstrumentationRegistry;
import org.apache.commons.lang3.StringUtils;
import org.hamcrest.Matcher;

/**
 * Utility class for Espresso-based Android UI testing.
 * This class provides methods for interacting with UI elements using Espresso.
 */
public class EspressoUtils {

    public static void clickButtonWithId(int buttonId) {
        Matcher<View> viewMatcher = ViewMatchers.withId(buttonId);
        ViewInteraction button = Espresso.onView(viewMatcher);
        button.perform(ViewActions.click());
    }


    public static void clickButtonWithText(String text) {
        Matcher<View> viewMatcher = ViewMatchers.withText(text);
        ViewInteraction button = Espresso.onView(viewMatcher);
        button.perform(ViewActions.click());
    }

    public static int convertStringIdToInt(String id) {
        if (StringUtils.isBlank(id)) {
            return -1;
        }
        String[] parts = id.split(":");
        // Handle cases where there is no colon, possibly indicating an invalid ID
        if (parts.length < 1) {
            return -1;
        }
        String packageName = parts[0];
        String idName = parts[1].split("/")[1];
        return InstrumentationRegistry.getInstrumentation().getTargetContext().getResources().getIdentifier(idName, "id", packageName);
    }
}
