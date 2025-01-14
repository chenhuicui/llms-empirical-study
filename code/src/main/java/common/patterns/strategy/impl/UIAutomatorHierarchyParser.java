package common.patterns.strategy.impl;

import androidx.test.uiautomator.UiObject2;
import mo.must.common.components.UIAutomatorInputComponent;
import mo.must.common.constants.TestConstants;
import mo.must.common.util.EspressoUtils;
import mo.must.common.util.UIAutomatorUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * UIAutomatorHierarchyParser class is used to parse the UI hierarchy,
 * find and retrieve input components of specified types.
 * This class is specifically designed to handle UIAutomator components and extends AbstractHierarchyParserStrategy.
 */
public class UIAutomatorHierarchyParser extends AbstractHierarchyParserStrategy<String, UIAutomatorInputComponent> {

    /**
     * Constructor to initialize an instance of UIAutomatorHierarchyParser.
     *
     * @param typeNames The names of the input component types to find
     */
    public UIAutomatorHierarchyParser(String... typeNames) {
        setComponentTypes(typeNames);
    }

    /**
     * Extracts input components from the UI hierarchy based on the specified input component type name.
     *
     * @param type The specified input component type name
     * @return The list of input components filtered by the specified type name
     */
    @Override
    protected List<UIAutomatorInputComponent> fetchInputComponentsByType(String type) {
        List<UIAutomatorInputComponent> inputWidgets = new ArrayList<>();

        List<UiObject2> objectList = UIAutomatorUtils.findAllUiObjectsByTypeName(type);

        for (int i = 0; i < objectList.size(); i++) {
            UIAutomatorInputComponent uIAutomatorInputComponent = new UIAutomatorInputComponent(objectList.get(i));

            uIAutomatorInputComponent.setWidgetIndex(TestConstants.convertToOrdinal(i + 1));
            uIAutomatorInputComponent.setIntId(EspressoUtils.convertStringIdToInt(UIAutomatorUtils.getIdOfUiObject2(objectList.get(i))));
            inputWidgets.add(uIAutomatorInputComponent);
        }

        return inputWidgets;
    }

    /**
     * Returns a description of component types with their counts.
     *
     * @return A string describing the component types with their counts
     */
    @Override
    public String getComponentTypeWithNumDescription() {
        // 获取输入组件类型和数量的映射
        Map<String, Integer> componentCountMap = getComponentCountMap();

        // 将输入组件类型和数量转化为字符串列表
        return componentCountMap.entrySet()
                .stream()
                .map(entry -> entry.getValue() + " " + UIAutomatorUtils.getClassSimpleNameByFullName(entry.getKey()))
                .collect(Collectors.joining(" and "));
    }

}
