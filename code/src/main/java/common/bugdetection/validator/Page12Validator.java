package common.bugdetection.validator;

import java.util.List;

/**
 * 以小写 ‘a’ 开头的输入会触发占位符问题。
 * Input starting with a lowercase ‘a’ triggers a placeholder issue.
 */
public class Page12Validator extends BasePageValidator {
    @Override
    protected boolean customValidate(List<String> list) {
        return list.get(0).startsWith("a");
    }
}
