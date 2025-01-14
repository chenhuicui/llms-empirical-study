package common.components;

import androidx.test.uiautomator.UiObject2;
import mo.must.common.processor.AdjacentTextProcessor;
import mo.must.common.prompt.PromptConfiguration;
import mo.must.common.util.UIAutomatorUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Optional;

/**
 * The UIAutomatorInputComponent class represents an input component using the UIAutomator
 * framework along with its adjacent texts.
 * This class extends AbstractBaseInputComponent and is used to store information
 * related to the input component and its adjacent texts.
 */
public class UIAutomatorInputComponent extends AbstractBaseInputComponent<UiObject2> {

    /**
     * Creates a new instance of UIAutomatorInputComponent for the given input component.
     * Also, extracts and sets the adjacent text information for the input component.
     *
     * @param inputWidget the input component defined using UI Automator framework
     */
    public UIAutomatorInputComponent(UiObject2 inputWidget) {
        setInputWidget(inputWidget);
        setAdjacentTexts(
                new AdjacentTexts(AdjacentTextProcessor.getAdjacentTextMap(UIAutomatorUtils.getIdOfUiObject2(inputWidget))));
        setSimpleClassName(UIAutomatorUtils.getClassSimpleName(inputWidget));
        setStringId(UIAutomatorUtils.getIdOfUiObject2(inputWidget));
    }

    /**
     * Retrieves the hint text for the input component.
     * This method searches for non-empty text or content description in the UiObject2 instance.
     * If neither is found, it attempts to use the "resource-id".
     *
     * @return the formatted hint text for the input component
     */
    @Override
    public String getHintTextForInputComponent() {
        StringBuilder resultBuilder = new StringBuilder();
        String optionalValue = getFirstNonEmptyValue();

        // If a non-empty value is found, add it to the result string
        if (!StringUtils.isBlank(optionalValue)) {
            return resultBuilder.append(String.format(PromptConfiguration.HINT_TEXT_TEMPLATE, optionalValue)).toString();
        }

        // If no non-empty value is found, check the "resource-id"
        Optional<String> resourceId = Optional.ofNullable(stringId)
                .map(String::valueOf)
                .filter(s -> !s.isEmpty());
        resourceId.ifPresent(s -> resultBuilder.append(String.format(PromptConfiguration.RESOURCE_ID_TEMPLATE, s)));

        return resultBuilder.toString();
    }

    /**
     * Gets the first non-empty value from the input component.
     * It checks the text and content description of the UiObject2.
     *
     * @return the first non-empty value as a string
     */
    private String getFirstNonEmptyValue() {
        if (!StringUtils.isBlank(inputWidget.getText())) {
            return inputWidget.getText();
        }
        if (!StringUtils.isBlank(inputWidget.getContentDescription())) {
            return inputWidget.getContentDescription();
        }
        return "";
    }
}