Feature: task_ibl episode type test
  As a QA engineer
  I want to validate the IBL test API
  So that I can ensure the API is working correctly

  @regression
  Scenario: ID are not null or empty and episode type is always episode
    Given the API base URL is set to "https://testapi.io/api/RMSTest"
    When I send a GET request to endpoint "/ibltest"
    Then every elements ID field should not be null or empty
    And every item should have episode type as "episode"
