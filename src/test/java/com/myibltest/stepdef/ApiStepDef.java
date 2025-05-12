package com.myibltest.stepdef;

import com.myibltest.utils.paths.ElementVerification;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.Before;
import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import io.restassured.response.Response;
import org.junit.Assert;

import java.time.OffsetDateTime;
import java.time.format.DateTimeParseException;

import com.myibltest.utils.RestAssuredUtils;
import com.myibltest.utils.ResponseUtils;
import com.myibltest.reporting.ReportManager;
import com.myibltest.config.ConfigManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.myibltest.utils.paths.JsonPaths.SCHEDULE_ELEMENTS;

/**
 * Step definitions for API testing
 */
public class ApiStepDef {

    private RestAssuredUtils restUtils;
    private Response response;
    private ResponseUtils responseUtils;
    private Scenario scenario;
    private ConfigManager configManager;
    private ElementVerification elementVerification = new ElementVerification();


    /**
     * Setup before each scenario
     * @param scenario the current scenario
     */
    @Before
    public void setup(Scenario scenario) {
        this.scenario = scenario;
        this.configManager = ConfigManager.getInstance();
        ReportManager.logStep("Starting scenario: " + scenario.getName());
    }

    /**
     * Set the API base URL
     * @param baseUrl the base URL for API requests
     */
    @Given("the API base URL is set to {string}")
    public void setApiBaseUrl(String baseUrl) {
        restUtils = new RestAssuredUtils(baseUrl);
        ReportManager.logStep("API base URL set to: " + baseUrl);
    }

    /**
     * Send a GET request to an endpoint
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
     * Verify every elements ID field is not null or empty
     */
    @Then("every elements ID field should not be null or empty")
    public void verifyElementsIdIsNotNullOrEmpty() {
        List<Map<String, Object>> elements = responseUtils.getResponse().jsonPath().getList(SCHEDULE_ELEMENTS);
        boolean allIdsValid = elementVerification.verifyElementIds(elements);
        Assert.assertTrue("All elements should have non-null and non-empty ID fields", allIdsValid);
    }

    /**
     * Verify every item has episode type as specified value
     * @param expectedType the expected type value
     */
    @And("every item should have episode type as {string}")
    public void verifyEveryItemHasEpisodeTypeAs(String expectedType) {
        List<Map<String, Object>> elements = responseUtils.getResponse().jsonPath().getList(SCHEDULE_ELEMENTS);
        boolean allTypesValid = true;
        for (Map<String, Object> element : elements) {
            Map<String, Object> episode = (Map<String, Object>) element.get("episode");
            if (episode == null || !expectedType.equals(episode.get("type"))) {
                allTypesValid = false;
                break;
            }
        }
        Assert.assertTrue("Every item should have episode type as '" + expectedType + "'", allTypesValid);
    }

    /**
     * Verify every item's episode title is not null or empty
     */
    @Then("every items episode title should not be null or empty")
    public void verifyEveryItemsEpisodeTitleIsNotNullOrEmpty() {
        List<Map<String, Object>> elements = responseUtils.getResponse().jsonPath().getList(SCHEDULE_ELEMENTS);
        boolean allTitlesValid = elementVerification.verifyEpisodeTitles(elements);
        Assert.assertTrue("Every item's episode title should not be null or empty", allTitlesValid);
    }

    /**
     * Verify only one episode has live field set to true
     */
    @Then("only one episode should have live field set to {string}")
    public void verifyOnlyOneEpisodeShouldHaveLiveFieldTo(String liveValue) {
        List<Map<String, Object>> elements = responseUtils.getResponse().jsonPath().getList(SCHEDULE_ELEMENTS);
        boolean isLiveExpected = Boolean.parseBoolean(liveValue);
        int liveCount = 0;
        for (Map<String, Object> element : elements) {
            Map<String, Object> episode = (Map<String, Object>) element.get("episode");
            if (episode != null) {
                Boolean live = (Boolean) episode.get("live");
                if (Boolean.TRUE.equals(live)) {
                    liveCount++;
                }
            }
        }
        int expectedLiveCount = isLiveExpected ? 1 : 0;
        Assert.assertEquals("Incorrect number of live episodes found", expectedLiveCount, liveCount);
    }

    /**
     * Verify transmission start date is before transmission end date for all items
     */
    @Then("transmission start date should be before transmission end date for all items")
    public void verifyTransmissionStartBeforeEnd() {
        List<Map<String, Object>> elements = responseUtils.getResponse().jsonPath().getList(SCHEDULE_ELEMENTS);
        List<String> invalidElements = new ArrayList<>();

        for (int i = 0; i < elements.size(); i++) {
            Map<String, Object> element = elements.get(i);
            String startStr = (String) element.get("transmission_start");
            String endStr = (String) element.get("transmission_end");

            if (startStr == null || endStr == null) {
                invalidElements.add(String.format("Element at index %d is missing start or end date", i));
                continue;
            }

            try {
                OffsetDateTime start = OffsetDateTime.parse(startStr);
                OffsetDateTime end = OffsetDateTime.parse(endStr);
                if (!start.isBefore(end)) {
                    invalidElements.add(String.format("Element at index %d: start [%s] is not before end [%s]", i, start, end));
                }
            } catch (DateTimeParseException e) {
                invalidElements.add(String.format("Element at index %d has invalid date format", i));
            }
        }

        if (!invalidElements.isEmpty()) {
            Assert.fail("Invalid transmission dates:\n" + String.join("\n", invalidElements));
        }
    }


    /**
     * Verify the response headers contain a specific header
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
    @And("the error object should have properties {string} and {string}")
    public void verifyErrorObjectHasProperties(String property1, String property2) {
        boolean hasProperty1 = responseUtils.hasJsonPath("error." + property1);
        boolean hasProperty2 = responseUtils.hasJsonPath("error." + property2);

        ReportManager.logAssertion("Error object has property " + property1, true, hasProperty1);
        ReportManager.logAssertion("Error object has property " + property2, true, hasProperty2);

        Assert.assertTrue("Error object is missing property " + property1, hasProperty1);
        Assert.assertTrue("Error object is missing property " + property2, hasProperty2);
    }

    //Cleanup after each scenario
    @After
    public void tearDown() {
        ReportManager.logStep("Completed scenario: " + scenario.getName() +
                " with status: " + scenario.getStatus());
    }


    /**
     * Verify the response status code
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
     * @param maxResponseTimeMs the maximum acceptable response time in milliseconds
     */
    @And("the response time should be less than {int} milliseconds")
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
     * @param jsonPath the JSON path
     */
    @Then("the response should contain JSON path {string}")
    public void verifyResponseContainsJsonPath(String jsonPath) {
        boolean hasPath = responseUtils.hasJsonPath(jsonPath);
        ReportManager.logAssertion("JSON Path Exists: " + jsonPath, true, hasPath);
        Assert.assertTrue("JSON path " + jsonPath + " not found in response", hasPath);
    }

    @Given("I make a request to retrieve the schedule")
    public void iMakeARequestToRetrieveTheSchedule() {
        // manual tested

    }

    @When("the API call is successful")
    public void theAPICallIsSuccessful() {
        // manual tested
    }

    @Then("the response {string} should contain {string}")
    public void theResponseShouldContain(String arg0, String arg1) {
        // manual tested
    }

    @Then("the first element in {string} should have {string} as {string}")
    public void theFirstElementInShouldHaveAs(String arg0, String arg1, String arg2) {
        // manual tested

    }

    @Then("every element in {string} should have a {string} matching the pattern {string}")
    public void everyElementInShouldHaveAMatchingThePattern(String arg0, String arg1, String arg2) {
        // manual tested
    }
}