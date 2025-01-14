package common.bugdetection.validator;

import java.util.List;
import java.util.Objects;

/**
 * 密码与确认密码不一致时会导致应用程序崩溃。
 * The application crashes when the password and confirm password fields do not match.
 */
public class Page37Validator extends BasePageValidator {
    @Override
    protected boolean customValidate(List<String> list) {
        return !Objects.equals(list.get(3), list.get(4));
    }
}
