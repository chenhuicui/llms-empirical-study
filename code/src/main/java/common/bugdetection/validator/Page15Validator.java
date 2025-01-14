package common.bugdetection.validator;

import java.util.List;
import java.util.Objects;

/**
 * 若输入的字符串仅为 “[” 时，BitmapFont 标记会崩溃。
 * If the input string is just “[”, the BitmapFont markup causes a crash.
 */
public class Page15Validator extends BasePageValidator {
    @Override
    protected boolean customValidate(List<String> list) {
        String input = list.get(0);
        return input.length() == 1 && Objects.equals("[", input);
    }
}
