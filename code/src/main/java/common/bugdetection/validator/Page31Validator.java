package common.bugdetection.validator;

import java.util.List;

/**
 * 当输入的时间不在 0-60 范围内，会显示异常弹窗，格式为00:00。
 * If the entered time is not between 0 and 60, an exception popup is displayed, formation :00:00 .
 */
public class Page31Validator extends BasePageValidator {
    @Override
    protected boolean customValidate(List<String> list) {
        for (int i = 0; i < 2; i++) {
            String input = list.get(i);
            // 检查是否符合 00:00 格式
            if (!input.matches("^\\d{2}:\\d{2}$")) {
                return false; // 如果格式不正确，不认为是触发问题的输入
            }
            if (!isValidTime(input)) {
                return true;
            }
        }
        return false;
    }

    private boolean isValidTime(String input) {
        // 解析小时和分钟部分
        String[] parts = input.split(":");
        int minutes;
        try {
            minutes = Integer.parseInt(parts[1]);
        } catch (NumberFormatException e) {
            return false;
        }
        return minutes >= 0 && minutes <= 60;
    }
}
