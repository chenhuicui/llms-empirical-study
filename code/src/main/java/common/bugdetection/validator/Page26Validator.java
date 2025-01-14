package common.bugdetection.validator;

import java.util.List;

/**
 * 无效收件人地址时会导致崩溃。
 * An invalid recipient address leads to a crash.
 */
public class Page26Validator extends BasePageValidator {
    @Override
    protected boolean customValidate(List<String> list) {
        return !isValidEmail(list.get(1));
    }
}
