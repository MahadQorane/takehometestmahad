Feature: Functional Manual Testing
  As a QA engineer
  I want to validate the IBL test API
  So that I can ensure the API is working correctly

Scenario: Verify a specific element's ID exists
Given I make a request to retrieve the schedule
When the API call is successful
Then the response "schedule.elements[*].id" should contain "p0g9q573"


Scenario: Verify the service ID of the first element
Given I make a request to retrieve the schedule
When the API call is successful
Then the first element in "schedule.elements" should have "service_id" as "bbc_one_london"


Scenario: Verify the format of scheduled start and end times
Given I make a request to retrieve the schedule
When the API call is successful
Then every element in "schedule.elements" should have a "scheduled_start" matching the pattern "^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d{3}Z$"
