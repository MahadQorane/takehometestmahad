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

#  @regression
#  Scenario: ID fields are not null or empty and episode type is always "episode"
#    Given the API base URL is set to "https://testapi.io/api/RMSTest"
#    When I send a GET request to endpoint "/ibltest"
#    Then every elements ID field should not be null or empty
#    And every item should have episode type as "episode"
#
#  @regression
#  Scenario: Title field in episode is never null or empty for any schedule item
#    Given the API base URL is set to "https://testapi.io/api/RMSTest"
#    When I send a GET request to endpoint "/ibltest"
#    Then every items episode title should not be null or empty
#
#  @regression
#  Scenario: One episode in the list has live field as true
#    Given the API base URL is set to "https://testapi.io/api/RMSTest"
#    When I send a GET request to endpoint "/ibltest"
#    Then only one episode should have live field set to true
#
#  @regression
#  Scenario: Transmission start date is before the transmission end date
#    Given the API base URL is set to "https://testapi.io/api/RMSTest"
#    When I send a GET request to endpoint "/ibltest"
#    Then transmission start date should be before transmission end date for all items
#
#  @regression
#  Scenario: Response headers contain Date
#    Given the API base URL is set to "https://testapi.io/api/RMSTest"
#    When I send a GET request to endpoint "/ibltest"
#    Then the response headers should contain "Date"
#
#  @regression @negative
#  Scenario: Error response for invalid endpoint
#    Given the API base URL is set to "https://testapi.io/api/RMSTest"
#    When I send a GET request to endpoint "/ibltest/2023-09-11"
#    Then the response status code should be 404
#    And the error object should have properties "details" and "http_response_code"

  # Additional scenarios can be added here in the future
  # For example:
  # Scenario: Verify API response contains expected data
  # Scenario: Verify POST request creates a new resource
  # Scenario: Verify DELETE request removes a resource