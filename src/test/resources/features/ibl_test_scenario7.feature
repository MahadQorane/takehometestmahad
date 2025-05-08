Feature: task_ibl API error response test
  As a QA engineer
  I want to validate the IBL test API
  So that I can ensure the API is working correctly

  @regression @negative
  Scenario: Error response for invalid endpoint
    Given the API base URL is set to "https://testapi.io/api/RMSTest"
    When I send a GET request to endpoint "/ibltest/2023-09-11"
    Then the response status code should be 404
    And the error object should have properties "details" and "http_response_code"

  # Additional scenarios can be added here in the future
  # For example:
  # Scenario: Verify API response contains expected data
  # Scenario: Verify POST request creates a new resource
  # Scenario: Verify DELETE request removes a resource