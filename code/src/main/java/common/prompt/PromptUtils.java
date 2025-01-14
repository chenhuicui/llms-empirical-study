package common.prompt;


import mo.must.common.components.AbstractBaseInputComponent;
import mo.must.common.components.AdjacentTexts;
import mo.must.common.enums.Direction;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Utility class for generating context and prompt messages for various input components
 * in the application's user interface.
 */
public class PromptUtils {

    /**
     * Generates a contextual description for the given input component.
     *
     * @param inputComponent The input component for which to generate the context description
     * @param <T>            The type of the input component, extending AbstractBaseInputComponent
     * @return A string describing the horizontal and vertical context of the component
     */
    public static <T extends AbstractBaseInputComponent<?>> String generateContextForComponent(T inputComponent) {
        String leftAdjacentText = constructContext(inputComponent, Direction.LEFT);
        String rightAdjacentText = constructContext(inputComponent, Direction.RIGHT);
        String aboveAdjacentText = constructContext(inputComponent, Direction.ABOVE);
        String belowAdjacentText = constructContext(inputComponent, Direction.BELOW);
        return Stream.of(leftAdjacentText, rightAdjacentText, aboveAdjacentText, belowAdjacentText)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.joining(" "));
    }


    /**
     * Constructs context description based on the direction relative to the input component.
     *
     * @param inputComponent The input component for which to construct the context
     * @param direction      The direction to construct the context for
     * @param <T>            The type of the input component, extending AbstractBaseInputComponent
     * @return A string describing the adjacent context in the specified direction
     */
    private static <T extends AbstractBaseInputComponent<?>> String constructContext(T inputComponent, Direction direction) {
        AdjacentTexts adjacentTexts = inputComponent.getAdjacentTexts();

        return Optional.ofNullable(adjacentTexts.getTextForDirection(direction)).filter(s -> !s.isEmpty())
                .map(s -> String.format(PromptConfiguration.ADJACENT_LABEL_TEMPLATE,
                        direction.name().toLowerCase(),
                        inputComponent.getWidgetIndex(),
                        inputComponent.getSimpleClassName()
                        , s))
                .orElse("");
    }

    /**
     /**
     * Generates a prompt message for the given input component.
     *
     * @param inputComponent The input component for which to generate the prompt message
     * @param <I>            The type of the input component, extending AbstractBaseInputComponent
     * @return A string containing the prompt message for the input component
     */
    public static <I extends AbstractBaseInputComponent<?>> String generatePromptForComponent(I inputComponent) {
        String basePrompt = String.format(PromptConfiguration.COMPONENT_REFERENCE_TEMPLATE,
                inputComponent.getWidgetIndex(), inputComponent.getSimpleClassName());
        return basePrompt + inputComponent.getHintTextForInputComponent();
    }

}
