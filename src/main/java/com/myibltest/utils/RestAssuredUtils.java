package com.myibltest.utils;

import io.restassured.RestAssured;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.myibltest.config.ConfigManager;

import java.util.Map;

/**
 * Utility class for making REST API calls using RestAssured
 */
public class RestAssuredUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(RestAssuredUtils.class);
    private static final ConfigManager configManager = ConfigManager.getInstance();

    private String baseUrl;
    private RequestSpecification requestSpec;
    private Response lastResponse;

    /**
     * Constructor with default base URL
     */
    public RestAssuredUtils() {
        this(configManager.getProperty("api.base.url"));
    }

    /**
     * Constructor with specified base URL
     * @param baseUrl the base URL for API requests
     */
    public RestAssuredUtils(String baseUrl) {
        this.baseUrl = baseUrl;
        initializeRequestSpec();
    }

    /**
     * Initialize the request specification with default configuration
     */
    private void initializeRequestSpec() {
        int timeout = configManager.getIntProperty("api.timeout", 10000);

        RestAssuredConfig config = RestAssured.config()
                .httpClient(HttpClientConfig.httpClientConfig()
                        .setParam("http.connection.timeout", timeout)
                        .setParam("http.socket.timeout", timeout));

        requestSpec = RestAssured.given()
                .config(config)
                .contentType(ContentType.JSON)
                .log().ifValidationFails();
    }


    /**
     * Get the base URL for API requests
     *
     * @return the base URL
     */
    public String getBaseUrl() {
        return baseUrl;
    }

    /**
     * Execute a GET request
     *
     * @param endpoint the API endpoint (will be appended to the base URL)
     * @return the response
     */
    public Response get(String endpoint) {
        String url = baseUrl + endpoint;
        LOGGER.info("Executing GET request to {}", url);
        lastResponse = requestSpec.when().get(url);
        LOGGER.info("Response status code: {}", lastResponse.getStatusCode());
        return lastResponse;
    }

    /**
     * Get the last response
     *
     * @return the last response
     */
    public Response getLastResponse() {
        return lastResponse;
    }
}
