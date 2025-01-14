package common.bugdetection.validator;

import java.util.List;

/**
 * 在搜索框中输入双引号字符 “ 时应用程序崩溃。
 * The application crashes when a double-quote character (”) is entered in the search box.
 */
public class Page14Validator extends BasePageValidator {
    @Override
    protected boolean customValidate(List<String> list) {
        return list.get(0).contains("\"");
    }
}
