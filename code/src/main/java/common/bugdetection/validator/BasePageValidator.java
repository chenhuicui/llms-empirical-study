package common.bugdetection.validator;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;


public abstract class BasePageValidator implements PageValidator {

    @Override
    public boolean validate(List<String> list, int componentNum) {
        if (!checkNumber(list, componentNum)) {
            return false;
        }
        return customValidate(list);
    }

    protected abstract boolean customValidate(List<String> list);

    protected boolean isNumeric(String input) {
        return input.matches("\\d+");
    }

    protected boolean containsNonDigit(String input) {
        return !input.matches("\\d+"); // 返回 true 表示包含非数字字符
    }

    private boolean checkNumber(List<String> textList, int componentNum) {
        return textList.size() == componentNum;
    }

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"
    );

    protected boolean isValidEmail(String input) {
        return EMAIL_PATTERN.matcher(input).matches();
    }

    protected List<String> convertNullList(List<String> textList, int componentNum) {
        if (!textList.isEmpty()) {
            return textList;
        }
        return new ArrayList<>(Collections.nCopies(componentNum, ""));
    }

    protected List<String> formateTextsList(List<String> textList, int componentNum) {
        if (textList.isEmpty()) {
            textList = new ArrayList<>(Collections.nCopies(componentNum, ""));
        }

        int currentSize = textList.size();
        if (currentSize == componentNum) {
            return textList;
        }

        int diffSize = componentNum - currentSize;
        System.out.println(diffSize);
        textList = new ArrayList<>(textList);
        if (diffSize < 0) {
            return textList.subList(0, componentNum);
        } else {
            for (int i = 0; i < diffSize; i++) {
                textList.add("");
            }
            return textList;
        }
    }

}
