Feature: task_ibl success response test
  As a QA engineer
  I want to validate the IBL test API
  So that I can ensure the API is working correctly

  @smoke @regression @wip
  Scenario: Successful response with acceptable performance
    Given the API base URL is set to "https://testapi.io/api/RMSTest"
    When I send a GET request to endpoint "/ibltest"
    Then the response status code should be 200
    And the response time should be less than 1050 milliseconds