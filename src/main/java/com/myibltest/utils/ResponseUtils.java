package com.myibltest.utils;

import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * Utility class for handling API responses
 */
public class ResponseUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(ResponseUtils.class);
    private final Response response;

    /**
     * Constructor
     *
     * @param response the RestAssured response
     */
    public ResponseUtils(Response response) {
        this.response = response;
    }

    /**
     * Get the HTTP status code
     *
     * @return the status code
     */
    public int getStatusCode() {
        return response.getStatusCode();
    }

    /**
     * Get the response time in milliseconds
     *
     * @return the response time
     */
    public long getResponseTime() {
        return response.getTime();
    }

    /**
     * Check if the response time is less than a specified threshold
     *
     * @param thresholdMillis the threshold in milliseconds
     * @return true if response time is less than the threshold, false otherwise
     */
    public boolean isResponseTimeLessThan(long thresholdMillis) {
        long actualTime = getResponseTime();
        boolean result = actualTime < thresholdMillis;

        if (result) {
            LOGGER.info("Response time {} ms is less than threshold {} ms", actualTime, thresholdMillis);
        } else {
            LOGGER.warn("Response time {} ms exceeds threshold {} ms", actualTime, thresholdMillis);
        }

        return result;
    }

    /**
     * Get a value from the response using JsonPath
     *
     * @param path the JsonPath expression
     * @return the value at the specified path
     */
    public Object getJsonValue(String path) {
        return response.jsonPath().get(path);
    }

    /**
     * Get a string value from the response using JsonPath
     *
     * @param path the JsonPath expression
     * @return the string value at the specified path
     */
    public String getJsonString(String path) {
        return response.jsonPath().getString(path);
    }

    /**
     * Get an integer value from the response using JsonPath
     *
     * @param path the JsonPath expression
     * @return the integer value at the specified path
     */
    public int getJsonInt(String path) {
        return response.jsonPath().getInt(path);
    }

    /**
     * Get a boolean value from the response using JsonPath
     *
     * @param path the JsonPath expression
     * @return the boolean value at the specified path
     */
    public boolean getJsonBoolean(String path) {
        return response.jsonPath().getBoolean(path);
    }

    /**
     * Get a list from the response using JsonPath
     *
     * @param path the JsonPath expression
     * @return the list at the specified path
     */
    public List<Object> getJsonList(String path) {
        return response.jsonPath().getList(path);
    }

    /**
     * Get a map from the response using JsonPath
     *
     * @param path the JsonPath expression
     * @return the map at the specified path
     */
    public Map<String, Object> getJsonMap(String path) {
        return response.jsonPath().getMap(path);
    }

    /**
     * Check if a path exists in the response
     *
     * @param path the JsonPath expression
     * @return true if the path exists, false otherwise
     */
    public boolean hasJsonPath(String path) {
        try {
            return response.jsonPath().get(path) != null;
        } catch (Exception e) {
            // If there's an exception while accessing the path, it doesn't exist
            return false;
        }
    }

    /**
     * Get all items as a list of maps from an array response
     *
     * @return list of items as maps
     */
    public List<Map<String, Object>> getJsonArray() {
        return response.jsonPath().getList("$");
    }

    /**
     * Count items in the response that match a specific condition
     *
     * @param path the JsonPath to the boolean property to check
     * @param value the value to match
     * @return count of matching items
     */
    public int countItemsWithValue(String path, boolean value) {
        List<Map<String, Object>> items = getJsonArray();
        int count = 0;

        for (int i = 0; i < items.size(); i++) {
            try {
                boolean itemValue = response.jsonPath().getBoolean("[" + i + "]" + path);
                if (itemValue == value) {
                    count++;
                }
            } catch (Exception e) {
                // Skip if the path doesn't exist for this item
            }
        }

        return count;
    }

    /**
     * Get the response body as a string
     *
     * @return the response body
     */
    public String getBodyAsString() {
        return response.getBody().asString();
    }

    /**
     * Get the response headers
     *
     * @return the response headers
     */
    public Map<String, String> getHeaders() {
        return response.getHeaders().asList().stream()
                .collect(java.util.stream.Collectors.toMap(
                        io.restassured.http.Header::getName,
                        io.restassured.http.Header::getValue,
                        (existing, replacement) -> existing + ", " + replacement
                ));
    }

    /**
     * Get a specific header value
     *
     * @param headerName the name of the header
     * @return the header value or null if not found
     */
    public String getHeader(String headerName) {
        return response.getHeader(headerName);
    }

    /**
     * Get the content type of the response
     *
     * @return the content type
     */
    public String getContentType() {
        return response.getContentType();
    }

    /**
     * Get the original RestAssured response
     *
     * @return the RestAssured response
     */
    public Response getResponse() {
        return response;
    }
}