package common.patterns.strategy.factory;


import mo.must.common.enums.InputComponentTypeEnum;
import mo.must.common.parser.HierarchyParser;
import mo.must.common.patterns.strategy.IComponentFinderStrategy;

import java.util.HashMap;
import java.util.Map;

/**
 * ComponentFinderFactory is an implementation of the factory design pattern,
 * used to create an appropriate ComponentFinderStrategy based on the specified input component type.
 * This allows dynamic strategy selection rather than hard-coded strategy choices.
 */
public class ComponentFinderFactory {

    // A map storing the mapping between various component types and their corresponding strategies
    private final Map<InputComponentTypeEnum, IComponentFinderStrategy> strategies = new HashMap<>();

    /**
     * Constructor to initialize the strategy map.
     */
    public ComponentFinderFactory() {
        initializeStrategies();
    }

    /**
     * Initializes the strategy map.
     * Assigns the appropriate finder strategy based on different input component types.
     */
    private void initializeStrategies() {
        // Add finder strategy for EditText component type
        this.strategies.put(InputComponentTypeEnum.EditText, components -> HierarchyParser.findComponents(components, HierarchyParser::isEditTextOrAutoCompleteTextView));
        // TODO: If there are other component types that need to add strategies, you can expand here. e.g., Spinner
    }

    /**
     * Returns the appropriate finder strategy based on the specified component type.
     *
     * @param type The input component type
     * @return The finder strategy corresponding to the component type
     */
    public IComponentFinderStrategy getStrategy(InputComponentTypeEnum type) {
        return strategies.get(type);
    }
}
