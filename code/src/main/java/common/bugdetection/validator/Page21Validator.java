package common.bugdetection.validator;

import java.util.List;
import java.util.regex.Pattern;

/**
 * 在网站 URL 中输入格式错误的 URL 会出现问题。
 * Entering a malformed URL in the website URL field leads to an issue.
 */
public class Page21Validator extends BasePageValidator {

    private static final Pattern URL_PATTERN = Pattern.compile(
            "^(https?://)" +               // 必须以 http:// 或 https:// 开头
                    "([\\w.-]+)" +                 // 主机名部分，例如 example.com
                    "(\\.[a-z]{2,})" +             // 域名后缀，例如 .com, .org
                    "(:\\d+)?(/.*)?$",             // 可选端口号和路径
            Pattern.CASE_INSENSITIVE
    );

    @Override
    protected boolean customValidate(List<String> list) {
        String input = list.get(2);
        return !isValidURL(input);
    }

    /**
     * 检查字符串是否为有效的 URL
     */
    private boolean isValidURL(String input) {
        return URL_PATTERN.matcher(input).matches();
    }
}