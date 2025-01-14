package common.bugdetection.validator;

import java.util.List;
import java.util.Objects;

/**
 * 输入 “WRITE_SETTINGS” 时导致问题。
 * Inputting “WRITE_SETTINGS” triggers an issue.
 */
public class Page8Validator extends BasePageValidator {
    @Override
    protected boolean customValidate(List<String> list) {
        return Objects.equals("WRITE_SETTINGS", list.get(0));
    }
}
