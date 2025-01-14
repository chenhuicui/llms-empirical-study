package common.bugdetection.validator;

import java.util.List;
import java.util.Objects;

/**
 * 当出发文本视图为空时应用程序崩溃。
 * The application crashes when the departure text view is empty.
 */
public class Page17Validator extends BasePageValidator {
    @Override
    protected boolean customValidate(List<String> list) {
        return Objects.equals("", list.get(0));
    }
}
