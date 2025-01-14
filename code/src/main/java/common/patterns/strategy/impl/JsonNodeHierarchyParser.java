package common.patterns.strategy.impl;


import mo.must.common.components.JsonNodeInputComponent;
import mo.must.common.enums.InputComponentTypeEnum;
import mo.must.common.patterns.strategy.factory.ComponentFinderFactory;
import mo.must.common.processor.JsonNodeProcessor;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * JsonNodeHierarchyParser class is used to parse the provided XML content,
 * find, and retrieve input components of specified types.
 * It extends from AbstractHierarchyParserStrategy and uses JsonNode-specific processing.
 */
public class JsonNodeHierarchyParser extends AbstractHierarchyParserStrategy<InputComponentTypeEnum, JsonNodeInputComponent> {

    // Stores the XML hierarchy content
    private final String xmlContent;

    /**
     * Constructor to initialize an instance of JsonNodeHierarchyParser.
     *
     * @param xmlContent The XML content string to parse
     * @param types      The specified types of input components to find
     */
    public JsonNodeHierarchyParser(String xmlContent, InputComponentTypeEnum... types) {
        this.xmlContent = xmlContent;
        this.componentTypes = types;
    }

    /**
     * Extracts input components from the XML content based on the specified input component type.
     *
     * @param type The specified input component type
     * @return The list of input components filtered by the specified type
     */
    @Override
    protected List<JsonNodeInputComponent> fetchInputComponentsByType(InputComponentTypeEnum type) {
        ComponentFinderFactory factory = new ComponentFinderFactory();
        List<JsonNodeInputComponent> components = JsonNodeProcessor.getAllJsonNodeComponentsFromXml(xmlContent);
        return JsonNodeProcessor.processInputComponents(factory, components, type);
    }

    /**
     * Returns a description of component types with their counts.
     *
     * @return A string describing the component types with their counts
     */
    @Override
    public String getComponentTypeWithNumDescription() {
        Map<InputComponentTypeEnum, Integer> componentCountMap = getComponentCountMap();

        return componentCountMap.entrySet()
                .stream()
                .map(entry -> entry.getValue() + " " + (entry.getKey() != null ?
                        (entry.getKey()).name() : null))
                .collect(Collectors.joining(" and "));
    }

}
