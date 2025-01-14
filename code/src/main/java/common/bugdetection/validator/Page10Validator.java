package common.bugdetection.validator;

import java.util.List;

/**
 * 输入除字母 ‘a’（无论大小写）后再输入任何字母时出现问题。
 * An issue arises when any letter after ‘a’ (uppercase or lowercase) is typed.
 */
public class Page10Validator extends BasePageValidator {
    @Override
    protected boolean customValidate(List<String> list) {
        String input = list.get(0);
        int indexA = input.toLowerCase().indexOf('a');

        if (indexA == -1 || indexA == input.length() - 1) {
            return false;
        }

        String nextChar = String.valueOf(input.charAt(indexA + 1));
        return nextChar.matches("[a-zA-Z0-9]");
    }
}
