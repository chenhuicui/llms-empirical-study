package common.bugdetection.validator;

import java.util.List;

/**
 * 以 “http” 开头的网址会转换为 emoji。
 * A URL starting with “http” transforms into an emoji.
 */
public class Page16Validator extends BasePageValidator {
    @Override
    protected boolean customValidate(List<String> list) {
        String input = list.get(0);
        return input.startsWith("http");
    }
}
