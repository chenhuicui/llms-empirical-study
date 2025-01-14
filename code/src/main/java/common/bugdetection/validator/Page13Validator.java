package common.bugdetection.validator;

import java.util.List;

/**
 * 输入一个 “0” 会自动添加两个 “0”。
 * Entering a “0” automatically adds two zeros.
 */
public class Page13Validator extends BasePageValidator {
    @Override
    protected boolean customValidate(List<String> list) {
        return list.get(0).contains("0");
    }
}
