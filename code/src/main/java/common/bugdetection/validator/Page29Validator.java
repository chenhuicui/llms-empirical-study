package common.bugdetection.validator;

import java.util.List;

/**
 * 确保输入多行（超过一行）文本时有问题。
 * Ensuring that the input spans more than one line causes an issue.
 */
public class Page29Validator extends BasePageValidator {
    @Override
    protected boolean customValidate(List<String> list) {
        return list.get(0).contains("\n");
    }
}
