package common.patterns.strategy;


import mo.must.common.components.JsonNodeInputComponent;

import java.util.List;

/**
 * The IComponentFinderStrategy interface defines a strategy for finding components.
 * This strategy is designed to extract specific types or conditions of components from a given list of components.
 * Using the strategy design pattern allows the code to be more flexible and makes it easier to add, remove,
 * or modify finding strategies without changing the code that uses these strategies.
 */
public interface IComponentFinderStrategy {
    /**
     * Finds specific components from the given list of components based on the specified finding strategy.
     * Classes that implement this interface should provide the concrete finding logic.
     *
     * @param components The list of components to search
     * @return A list of components that match the finding criteria
     */
    List<JsonNodeInputComponent> find(List<JsonNodeInputComponent> components);
}
