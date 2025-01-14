package common.processor;

import android.util.Log;
import android.view.ViewGroup;
import androidx.test.uiautomator.UiObject2;
import mo.must.common.constants.TestConstants;
import mo.must.common.util.EspressoUtils;
import mo.must.common.util.RobotiumUtils;
import mo.must.common.util.UIAutomatorUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Utility class to handle adjacent text information processing.
 * The purpose of this class is to get adjacent text information from elements with the same parent element
 * as the specified input element ID.
 * It uses Robotium, Espresso, and UIAutomator tools to get related information about UI components.
 */
public class AdjacentTextProcessor {

    /**
     * Gets a map of adjacent text information based on the specified input element ID.
     *
     * @param id The input element ID to query
     * @return A map containing the adjacent component text information for each direction
     */
    public static Map<String, String> getAdjacentTextMap(String id) {
        // Use UIAutomator to get all possible adjacent elements by ID
        Map<String, UiObject2> candidateAdjacentObjects = UIAutomatorUtils.getAdjacentObjects(id);
        Map<String, String> adjacentTextMap = new HashMap<>();
        for (Map.Entry<String, UiObject2> entry : candidateAdjacentObjects.entrySet()) {
            String key = entry.getKey();
            UiObject2 value = entry.getValue();

            // Get text for each possible adjacent element
            String adjacentText = getAdjacentText(value);
            adjacentTextMap.put(key, adjacentText);
        }
        return adjacentTextMap;
    }

    /**
     * Retrieves the adjacent text for a given UI object.
     *
     * @param value The UI object that might be adjacent
     * @return The text of the adjacent component, or an empty string if the component is null or has no ID
     */
    private static String getAdjacentText(UiObject2 value) {
        if (value == null) {
            Log.i(TestConstants.TAG, "getAdjacentText: Adjacent component is null, skipping and returning an empty string.");
            return "";
        }
        String uiObjectId = UIAutomatorUtils.getIdOfUiObject2(value);
        if (StringUtils.isBlank(uiObjectId)) {
            Log.i(TestConstants.TAG, "Adjacent component ID is null, skipping and returning an empty string.");
            return "";
        }
        return value.getText();
    }

    /**
     * Gets a map of adjacent text information from elements with the same parent as the input element.
     *
     * @param id The input element ID to query
     * @return A map containing the adjacent component text information for each direction
     */
    public static Map<String, String> getAdjacentTextMapFromSameParent(String id) {
        // Convert the string ID using Espresso and get the parent view from Robotium
        ViewGroup inputParent = RobotiumUtils.getParentByViewId(EspressoUtils.convertStringIdToInt(id));

        // If the parent view is null, log an error and return an empty map
        if (inputParent == null) {
            Log.e(TestConstants.TAG, "inputParent retrieved is null.");
            return Collections.emptyMap(); // Return an empty map early
        }

        // Use UIAutomator to get all possible adjacent elements by ID
        Map<String, UiObject2> candidateAdjacentObjects = UIAutomatorUtils.getAdjacentObjects(id);
        Map<String, String> adjacentTextMap = new HashMap<>();
        for (Map.Entry<String, UiObject2> entry : candidateAdjacentObjects.entrySet()) {
            String key = entry.getKey();
            UiObject2 value = entry.getValue();

            // Get text for each possible adjacent element
            String adjacentText = getAdjacentText(value, inputParent);
            adjacentTextMap.put(key, adjacentText);
        }
        return adjacentTextMap;
    }

    /**
     * Retrieves the adjacent text for a given UI object, ensuring it has the same parent as the input element.
     *
     * @param value       The UI object that might be adjacent
     * @param inputParent The parent view of the input element
     * @return The text of the adjacent component, or an empty string if the component is null or not in the same parent
     */
    private static String getAdjacentText(UiObject2 value, ViewGroup inputParent) {
        if (value == null) {
            Log.i(TestConstants.TAG, "getAdjacentText: Adjacent component is null, skipping and returning an empty string.");
            return "";
        }
        // Get the ID of the UI object from UIAutomator
        String uiObjectId = UIAutomatorUtils.getIdOfUiObject2(value);
        if (StringUtils.isBlank(uiObjectId)) {
            Log.i(TestConstants.TAG, "Adjacent component ID is null, skipping and returning an empty string.");
            return "";
        }
        // Convert the string ID using Espresso and get the parent view from Robotium
        ViewGroup uiObjectParent = RobotiumUtils.getParentByViewId(EspressoUtils.convertStringIdToInt(uiObjectId));
        // Return an empty string if the parents do not match, otherwise return the text of the UI object
        if (!Objects.equals(inputParent, uiObjectParent)) {
            return "";
        }
        return value.getText();
    }
}