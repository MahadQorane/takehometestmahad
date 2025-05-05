package com.myibltest.reporting;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.restassured.response.Response;

/**
 * Utility class for test reporting and logging
 */
public class ReportManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReportManager.class);

    /**
     * Private constructor to prevent instantiation
     */
    private ReportManager() {
        // Private constructor to hide the implicit public one
    }

    /**
     * Log request details
     *
     * @param method HTTP method
     * @param url request URL
     */
    public static void logRequest(String method, String url) {
        LOGGER.info("Sending {} request to: {}", method, url);
    }

    /**
     * Log request details with headers
     *
     * @param method HTTP method
     * @param url request URL
     * @param headers request headers
     */
    public static void logRequest(String method, String url, String headers) {
        LOGGER.info("Sending {} request to: {}", method, url);
        LOGGER.info("Headers: {}", headers);
    }

    /**
     * Log request details with headers and body
     *
     * @param method HTTP method
     * @param url request URL
     * @param headers request headers
     * @param body request body
     */
    public static void logRequest(String method, String url, String headers, String body) {
        LOGGER.info("Sending {} request to: {}", method, url);
        LOGGER.info("Headers: {}", headers);
        LOGGER.info("Body: {}", body);
    }

    /**
     * Log response details
     *
     * @param response the RestAssured response
     */
    public static void logResponse(Response response) {
        LOGGER.info("Response Status: {}", response.getStatusLine());
        LOGGER.info("Response Time: {} ms", response.getTime());
        LOGGER.info("Response Headers: {}", response.getHeaders());

        // Log response body only if it's not too large
        String body = response.getBody().asString();
        if (body.length() <= 1000) {
            LOGGER.info("Response Body: {}", body);
        } else {
            LOGGER.info("Response Body: {} (truncated)", body.substring(0, 1000));
        }
    }

    /**
     * Log a test step
     *
     * @param step step description
     */
    public static void logStep(String step) {
        LOGGER.info("STEP: {}", step);
    }

    /**
     * Log test step result
     *
     * @param step step description
     * @param result result description
     */
    public static void logStepResult(String step, String result) {
        LOGGER.info("STEP: {} - RESULT: {}", step, result);
    }

    /**
     * Log test assertion
     *
     * @param assertion assertion description
     * @param expected expected value
     * @param actual actual value
     */
    public static void logAssertion(String assertion, Object expected, Object actual) {
        LOGGER.info("ASSERTION: {} - Expected: [{}], Actual: [{}]", assertion, expected, actual);
    }

    /**
     * Log test failure
     *
     * @param message failure message
     */
    public static void logFailure(String message) {
        LOGGER.error("FAILURE: {}", message);
    }
}
