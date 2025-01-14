package common.bugdetection.validator;

import java.util.List;

/**
 * @Description: TODO
 * @Author: xiaoche
 * @Date: 2024/12/7 13:09
 */
public interface PageValidator {
    boolean validate(List<String> list, int componentNum);
}
