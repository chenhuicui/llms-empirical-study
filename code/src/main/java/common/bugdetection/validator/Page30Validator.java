package common.bugdetection.validator;

import java.util.List;
import java.util.Objects;

/**
 * 在“Enter Hospital ID”编辑框中不输入任何内容导致问题。
 * Leaving the “Enter Hospital ID” EditText empty causes an issue.
 */
public class Page30Validator extends BasePageValidator {
    @Override
    protected boolean customValidate(List<String> list) {
        return Objects.equals("", list.get(0));
    }
}
