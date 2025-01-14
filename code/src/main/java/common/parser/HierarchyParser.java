package common.parser;

import mo.must.common.components.JsonNodeInputComponent;
import mo.must.common.prompt.PromptConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * HierarchyParserUtils provides utility methods for processing input components in a hierarchy.
 */
public class HierarchyParser {

    /**
     * Finds and returns a list of input components that match a given predicate condition.
     *
     * @param components The list of input components to search.
     * @param predicate  The predicate condition used to filter the components.
     * @return A list of input components that match the predicate condition.
     */
    public static List<JsonNodeInputComponent> findComponents(List<JsonNodeInputComponent> components, Predicate<JsonNodeInputComponent> predicate) {
        // Create a list to store input components that match the condition
        List<JsonNodeInputComponent> foundComponents = new ArrayList<>();
        // Iterate through the input components list
        for (JsonNodeInputComponent component : components) {
            // Check if the current component matches the predicate condition
            if (predicate.test(component)) {
                // If it matches, add the component to the foundComponents list
                foundComponents.add(component);
            }
        }
        // Return the list of components that match the predicate condition
        return foundComponents;
    }

    /**
     * Checks whether the given input component is of type EditText or AutoCompleteTextView.
     *
     * @param component The input component to check.
     * @return true if the component is of type EditText or AutoCompleteTextView; false otherwise.
     */
    public static boolean isEditTextOrAutoCompleteTextView(JsonNodeInputComponent component) {
        String classType = component.getInputWidget().get("class").asText();
        return PromptConfiguration.EDITTEXT_CLASS_NAME.equals(classType) || PromptConfiguration.AUTOCOMPLETE_TEXTVIEW_CLASS_NAME.equals(classType);
    }
}