package common.bugdetection.validator;

import java.util.List;
import java.util.Objects;

/**
 * 当文本字段为空时点击“设置频率”导致应用程序崩溃。
 * Clicking “Set Frequency” with an empty text field causes the application to crash.
 */
public class Page25Validator extends BasePageValidator {
    @Override
    protected boolean customValidate(List<String> list) {
        return Objects.equals("", list.get(0));
    }
}
