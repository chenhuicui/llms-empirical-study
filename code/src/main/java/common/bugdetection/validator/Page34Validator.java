package common.bugdetection.validator;

import java.util.List;
import java.util.Objects;

/**
 * 不允许出现空字符输入。
 * Empty character input is not allowed.
 */
public class Page34Validator extends BasePageValidator {
    @Override
    protected boolean customValidate(List<String> list) {
        return Objects.equals("", list.get(0));
    }
}

