package com.myibltest.stepdef;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.Before;
import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import io.restassured.response.Response;
import org.junit.Assert;

import com.myibltest.utils.RestAssuredUtils;
import com.myibltest.utils.ResponseUtils;
import com.myibltest.reporting.ReportManager;
import com.myibltest.config.ConfigManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Step definitions for API testing
 */
public class ApiStepDef {

    private RestAssuredUtils restUtils;
    private Response response;
    private ResponseUtils responseUtils;
    private Scenario scenario;
    private ConfigManager configManager;

    /**
     * Setup before each scenario
     *
     * @param scenario the current scenario
     */
    @Before
    public void setup(Scenario scenario) {
        this.scenario = scenario;
        this.configManager = ConfigManager.getInstance();
        ReportManager.logStep("Starting scenario: " + scenario.getName()); }
        /**
         * Verify every elements ID field is not null or empty
         */
        @Then("every elements ID field should not be null or empty")
        public void verifyElementsIdIsNotNullOrEmpty() {
            List<Map<String, Object>> elements = responseUtils.getResponse().jsonPath().getList("$");
            ReportManager.logStep("Checking ID fields for " + elements.size() + " elements");

            for (int i = 0; i < elements.size(); i++) {
                String id = responseUtils.getJsonString("[" + i + "].id");
                ReportManager.logAssertion("Element [" + i + "] ID", "not null or empty", id);
                Assert.assertTrue("Element ID at index " + i + " is null or empty",
                        id != null && !id.trim().isEmpty());
            }
        }

        /**
         * Verify every item has episode type as specified value
         *
         * @param expectedType the expected type value
         */
        @Then("every item should have episode type as {string}")
        public void verifyAllEpisodeTypesEqual(String expectedType) {
            List<Map<String, Object>> elements = responseUtils.getResponse().jsonPath().getList("$");
            ReportManager.logStep("Checking episode type for " + elements.size() + " elements");

            for (int i = 0; i < elements.size(); i++) {
                String type = responseUtils.getJsonString("[" + i + "].episode.type");
                ReportManager.logAssertion("Element [" + i + "] episode type", expectedType, type);
                Assert.assertEquals("Episode type at index " + i + " is not " + expectedType,
                        expectedType, type);
            }
        }

        /**
         * Verify every item's episode title is not null or empty
         */
        @Then("every items episode title should not be null or empty")
        public void verifyAllEpisodeTitlesNotEmpty() {
            List<Map<String, Object>> elements = responseUtils.getResponse().jsonPath().getList("$");
            ReportManager.logStep("Checking episode titles for " + elements.size() + " elements");

            for (int i = 0; i < elements.size(); i++) {
                String title = responseUtils.getJsonString("[" + i + "].episode.title");
                ReportManager.logAssertion("Element [" + i + "] episode title", "not null or empty", title);
                Assert.assertTrue("Episode title at index " + i + " is null or empty",
                        title != null && !title.trim().isEmpty());
            }
        }

        /**
         * Verify only one episode has live field set to true
         */
        @Then("only one episode should have live field set to true")
        public void verifyOnlyOneEpisodeIsLive() {
            List<Map<String, Object>> elements = responseUtils.getResponse().jsonPath().getList("$");
            ReportManager.logStep("Checking live status for " + elements.size() + " elements");

            int liveCount = 0;
            for (int i = 0; i < elements.size(); i++) {
                if (responseUtils.getResponse().jsonPath().getBoolean("[" + i + "].episode.live")) {
                    liveCount++;
                    ReportManager.logStep("Found live episode at index " + i);
                }
            }

            ReportManager.logAssertion("Number of live episodes", 1, liveCount);
            Assert.assertEquals("Number of live episodes should be exactly 1", 1, liveCount);
        }

        /**
         * Verify transmission start date is before transmission end date for all items
         */
        @Then("transmission start date should be before transmission end date for all items")
        public void verifyTransmissionDates() throws ParseException {
            List<Map<String, Object>> elements = responseUtils.getResponse().jsonPath().getList("$");
            ReportManager.logStep("Checking transmission dates for " + elements.size() + " elements");

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

            for (int i = 0; i < elements.size(); i++) {
                String startDateStr = responseUtils.getJsonString("[" + i + "].transmission_start");
                String endDateStr = responseUtils.getJsonString("[" + i + "].transmission_end");

                // Parse dates
                Date startDate = dateFormat.parse(startDateStr);
                Date endDate = dateFormat.parse(endDateStr);

                ReportManager.logAssertion("Element [" + i + "] - Start date before end date",
                        "true", startDate.before(endDate));

                Assert.assertTrue("Transmission start date is not before end date at index " + i,
                        startDate.before(endDate));
            }
        }

        /**
         * Verify the response headers contain a specific header
         *
         * @param headerName the name of the header to check
         */
        @Then("the response headers should contain {string}")
        public void verifyResponseHeadersContain(String headerName) {
            String headerValue = responseUtils.getHeader(headerName);
            ReportManager.logAssertion("Response header " + headerName, "present", headerValue != null);
            Assert.assertNotNull("Response header " + headerName + " is missing", headerValue);
        }

        /**
         * Verify the error object has specific properties
         *
         * @param property1 first property name
         * @param property2 second property name
         */
        @Then("the error object should have properties {string} and {string}")
        public void verifyErrorObjectHasProperties(String property1, String property2) {
            boolean hasProperty1 = responseUtils.hasJsonPath("error." + property1);
            boolean hasProperty2 = responseUtils.hasJsonPath("error." + property2);

            ReportManager.logAssertion("Error object has property " + property1, true, hasProperty1);
            ReportManager.logAssertion("Error object has property " + property2, true, hasProperty2);

            Assert.assertTrue("Error object is missing property " + property1, hasProperty1);
            Assert.assertTrue("Error object is missing property " + property2, hasProperty2);
        }


    /**
     * Cleanup after each scenario
     */
    @After
    public void tearDown() {
        ReportManager.logStep("Completed scenario: " + scenario.getName() +
                " with status: " + scenario.getStatus());
    }

    /**
     * Set the API base URL
     *
     * @param baseUrl the base URL for API requests
     */
    @Given("the API base URL is set to {string}")
    public void setApiBaseUrl(String baseUrl) {
        restUtils = new RestAssuredUtils(baseUrl);
        ReportManager.logStep("API base URL set to: " + baseUrl);
    }

    /**
     * Send a GET request to an endpoint
     *
     * @param endpoint the API endpoint
     */
    @When("I send a GET request to endpoint {string}")
    public void sendGetRequest(String endpoint) {
        ReportManager.logRequest("GET", restUtils.getBaseUrl() + endpoint);
        response = restUtils.get(endpoint);
        responseUtils = new ResponseUtils(response);
        ReportManager.logResponse(response);
    }

    /**
     * Verify the response status code
     *
     * @param expectedStatusCode the expected status code
     */
    @Then("the response status code should be {int}")
    public void verifyResponseStatusCode(int expectedStatusCode) {
        int actualStatusCode = responseUtils.getStatusCode();
        ReportManager.logAssertion("Status Code", expectedStatusCode, actualStatusCode);
        Assert.assertEquals("Unexpected response status code", expectedStatusCode, actualStatusCode);
    }

    /**
     * Verify the response time is less than a specified threshold
     *
     * @param maxResponseTimeMs the maximum acceptable response time in milliseconds
     */
    @Then("the response time should be less than {int} milliseconds")
    public void verifyResponseTime(int maxResponseTimeMs) {
        long actualResponseTime = responseUtils.getResponseTime();
        ReportManager.logAssertion("Response Time", "< " + maxResponseTimeMs + " ms", actualResponseTime + " ms");
        Assert.assertTrue(
                "Response time " + actualResponseTime + " ms exceeds maximum " + maxResponseTimeMs + " ms",
                responseUtils.isResponseTimeLessThan(maxResponseTimeMs)
        );
    }

    /**
     * Verify the response contains a specific JSON path
     *
     * @param jsonPath the JSON path
     */
    @Then("the response should contain JSON path {string}")
    public void verifyResponseContainsJsonPath(String jsonPath) {
        boolean hasPath = responseUtils.hasJsonPath(jsonPath);
        ReportManager.logAssertion("JSON Path Exists: " + jsonPath, true, hasPath);
        Assert.assertTrue("JSON path " + jsonPath + " not found in response", hasPath);
    }

    /**
     * Verify the response JSON path has a specific string value
     *
     * @param jsonPath the JSON path
     * @param expectedValue the expected string value
     */
    @Then("the response JSON path {string} should be {string}")
    public void verifyResponseJsonPathEquals(String jsonPath, String expectedValue) {
        String actualValue = responseUtils.getJsonString(jsonPath);
        ReportManager.logAssertion("JSON Path " + jsonPath, expectedValue, actualValue);
        Assert.assertEquals("Unexpected value for JSON path " + jsonPath, expectedValue, actualValue);
    }

    /**
     * Verify the response JSON path has a specific integer value
     *
     * @param jsonPath the JSON path
     * @param expectedValue the expected integer value
     */
    @Then("the response JSON path {string} should be {int}")
    public void verifyResponseJsonPathEquals(String jsonPath, int expectedValue) {
        int actualValue = responseUtils.getJsonInt(jsonPath);
        ReportManager.logAssertion("JSON Path " + jsonPath, expectedValue, actualValue);
        Assert.assertEquals("Unexpected value for JSON path " + jsonPath, expectedValue, actualValue);
    }

    /**
     * Verify the response JSON path has a specific boolean value
     *
     * @param jsonPath the JSON path
     * @param expectedValue the expected boolean value
     */
    @Then("the response JSON path {string} should be {bool}")
    public void verifyResponseJsonPathEquals(String jsonPath, Boolean expectedValue) {
        boolean actualValue = responseUtils.getJsonBoolean(jsonPath);
        ReportManager.logAssertion("JSON Path " + jsonPath, expectedValue, actualValue);
        Assert.assertEquals("Unexpected value for JSON path " + jsonPath, expectedValue, actualValue);
    }

    /**
     * Verify the response JSON path contains a specific value
     *
     * @param jsonPath the JSON path
     * @param expectedValue the expected value to be contained
     */
    @Then("the response JSON path {string} should contain {string}")
    public void verifyResponseJsonPathContains(String jsonPath, String expectedValue) {
        String actualValue = responseUtils.getJsonString(jsonPath);
        ReportManager.logAssertion("JSON Path " + jsonPath + " contains", expectedValue, actualValue);
        Assert.assertTrue(
                "JSON path " + jsonPath + " value " + actualValue + " does not contain " + expectedValue,
                actualValue != null && actualValue.contains(expectedValue)
        );
    }
}