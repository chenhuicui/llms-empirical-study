package common.bugdetection.validator;

import java.util.List;
import java.util.Objects;

/**
 * 在未输入内容就点击登录按钮会导致应用程序崩溃。
 * Clicking the Login button without entering any input crashes the app.
 */
public class Page27Validator extends BasePageValidator {
    @Override
    protected boolean customValidate(List<String> list) {
        for (String input : list) {
            if (Objects.equals("", input)) {
                return true;
            }
        }
        return false;
    }
}
