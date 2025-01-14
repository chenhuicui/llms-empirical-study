package common.bugdetection.validator;

import java.util.List;

/**
 * 允许输入无效的电子邮箱地址。
 * Invalid email addresses can be entered without restriction.
 */
public class Page36Validator extends BasePageValidator {
    @Override
    protected boolean customValidate(List<String> list) {
        return !isValidEmail(list.get(2)) || !isValidEmail(list.get(5));
    }
}
