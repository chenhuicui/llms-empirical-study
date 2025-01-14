package common.bugdetection.validator;

import java.util.List;
import java.util.Objects;

/**
 * 输入单独的 “#” 号不被允许。
 * A standalone “#” character is not allowed.
 */
public class Page32Validator extends BasePageValidator {
    @Override
    protected boolean customValidate(List<String> list) {
        return Objects.equals("#", list.get(0));
    }
}
