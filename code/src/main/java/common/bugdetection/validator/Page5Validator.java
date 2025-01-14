package common.bugdetection.validator;

import java.util.List;

/**
 * 对 100 个字符以上的长搜索词建议无法正常获取。
 * The application fails to retrieve suggestions for very long search terms (≥100 characters).
 */
public class Page5Validator extends BasePageValidator {
    @Override
    protected boolean customValidate(List<String> list) {
        return list.get(0).length() >= 100;
    }
}

