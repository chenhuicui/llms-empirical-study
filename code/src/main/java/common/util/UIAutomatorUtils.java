package common.util;


import android.graphics.Rect;
import android.util.Log;
import androidx.test.uiautomator.*;
import mo.must.common.constants.TestConstants;
import mo.must.common.enums.Direction;
import org.apache.commons.lang3.StringUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Utility class for UIAutomator-based Android UI testing.
 * This class provides methods for interacting with UI elements using UIAutomator.
 */
public class UIAutomatorUtils {
    private static UiDevice uiDevice;


    public static void init(UiDevice uiDeviceInstance) {
        uiDevice = uiDeviceInstance;
    }


    public static UiObject findViewById(String id) {
        return uiDevice.findObject(new UiSelector().resourceId(id));
    }

    public static UiObject2 findViewByTypeName(String typeName) {
        return uiDevice.findObject(By.clazz(typeName));
    }

    public static List<UiObject2> findAllUiObjectsByTypeName(String typeName) {
        return uiDevice.findObjects(By.clazz(typeName));
    }

    public static UiObject2 findViewByTypeClass(Class<Object> type) {
        return uiDevice.findObject(By.clazz(type));
    }

    public static List<UiObject2> findAllUiObjects() {
        return uiDevice.findObjects(By.depth(0));

    }

    private static Map<String, String> getAdjacentTexts(String id) {
        if (StringUtils.isBlank(id)) {
            return Collections.emptyMap();
        }

        Map<String, String> textMap = new HashMap<>();
        for (Direction direction : Direction.values()) {
            String text = getAdjacentText(id, direction);
            textMap.put(direction.name(), text);
        }
        return textMap;
    }

    public static Map<String, UiObject2> getAdjacentObjects(String id) {
        if (StringUtils.isBlank(id)) {
            return Collections.emptyMap();
        }

        Map<String, UiObject2> textMap = new HashMap<>();
        for (Direction direction : Direction.values()) {
            UiObject2 text = getAdjacentObject(id, direction);
            textMap.put(direction.name(), text);
        }
        return textMap;
    }

    private static UiObject2 getAdjacentObject(String id, Direction direction) {
        UiObject2 closestText = null;
        try {
            Rect bounds = findViewById(id).getBounds();
            List<UiObject2> textObjects = uiDevice.findObjects(By.clazz(android.widget.TextView.class));

            int minDistance = Integer.MAX_VALUE;

            for (UiObject2 textObject : textObjects) {
                Rect textBounds = textObject.getVisibleBounds();

                int textCenterX = (textBounds.left + textBounds.right) / 2;
                int textCenterY = (textBounds.top + textBounds.bottom) / 2;

                int distance;

                if (direction == Direction.ABOVE && textBounds.bottom < bounds.top) {
                    distance = bounds.top - textBounds.bottom;
                } else if (direction == Direction.BELOW && textBounds.top > bounds.bottom) {
                    distance = textBounds.top - bounds.bottom;
                } else if (direction == Direction.LEFT && textBounds.right < bounds.left) {
                    distance = bounds.left - textBounds.right;
                } else if (direction == Direction.RIGHT && textBounds.left > bounds.right) {
                    distance = textBounds.left - bounds.right;
                } else {
                    continue; // 跳过不符合方向的文本控件
                }

                if ((direction == Direction.ABOVE || direction == Direction.BELOW ?
                        (textCenterX >= bounds.left && textCenterX <= bounds.right) :
                        (textCenterY >= bounds.top && textCenterY <= bounds.bottom))) {
                    if (distance < minDistance) {
                        minDistance = distance;
                        closestText = textObject;
                    }
                }
            }
        } catch (Exception e) {
            Log.e(TestConstants.TAG, "Error in getAdjacentText: ", e);
        }
        return closestText;
    }

    private static String getAdjacentText(String id, Direction direction) {
        UiObject2 closestText = getAdjacentObject(id, direction);
        return (closestText != null) ? closestText.getText() : null;
    }

    public static String getSpinnerDefaultValueById(String id) {
        try {
            UiCollection uiCollection = new UiCollection(new UiSelector().resourceId(id));
            UiObject spinner = uiCollection.getChild(new UiSelector().className("android.widget.TextView"));

            return spinner.getText();
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getClassNameById(String id) {
        try {
            UiObject element = findViewById(id);
            return element.getClassName();
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getClassSimpleNameById(String id) {
        if (StringUtils.isBlank(id)) {
            return "";
        }
        String name = getClassNameById(id);
        return name.substring(name.lastIndexOf(".") + 1);
    }

    public static String getClassSimpleName(UiObject2 obj) {
        if (obj == null) {
            Log.e(TestConstants.TAG, "对象为null");
            return "";
        }
        String name = obj.getClassName();
        return name.substring(name.lastIndexOf(".") + 1);
    }

    public static String getClassSimpleNameByFullName(String fullName) {
        if (StringUtils.isBlank(fullName)) {
            return "";
        }
        String[] parts = fullName.split("\\.");  // 使用正则表达式中的转义符号
        return parts.length < 1 ? "" : parts[parts.length - 1];
    }

    public static String getIdOfUiObject2(UiObject2 object2) {
        String resourceId = object2.getResourceName();
        return StringUtils.isBlank(resourceId) ? "" : resourceId;
    }

    public static void clickXY(int x, int y) {
        uiDevice.click(x, y);
    }

    public static void swipeLeft() {
        int[] a = swipe();

        // 执行滑动动作
        uiDevice.swipe(a[0], a[2], a[1], a[2], 10);  // 10 是滑动步长
    }

    public static void swipeRight() {
        // 获取屏幕尺寸
        int[] a = swipe();

        // 执行滑动动作
        uiDevice.swipe(a[1], a[2], a[0], a[2], 10);  // 10 是滑动步长
    }

    private static int[] swipe() {
        int screenWidth = uiDevice.getDisplayWidth();
        int screenHeight = uiDevice.getDisplayHeight();

        // 计算滑动的起点和终点坐标
        int startX = (int) (screenWidth * 0.8);  // 起点 x 坐标
        int endX = (int) (screenWidth * 0.2);    // 终点 x 坐标
        int startY = screenHeight / 2;           // 垂直方向滑动屏幕中央
        return new int[]{startX, endX, startY};
    }

    public static void swipeFromTo(int startX, int startY, int endX, int endY) {
        uiDevice.swipe(startX, startY, endX, endY, 10);
    }
}
