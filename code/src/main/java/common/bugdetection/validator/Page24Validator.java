package common.bugdetection.validator;

import java.util.List;

/**
 * 在惰性 UI 中输入长文本后应用程序崩溃。
 * The application crashes after entering a long text in a lazy-loading UI component.
 */
public class Page24Validator extends BasePageValidator {
    @Override
    protected boolean customValidate(List<String> list) {
        return list.get(0).length() >= 100;
    }
}
