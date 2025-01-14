package common.bugdetection.validator;

import java.util.List;

/**
 * 如果输入中包含非数字符号，EditText 会在删除掩码符号后崩溃。
 * If non-digit symbols are entered, the EditText removes mask characters and then crashes.
 */
public class Page18Validator extends BasePageValidator {
    @Override
    protected boolean customValidate(List<String> list) {
        for (String input : list) {
            if (containsNonDigit(input)) {
                return true;
            }
        }
        return false; // 所有输入都为纯数字时，不触发问题
    }
}
