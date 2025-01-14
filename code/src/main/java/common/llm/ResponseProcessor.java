package common.llm;


import mo.must.common.prompt.PromptConfiguration;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The ResponseProcessor class provides methods for processing and parsing input strings,
 * specifically for extracting test cases from the input.
 */
public class ResponseProcessor {

    /**
     * Extracts test cases from an input string.
     *
     * @param response The input string, expected to contain a string array in the format ["testCase1", "testCase2", ...].
     * @return A list containing all test cases extracted from the input.
     *         If no test cases are found, returns a list containing a single message "Test case array not found."
     */
    public static List<String> extractTestCases(String response) {
        List<String> results = new ArrayList<>();
        if (StringUtils.isEmpty(response)) {
            results.add(PromptConfiguration.PARSE_EXCEPTION_MSG);
            return results;
        }

        // Use a regular expression to directly match the array content, e.g., ["testCase1", "testCase2"]
        Pattern arrayPattern = Pattern.compile("[\\[{](\"[^\"]+\"(,\\s*\"[^\"]+\")*)*[\\]}]");
        Matcher arrayMatcher = arrayPattern.matcher(response);

        // If a matching array format is found
        if (arrayMatcher.find()) {

            String arrayContents = arrayMatcher.group(1);
            if (arrayContents != null) {
                // Use a regular expression to match each element in the array, e.g., "testCase1"
                Pattern elementPattern = Pattern.compile("\"(.*?)\"");
                Matcher elementMatcher = elementPattern.matcher(arrayContents);

                // Loop through matches and add each element to the result list
                while (elementMatcher.find()) {
                    results.add(elementMatcher.group(1));
                }
            }

        } else {
            // If no array format is matched in the input, add an error message
            results.add(PromptConfiguration.PARSE_EXCEPTION_MSG);
        }

        return results;
    }
}
