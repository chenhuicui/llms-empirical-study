package common.bugdetection.validator;

import java.math.BigInteger;
import java.util.List;

/**
 * 当尝试编辑金额超过 999 的交易时，应用程序会崩溃。
 * The application crashes when editing transactions with an amount over 999 or creating return transactions.
 */
public class Page2Validator extends BasePageValidator {
    @Override
    protected boolean customValidate(List<String> list) {
        String input = list.get(0);
        // 检查输入是否为数字
        if (isNumeric(input)) {
            try {
                // 使用 BigInteger 解析数字并检查是否超过 999
                BigInteger amount = new BigInteger(input);
                return amount.compareTo(BigInteger.valueOf(999)) > 0;
            } catch (NumberFormatException e) {
                // 如果解析失败（非数字格式），返回 false
                return false;
            }
        }
        return false;
    }
}
