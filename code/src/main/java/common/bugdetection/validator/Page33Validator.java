package common.bugdetection.validator;

import java.util.List;

/**
 * 使用被禁止的字符更新字段名称会使红色错误消息被隐藏。
 * Updating a field name with prohibited characters hides the red error message.
 */
public class Page33Validator extends BasePageValidator {
    @Override
    protected boolean customValidate(List<String> list) {
        for (String input : list) {
            // 如果发现特殊字符，返回 true
            if (input.matches(".*[^a-zA-Z0-9].*")) {
                return true;
            }
        }
        return false;
    }
}
