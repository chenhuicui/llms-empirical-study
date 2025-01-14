package common.patterns.strategy.impl;

import android.util.Log;
import lombok.Data;
import mo.must.common.components.AbstractBaseInputComponent;
import mo.must.common.constants.TestConstants;
import mo.must.common.patterns.strategy.IHierarchyParserStrategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * AbstractHierarchyParserStrategy provides an abstract base for parsing and retrieving
 * lists of input components and their counts by type.
 * This class implements the Strategy pattern using the IHierarchyParserStrategy interface.
 *
 * @param <T> The type of input component type
 * @param <I> The type of input component instance
 */
@Data
public abstract class AbstractHierarchyParserStrategy<T, I> implements IHierarchyParserStrategy<T, I> {

    // Total count of all input components
    protected int inputTotalCount = 0;

    // Count of each type of input component
    protected final Map<T, Integer> componentCountMap = new HashMap<>();

    // Array of specified input component types
    protected T[] componentTypes;

    // List of input component IDs
    protected List<Integer> idList = new ArrayList<>();


    /**
     * Retrieves a list of input components from all specified input component types.
     *
     * @return List of input components
     */
    @Override
    public List<I> getInputComponents() {
        List<I> inputComponentList = new ArrayList<>();

        for (T type : getComponentTypes()) {
            List<I> inputComponents = fetchInputComponentsByType(type);

            if (!inputComponents.isEmpty()) {
                updateComponentCount(type, inputComponents.size());
            }
            Log.i(TestConstants.TAG, String.format("%s component count: %d", type, inputComponents.size()));
            inputComponentList.addAll(inputComponents);
        }
        for (I inputComponent : inputComponentList) {
            int id = ((AbstractBaseInputComponent<?>) inputComponent).getIntId();
            idList.add(id);
        }
        setInputTotalCount(inputComponentList.size());
        return inputComponentList;
    }

    @Override
    public int getInputTotalCount() {
        return inputTotalCount;
    }

    @Override
    public Map<T, Integer> getComponentCountMap() {
        return componentCountMap;
    }

    @Override
    public List<Integer> getInputIdList() {
        return idList;
    }

    /**
     * Updates the count for a specified input component type.
     *
     * @param type  The input component type
     * @param count The count of the specified input component type
     */
    protected void updateComponentCount(T type, int count) {
        componentCountMap.put(type, count);
    }

    /**
     * Returns a description of the component type with counts.
     * This method is abstract and should be implemented by subclasses.
     *
     * @return A string describing the component type with counts
     */
    public abstract String getComponentTypeWithNumDescription();


    /**
     * Fetches a list of input components by specified type.
     * This method is abstract and should be implemented by subclasses.
     *
     * @param type The input component type
     * @return List of input components
     */
    protected abstract List<I> fetchInputComponentsByType(T type);

}
