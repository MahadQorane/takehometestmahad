Feature: task_ibl live episode test
  As a QA engineer
  I want to validate the IBL test API
  So that I can ensure the API is working correctly

  @regression @wip
  Scenario: One episode in the list has live field as true
    Given the API base URL is set to "https://testapi.io/api/RMSTest"
    When I send a GET request to endpoint "/ibltest"
    Then only one episode should have live field set to "true"
