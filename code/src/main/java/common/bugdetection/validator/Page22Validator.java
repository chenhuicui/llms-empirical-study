package common.bugdetection.validator;

import java.util.List;

/**
 * 当输入以 “.” 开头的数字（如 “.67”）时，用于大气压或甲烷字段的输入导致应用无响应并崩溃。
 * Inputting a number starting with “.” (e.g., “.67”) for atmospheric or methane fields causes the application to become unresponsive and crash.
 */
public class Page22Validator extends BasePageValidator {
    @Override
    protected boolean customValidate(List<String> list) {
        return list.get(0).matches("^\\.\\d+$");
    }
}
