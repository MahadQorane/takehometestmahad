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
     *
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
     * Set the base URL for API requests
     *
     * @param baseUrl the base URL
     */
    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
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
     * Add a header to the request
     *
     * @param name header name
     * @param value header value
     * @return this instance for method chaining
     */
    public RestAssuredUtils addHeader(String name, String value) {
        requestSpec.header(name, value);
        return this;
    }

    /**
     * Add multiple headers to the request
     *
     * @param headers map of header name-value pairs
     * @return this instance for method chaining
     */
    public RestAssuredUtils addHeaders(Map<String, String> headers) {
        requestSpec.headers(headers);
        return this;
    }

    /**
     * Add a query parameter to the request
     *
     * @param name parameter name
     * @param value parameter value
     * @return this instance for method chaining
     */
    public RestAssuredUtils addQueryParam(String name, String value) {
        requestSpec.queryParam(name, value);
        return this;
    }

    /**
     * Add multiple query parameters to the request
     *
     * @param params map of parameter name-value pairs
     * @return this instance for method chaining
     */
    public RestAssuredUtils addQueryParams(Map<String, String> params) {
        requestSpec.queryParams(params);
        return this;
    }

    /**
     * Set the request body
     *
     * @param body request body object
     * @return this instance for method chaining
     */
    public RestAssuredUtils setBody(Object body) {
        requestSpec.body(body);
        return this;
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
     * Execute a POST request
     *
     * @param endpoint the API endpoint (will be appended to the base URL)
     * @return the response
     */
    public Response post(String endpoint) {
        String url = baseUrl + endpoint;
        LOGGER.info("Executing POST request to {}", url);
        lastResponse = requestSpec.when().post(url);
        LOGGER.info("Response status code: {}", lastResponse.getStatusCode());
        return lastResponse;
    }

    /**
     * Execute a PUT request
     *
     * @param endpoint the API endpoint (will be appended to the base URL)
     * @return the response
     */
    public Response put(String endpoint) {
        String url = baseUrl + endpoint;
        LOGGER.info("Executing PUT request to {}", url);
        lastResponse = requestSpec.when().put(url);
        LOGGER.info("Response status code: {}", lastResponse.getStatusCode());
        return lastResponse;
    }

    /**
     * Execute a DELETE request
     *
     * @param endpoint the API endpoint (will be appended to the base URL)
     * @return the response
     */
    public Response delete(String endpoint) {
        String url = baseUrl + endpoint;
        LOGGER.info("Executing DELETE request to {}", url);
        lastResponse = requestSpec.when().delete(url);
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
