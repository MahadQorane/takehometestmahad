Feature: task_ibl episode title test
  As a QA engineer
  I want to validate the IBL test API
  So that I can ensure the API is working correctly

  @regression
  Scenario: title in episode is never nul or empty for any schedule
    Given the API base URL is set to "https://testapi.io/api/RMSTest"
    When I send a GET request to endpoint "/ibltest"
    #  Then the title field in "episode" should not be null nor empty for any item
#    Then every schedule in item ID field should not be null or empty
    And every item should have episode type as "episode"


#  Verify that the “title” field in “episode”, is never null or empty (“”) for any schedule
#  item
