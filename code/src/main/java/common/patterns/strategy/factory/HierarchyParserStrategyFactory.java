package common.patterns.strategy.factory;


import android.util.Log;
import mo.must.common.constants.TestConstants;
import mo.must.common.enums.InputComponentTypeEnum;
import mo.must.common.enums.StrategyTypeEnum;
import mo.must.common.patterns.strategy.IHierarchyParserStrategy;
import mo.must.common.patterns.strategy.impl.JsonNodeHierarchyParser;
import mo.must.common.patterns.strategy.impl.UIAutomatorHierarchyParser;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Factory class for creating and returning specified hierarchy parser strategies.
 * This class implements the strategy pattern and associates strategy types with
 * their creator methods through a static map.
 * When a specific type of strategy is requested, the factory uses the associated creator method to
 * generate and return a new strategy instance.
 * This factory class can be easily extended to add new strategies by simply adding
 * the new strategy type and its corresponding creator method to the map.

 */
@SuppressWarnings("rawtypes")
public class HierarchyParserStrategyFactory {

    /**
     * A functional interface responsible for creating IHierarchyParserStrategy objects.
     */
    @FunctionalInterface
    interface StrategyCreator {
        IHierarchyParserStrategy create(String content, Object... types);
    }

    // A map to associate strategy types and their creators.
    private static final Map<StrategyTypeEnum, StrategyCreator> strategyMap = new HashMap<>();

    /**
     * A functional interface responsible for converting types.
     */
    @FunctionalInterface
    interface TypeConverter {
        Object[] convert(Object[] types);
    }

    // A map to associate strategy types with their type converters.
    private static final Map<StrategyTypeEnum, TypeConverter> typeConverterMap = new HashMap<>();

    // Static block to initialize the strategy map.
    static {
        strategyMap.put(StrategyTypeEnum.UI_AUTOMATOR,
                (content, types) -> new UIAutomatorHierarchyParser((String[]) types));

        strategyMap.put(StrategyTypeEnum.JSON_NODE,
                (content, types) -> new JsonNodeHierarchyParser(content, (InputComponentTypeEnum[]) types));

        typeConverterMap.put(StrategyTypeEnum.UI_AUTOMATOR, types ->
                Arrays.stream(types).map(obj -> (String) obj).toArray(String[]::new));

        typeConverterMap.put(StrategyTypeEnum.JSON_NODE, types ->
                Arrays.stream(types).map(obj -> (InputComponentTypeEnum) obj).toArray(InputComponentTypeEnum[]::new));

        Log.d(TestConstants.TAG, "Strategy and type converter mapping initialized.");

    }

    /**
     * Creates a strategy based on the specified type.
     *
     * @param type    The strategy type.
     * @param content The content for the strategy.
     * @param types   The types needed for the strategy.
     * @return The created strategy.
     * @throws IllegalArgumentException if the strategy type is not valid.
     */
    public static IHierarchyParserStrategy createStrategy(StrategyTypeEnum type, String content, Object... types) {
        if (!strategyMap.containsKey(type)) {
            Log.d(TestConstants.TAG, "Creating strategy for type: " + type);
            throw new IllegalArgumentException("Invalid strategy type");
        }

        // Perform type checking and conversion based on the strategy type
        Object[] convertedTypes;
        TypeConverter converter = typeConverterMap.get(type);
        if (converter != null) {
            convertedTypes = converter.convert(types);
        } else {
            Log.e(TestConstants.TAG, "Strategy type not found for corresponding type converter: " + type);
            throw new IllegalArgumentException("Invalid strategy type");
        }

        // Retrieve and invoke the corresponding strategy creator from the map, and return the strategy object
        Log.d(TestConstants.TAG, "Strategy type successfully created: " + type);
        return Objects.requireNonNull(strategyMap.get(type)).create(content, convertedTypes);
    }
}
