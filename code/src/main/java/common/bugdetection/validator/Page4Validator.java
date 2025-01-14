package common.bugdetection.validator;

import java.util.List;

/**
 * 第二个文本框中输入单个日文字符可能导致问题。
 * Entering a single Japanese character in the second EditText view may cause an issue.
 */
public class Page4Validator extends BasePageValidator {
    @Override
    protected boolean customValidate(List<String> list) {
        String input = list.get(0);
        return input.length() == 1 && input.matches("[\\u3040-\\u30FF\\u31F0-\\u31FF\\u4E00-\\u9FAF]");
    }
}
