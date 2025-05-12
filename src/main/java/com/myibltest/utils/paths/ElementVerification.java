package com.myibltest.utils.paths;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
//import org.testng.Assert;
//import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ElementVerification {

    /**
     * Verifies that every element's ID field is not null or empty
     * @param elements The list of element maps from the JSON response
     * @return true if all element IDs are valid, false otherwise
     */
    public boolean verifyElementIds(List<Map<String, Object>> elements) {
        boolean allValid = true;
        List<String> invalidElements = new ArrayList<>();

        if (elements == null) {
            System.out.println("Error: 'schedule.elements' is null.");
            return false;
        }

        for (int i = 0; i < elements.size(); i++) {
            Map<String, Object> element = elements.get(i);
            if (element != null) {
                String id = (String) element.get("id");
                if (id == null || id.trim().isEmpty()) {
                    allValid = false;
                    invalidElements.add("Element at index " + i + " has empty ID");
                }
            } else {
                allValid = false;
                invalidElements.add("Element at index " + i + " is null");
            }
        }

        // Log invalid element indices if any
        if (!invalidElements.isEmpty()) {
            System.out.println("Elements with invalid or missing IDs: " + String.join(", ", invalidElements));
        }

        return allValid;
    }

    /**
     * Verifies that the "type" in "episode" path for every item is always "episode"
     *
     * @param elements The list of element maps from the JSON response
     * @return true if all episode types are "episode", false otherwise
     */
    public boolean verifyEpisodeTypes(List<Map<String, Object>> elements) {
        boolean allValid = true;
        List<String> invalidElements = new ArrayList<>();

        if (elements == null) {
            System.out.println("Error: 'schedule.elements' is null.");
            return false;
        }

        for (int i = 0; i < elements.size(); i++) {
            Map<String, Object> element = elements.get(i);
            if (element != null) {
                Map<String, Object> episode = (Map<String, Object>) element.get("episode");
                if (episode != null) {
                    String type = (String) episode.get("type");
                    if (type == null || !type.equals("episode")) {
                        allValid = false;
                        invalidElements.add("Element at index " + i + " has episode type: " + type);
                    }
                } else {
                    allValid = false;
                    invalidElements.add("Element at index " + i + " is missing 'episode' object");
                }
            } else {
                allValid = false;
                invalidElements.add("Element at index " + i + " is null");
            }
        }

        // Log invalid element indices if any
        if (!invalidElements.isEmpty()) {
            System.out.println("Elements with invalid episode types: " + String.join(", ", invalidElements));
        }

        return allValid;
    }

    /**
     * Verifies that the "title" in "episode" path for every item is not null or empty
     *
     * @param elements The list of element maps from the JSON response
     * @return true if all episode titles are valid, false otherwise
     */
    public boolean verifyEpisodeTitles(List<Map<String, Object>> elements) {
        boolean allValid = true;
        List<String> invalidElements = new ArrayList<>();

        if (elements == null) {
            System.out.println("Error: 'schedule.elements' is null.");
            return false;
        }

        for (int i = 0; i < elements.size(); i++) {
            Map<String, Object> element = elements.get(i);
            if (element != null) {
                Map<String, Object> episode = (Map<String, Object>) element.get("episode");
                if (episode != null) {
                    String title = (String) episode.get("title");
                    if (title == null || title.trim().isEmpty()) {
                        allValid = false;
                        invalidElements.add("Element at index " + i + " has empty episode title");
                    }
                } else {
                    allValid = false;
                    invalidElements.add("Element at index " + i + " is missing 'episode' object");
                }
            } else {
                allValid = false;
                invalidElements.add("Element at index " + i + " is null");
            }
        }

        // Log invalid element indices if any
        if (!invalidElements.isEmpty()) {
            System.out.println("Elements with invalid or missing episode titles: " + String.join(", ", invalidElements));
        }

        return allValid;
    }

    /**
     * Verifies that only one episode has the "live" field set to true
     *
     * @param elements The list of element maps from the JSON response
     * @return true if only one live episode exists, false otherwise
     */
    public boolean verifySingleLiveEpisode(List<Map<String, Object>> elements) {
        int liveCount = 0;

        if (elements == null) {
            System.out.println("Error: 'schedule.elements' is null.");
            return false;
        }

        for (Map<String, Object> element : elements) {
            if (element != null) {
                Boolean live = (Boolean) element.get("live");
                if (Boolean.TRUE.equals(live)) {
                    liveCount++;
                }
            }
        }

        if (liveCount != 1) {
            System.out.println("Expected 1 live episode, but found " + liveCount);
            return false;
        }

        return true;
    }
}
