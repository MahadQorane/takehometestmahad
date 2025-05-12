Feature: task_ibl transmission start date end date test
  As a QA engineer
  I want to validate the IBL test API
  So that I can ensure the API is working correctly

  @regression @wip
  Scenario: Transmission start date is before the transmission end date
    Given the API base URL is set to "https://testapi.io/api/RMSTest"
    When I send a GET request to endpoint "/ibltest"
    Then transmission start date should be before transmission end date for all items
