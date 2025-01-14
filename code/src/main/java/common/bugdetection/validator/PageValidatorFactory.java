package common.bugdetection.validator;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: TODO
 * @Author: xiaoche
 * @Date: 2024/12/7 13:20
 */
public class PageValidatorFactory {
    private static final Map<Integer, PageValidator> VALIDATOR_MAP = new HashMap<>();
    private static final String VALIDATOR_PACKAGE = "mo.must.common.resultprocessor.bugdetection.validator.";

    static {
        for (int i = 1; i <= 37; i++) {
            try {
                String className = VALIDATOR_PACKAGE + "Page" + i + "Validator";
                Class<?> clazz = Class.forName(className);
                PageValidator validator = (PageValidator) clazz.getDeclaredConstructor().newInstance();
                VALIDATOR_MAP.put(i, validator);
            } catch (Exception e) {
                throw new RuntimeException("Failed to load validator for page: " + i, e);
            }
        }
    }

    public static PageValidator getValidator(int pageNum) {
        PageValidator validator = VALIDATOR_MAP.get(pageNum);
        if (validator == null) {
            throw new IllegalArgumentException("No validator defined for pageNum: " + pageNum);
        }
        return validator;
    }
}
