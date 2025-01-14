package common.bugdetection.validator;

import java.util.List;

/**
 * 输入包含 “:” 时导致应用程序崩溃。
 * Inputting “:” (contains) causes the application to crash.
 */
public class Page9Validator extends BasePageValidator {
    @Override
    protected boolean customValidate(List<String> list) {
        return list.get(0).contains(":");
    }
}
