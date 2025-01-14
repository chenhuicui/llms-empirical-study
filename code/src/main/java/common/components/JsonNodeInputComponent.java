package common.components;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;
import mo.must.common.prompt.PromptConfiguration;
import mo.must.common.util.EspressoUtils;
import mo.must.common.util.UIAutomatorUtils;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

/**
 * The JsonNodeInputComponent class represents an input component along with its adjacent texts.
 * This class extends AbstractBaseInputComponent and stores specific information for the input component,
 * such as the class name, simple class name, and resource ID.
 * <p>
 * This class utilizes Jackson's JsonNode to handle JSON data structures and Lombok's @Getter
 * annotation to automatically generate getter methods for the fields.
 */
@Getter
public class JsonNodeInputComponent extends AbstractBaseInputComponent<JsonNode> {

    private String className = "";

    /**
     * Retrieves the hint text for the input component.
     * This method searches for specific keys in the JsonNode to find the first non-empty value,
     * and formats this value with a hint text template. If no hint text is found, it attempts
     * to use the "resource-id".
     *
     * @return the formatted hint text for the input component
     */
    @Override
    public String getHintTextForInputComponent() {
        // Keys to check for hint text
        String[] keys = {"label", "text-hint", "text"};
        StringBuilder resultBuilder = new StringBuilder();

        Optional<String> optionalValue = getFirstNonEmptyValue(keys);

        // If a non-empty value is found, add it to the result string
        if (optionalValue.isPresent()) {
            return resultBuilder.append(String.format(PromptConfiguration.HINT_TEXT_TEMPLATE, optionalValue.get())).toString();
        }

        // If no non-empty value is found, check the "resource-id"
        Optional<String> resourceId = Optional.ofNullable(stringId)
                .map(String::valueOf)
                .filter(s -> !s.isEmpty());
        resourceId.ifPresent(s -> resultBuilder.append(String.format(PromptConfiguration.RESOURCE_ID_TEMPLATE, s)));

        return resultBuilder.toString();
    }

    /**
     * Sets the class name for the input component and updates the simple class name based on the full class name.
     *
     * @param className the full class name of the input component
     */
    public void setClassName(String className) {
        this.className = className;
        this.simpleClassName = UIAutomatorUtils.getClassSimpleNameByFullName(className);
    }

    /**
     * Sets the IDs (both string and integer) for the input component based on the JsonNode.
     *
     * @param inputWidget the JsonNode representing the input component
     */
    public void setId(JsonNode inputWidget) {
        this.stringId = inputWidget.get("resource-id").asText();
        this.intId = EspressoUtils.convertStringIdToInt(stringId);
    }

    /**
     * Gets the first non-empty value from the JsonNode based on the provided keys.
     *
     * @param keys the array of keys to check in the JsonNode
     * @return an Optional containing the first non-empty value, if found
     */
    private Optional<String> getFirstNonEmptyValue(String[] keys) {
        return Arrays.stream(keys)
                .map(key -> inputWidget.get(key))
                .filter(Objects::nonNull)
                .map(String::valueOf)
                .filter(s -> !s.isEmpty())
                .findFirst();
    }
}