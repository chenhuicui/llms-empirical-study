package common.bugdetection.validator;

import java.util.List;

/**
 * 输入无效数据时，应用程序会崩溃(非数字字符)。
 * The application crashes when invalid input is provided (non-numeric characters).
 */
public class Page7Validator extends BasePageValidator {
    @Override
    protected boolean customValidate(List<String> list) {
        return !isNumeric(list.get(0));
    }
}
