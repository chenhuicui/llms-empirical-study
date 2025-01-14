package common.bugdetection.validator;

import java.util.List;

/**
 * 输入包含除字母 'a'（小写或大写）以外的任意字母或任何特殊符号（%&€^¡¿~®©™¢¥$¦¬°¶§×，不包括重音字母和普通标点符号）会触发bug。
 * The input contains any letter other than 'a' (uppercase or lowercase) or any special symbols (%&€^¡¿~®©™¢¥$¦¬°¶§×,
 * excluding accented letters and common punctuation) will trigger the bug.
 */
public class Page11Validator extends BasePageValidator {
    @Override
    protected boolean customValidate(List<String> list) {
        String input = list.get(0);
        for (char c : input.toCharArray()) {
            if (isNonACharacter(c) || isSpecialSymbol(c)) {
                return true; // 如果存在不符合要求的字符，触发问题
            }
        }
        return false; // 全部字符均合法，则不触发问题
    }

    private boolean isNonACharacter(char c) {
        return Character.isLetter(c) && Character.toLowerCase(c) != 'a';
    }

    private boolean isSpecialSymbol(char c) {
        String specialSymbols = "%@#&€^¡¿~®©™¢¥$¦¬°¶§×";
        return specialSymbols.indexOf(c) >= 0;
    }
}
