package common.components;

import mo.must.common.enums.Direction;

import java.util.Map;

/**
 * Represents a set of adjacent text information.
 * Uses a map to store the content of adjacent texts in various directions.
 */
public class AdjacentTexts {
    private final Map<String, String> adjacentTextMap;

    /**
     * Constructs an AdjacentTexts instance with the specified map of adjacent text information.
     *
     * @param adjacentTextMap a map containing the adjacent text contents for various directions
     */
    public AdjacentTexts(Map<String, String> adjacentTextMap) {
        this.adjacentTextMap = adjacentTextMap;
    }

    /**
     * Retrieves the adjacent text content for the specified direction.
     *
     * @param direction the specified direction, e.g., "ABOVE", "BELOW", "LEFT", or "RIGHT"
     * @return the content of the adjacent text if it exists in the map, otherwise returns an empty string
     */
    public String getTextForDirection(Direction direction) {
        return adjacentTextMap.getOrDefault(direction.name(), "");
    }
}