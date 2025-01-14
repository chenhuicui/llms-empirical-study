package common.patterns.strategy;

import java.util.List;
import java.util.Map;

/**
 * Interface for hierarchy parsing strategy.
 * This interface defines a general hierarchy parsing strategy where:
 * - T represents the component type or any other type.
 * - I represents the input component or any other input.
 *
 * @param <T> The component type or any other type
 * @param <I> The input component or any other input
 */
public interface IHierarchyParserStrategy<T, I> {
    List<I> getInputComponents();
    Map<T, Integer> getComponentCountMap();
    int getInputTotalCount();
    List<Integer> getInputIdList();
}
