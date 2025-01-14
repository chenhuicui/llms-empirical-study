package common.bugdetection.validator;

import java.util.List;

/**
 * 在剂量字段输入非数字字符导致错误。
 * Entering non-numeric characters in the dosage field causes an error.
 */
public class Page6Validator extends BasePageValidator {
    @Override
    protected boolean customValidate(List<String> list) {
        return !isNumeric(list.get(0));
    }
}
