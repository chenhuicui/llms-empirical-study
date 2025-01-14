package common.bugdetection.validator;

import java.util.List;
import java.util.Objects;

/**
 * 当输入为空时，应用程序会崩溃。
 * The application crashes when the input field is empty.
 */
public class Page1Validator extends BasePageValidator {
    @Override
    protected boolean customValidate(List<String> list) {
        for (String input : list) {
            if (Objects.equals("", input)) {
                return true;
            }
        }
        return false;
    }
}
