package common.bugdetection.validator;

import java.util.List;
import java.util.Objects;

/**
 * 在网站 URL 中输入空的 URL 值导致问题。
 * Entering an empty URL in the website URL field causes an issue.
 */
public class Page20Validator extends BasePageValidator {
    @Override
    protected boolean customValidate(List<String> list) {
        return Objects.equals("", list.get(2));
    }
}
