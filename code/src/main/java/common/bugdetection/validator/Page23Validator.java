package common.bugdetection.validator;

import java.util.List;

/**
 * 空的两位数编号列表项（如 “10.”）会导致应用程序崩溃。
 * An empty two-digit numbered list item (e.g., “10.”) crashes the application.
 */
public class Page23Validator extends BasePageValidator {
    @Override
    protected boolean customValidate(List<String> list) {
        return list.get(0).matches("^\\d{2}\\.$");
    }
}
