package common.bugdetection.validator;

import java.util.List;
import java.util.Objects;

/**
 * 输入文本 “test” 后不按回车键会导致问题。
 * Entering the text “test” without pressing Enter causes an issue.
 */
public class Page19Validator extends BasePageValidator {
    @Override
    protected boolean customValidate(List<String> list) {
        return Objects.equals("test", list.get(0));
    }
}
