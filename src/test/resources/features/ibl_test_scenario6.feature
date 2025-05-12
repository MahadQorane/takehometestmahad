Feature: task_ibl header date test
  As a QA engineer
  I want to validate the IBL test API
  So that I can ensure the API is working correctly

  @regression @wip
  Scenario: Response headers contain Date
    Given the API base URL is set to "https://testapi.io/api/RMSTest"
    When I send a GET request to endpoint "/ibltest"
    Then the response headers should contain "Date"