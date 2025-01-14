package common.bugdetection.validator;

import java.util.List;

/**
 * 不允许多行输入。
 * Multi-line input is not allowed.
 */
public class Page35Validator extends BasePageValidator {
    @Override
    protected boolean customValidate(List<String> list) {
        return list.contains("\n");
    }
}
