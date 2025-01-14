package common.components;

import lombok.Data;

/**
 * Abstract class providing basic input component functionalities.
 * This class defines some fundamental properties and behaviors for input components,
 * such as adjacent texts and indexes related to the input component.
 *
 * @param <T> the type of the input component (e.g., JsonNode and UiObject2.)
 *
 * <p> This class utilizes Lombok's @Data annotation to automatically generate getter,
 * setter, toString, equals, and hashCode methods for the defined fields.
 * </p>
 *
 * Author: cuichenhui
 * Date: 2023-10-23
 */
@Data
public abstract class AbstractBaseInputComponent<T> {
    protected T inputWidget; // The actual input widget
    protected AdjacentTexts adjacentTexts = null; // Optional texts adjacent to the input component
    protected String widgetIndex; // Index of widget for when multiple instances are present
    protected int intId = 0; // Integer identifier for the component
    protected String stringId = ""; // String identifier for the component
    protected String simpleClassName; // Simple class name of the input component


    /**
     * Retrieves the hint text for the input component.
     *
     * @return the hint text associated with the input component
     */
    public abstract String getHintTextForInputComponent();
}