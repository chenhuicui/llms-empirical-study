package common.bugdetection.validator;

import java.util.List;
import java.util.Objects;

/**
 * 在搜索栏中输入单个空格导致问题。
 * Entering a single space into the search bar causes an issue.
 */
public class Page28Validator extends BasePageValidator {
    @Override
    protected boolean customValidate(List<String> list) {
        return Objects.equals(" ", list.get(0));
    }
}
