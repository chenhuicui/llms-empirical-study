package common.processor;

import android.util.Log;
import android.util.Xml;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import mo.must.common.components.AdjacentTexts;
import mo.must.common.components.JsonNodeInputComponent;
import mo.must.common.constants.TestConstants;
import mo.must.common.enums.InputComponentTypeEnum;
import mo.must.common.patterns.strategy.IComponentFinderStrategy;
import mo.must.common.patterns.strategy.factory.ComponentFinderFactory;
import mo.must.common.util.UIAutomatorUtils;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;


/**
 * Utility class for parsing and processing input components from XML content.
 */
public class JsonNodeProcessor {

    /**
     * Parses all input components from the given XML content and returns them in a list.
     *
     * @param xmlContent XML content containing the UI hierarchy
     * @return A list of parsed input components
     */
    public static List<JsonNodeInputComponent> getAllJsonNodeComponentsFromXml(String xmlContent) {
        List<JsonNodeInputComponent> result = new ArrayList<>();
        XmlPullParser parser = createXmlPullParser(xmlContent);
        try {
            int eventType;
            while ((eventType = parser.getEventType()) != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG && "node".equals(parser.getName()) && isRelevantNode(parser)) {
                    result.add(createInputComponentFromParser(parser));
                }
                parser.next();
            }
        } catch (XmlPullParserException | IOException e) {
            Log.e(TestConstants.TAG, "Failed to retrieve the component");
        }
        return result;
    }

    /**
     * Processes input components of a specific type.
     *
     * @param factory    Instance of ComponentFinderFactory
     * @param components List of all input components
     * @param type       Type of input components to process
     * @return A list of processed input components
     */
    public static List<JsonNodeInputComponent> processInputComponents(ComponentFinderFactory factory, List<JsonNodeInputComponent> components, InputComponentTypeEnum type) {
        List<JsonNodeInputComponent> inputComponents = new ArrayList<>();
        IComponentFinderStrategy strategy = factory.getStrategy(type);
        List<JsonNodeInputComponent> foundComponents = strategy.find(components);
        Log.i(TestConstants.TAG, String.format("%s component count: %d", type, foundComponents.size()));

        IntStream.rangeClosed(1, foundComponents.size()).forEach(index -> {
            JsonNodeInputComponent inputComponent = foundComponents.get(index - 1);
            inputComponent.setWidgetIndex(TestConstants.convertToOrdinal(index));
            inputComponent.setId(inputComponent.getInputWidget());
            inputComponent.setAdjacentTexts(new AdjacentTexts(AdjacentTextProcessor.getAdjacentTextMapFromSameParent(inputComponent.getStringId())));
            inputComponent.setClassName(UIAutomatorUtils.getClassNameById(inputComponent.getStringId()));
            inputComponents.add(inputComponent);
        });

        return inputComponents;
    }

    /**
     * Creates an input component from the XML parser.
     *
     * @param parser XML parser containing input component information
     * @return Created input component
     */
    private static JsonNodeInputComponent createInputComponentFromParser(XmlPullParser parser) {
        JsonNodeInputComponent inputComponent = new JsonNodeInputComponent();
        ObjectNode node = createObjectNodeFromParserAttributes(parser);
        inputComponent.setInputWidget(node);
        return inputComponent;
    }

    /**
     * Creates a JSON object node from the attributes in the XML parser.
     *
     * @param parser XML parser containing attributes
     * @return Created JSON object node
     */
    private static ObjectNode createObjectNodeFromParserAttributes(XmlPullParser parser) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode node = objectMapper.createObjectNode();
        IntStream.range(0, parser.getAttributeCount()).forEach(i -> node.put(parser.getAttributeName(i), parser.getAttributeValue(i)));
        return node;
    }

    /**
     * Creates and configures an XML parser to parse the given XML content.
     *
     * @param xmlContent XML content containing the UI hierarchy
     * @return Configured XML parser
     */
    private static XmlPullParser createXmlPullParser(String xmlContent) {
        XmlPullParser parser = Xml.newPullParser();
        try {
            parser.setInput(new StringReader(xmlContent));
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        return parser;
    }

    /**
     * Checks if the node the XML parser is at contains attributes related to "com.android.systemui".
     *
     * @param parser XML parser containing node information
     * @return False if the node contains any attributes related to "com.android.systemui", otherwise true
     */
    private static boolean isRelevantNode(XmlPullParser parser) {

        List<String> attributesToCheck = Arrays.asList("resource-id", "package");

        for (String attribute : attributesToCheck) {
            String attributeValue = parser.getAttributeValue(null, attribute);

            if (attributeValue != null && attributeValue.contains("com.android.systemui")) {
                return false;
            }
        }

        return true;
    }
}
