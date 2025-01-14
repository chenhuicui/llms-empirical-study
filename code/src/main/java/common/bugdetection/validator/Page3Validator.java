package common.bugdetection.validator;

import java.util.List;

/**
 * 以 ‘0’ 开头的电话号码会引发问题。
 * A phone number starting with ‘0’ causes an issue.
 */
public class Page3Validator extends BasePageValidator {
    @Override
    protected boolean customValidate(List<String> list) {
        return list.get(0).startsWith("0");
    }
}
