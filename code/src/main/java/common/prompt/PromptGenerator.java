package common.prompt;


import mo.must.common.components.AbstractBaseInputComponent;
import mo.must.common.patterns.strategy.impl.AbstractHierarchyParserStrategy;
import mo.must.common.util.RobotiumUtils;

import java.util.List;
import java.util.stream.Collectors;

public class PromptGenerator<I extends AbstractBaseInputComponent<?>> {
    protected boolean isValid;
    protected AbstractHierarchyParserStrategy<?, I> parser;
    protected List<I> inputComponentList;

    public PromptGenerator(AbstractHierarchyParserStrategy<?, I> parser, boolean isValid) {
        this.parser = parser;
        this.inputComponentList = parser.getInputComponents();
        this.isValid = isValid;
    }

    public String generatePrompt(PromptConfiguration config) {
        StringBuilder prompt = new StringBuilder();
        if (config.isIncludeGlobal()) {
            prompt.append(generateGlobalPrompt());
        }
        if (config.isIncludeComponent()) {
            prompt.append(generateComponentPrompt());
        }
        if (config.isIncludeRelated()) {
            prompt.append(generateRelatedPrompt());
        }
        if (config.isIncludeRestrictive()) {
            prompt.append(getRestrictivePrompt());
        }
        if (config.isIncludeGuiding()) {
            prompt.append(getGuidingPrompt());
        }
        return prompt.toString();
    }

    private String generateGlobalPrompt() {
        return String.format(PromptConfiguration.APP_PAGE_DESCRIPTION_TEMPLATE,
                RobotiumUtils.getAppName(),
                parser.getInputTotalCount(),
                RobotiumUtils.getCurrentActivitySimpleName(),
                parser.getComponentTypeWithNumDescription()
        );
    }

    private String generateComponentPrompt() {
        StringBuilder componentPrompt = new StringBuilder();
        for (I inputComponent : inputComponentList) {
            componentPrompt.append(PromptUtils.generatePromptForComponent(inputComponent));
        }
        return componentPrompt.toString();
    }

    private String generateRelatedPrompt() {
        return inputComponentList.stream()
                .map(PromptUtils::generateContextForComponent)
                .filter(context -> !context.isEmpty())
                .collect(Collectors.joining(" "));
    }

    private String getRestrictivePrompt() {
        String template = isValid ? PromptConfiguration.TEXT_INPUTS_GUIDELINE_TEMPLATE : PromptConfiguration.INVALID_TEXT_INPUTS_GUIDELINE_TEMPLATE;
        return String.format(template, parser.getComponentTypeWithNumDescription());
    }

    private String getGuidingPrompt() {
        return PromptConfiguration.GUIDING_GUIDELINE_TEMPLATE;
    }
}
