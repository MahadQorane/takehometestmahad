# takehometestmahad
This project contains automated tests to validate api data
# Project Structure
Test Framework: JUnit 5 / Cucumber

Language: Java 21+

Build Tool: Maven

HTTP Client: REST-assured

# Prerequisites
Test Framework: JUnit 5 / Cucumber

Java 17 or higher installed and configured

Maven 3.5+ installed

API (authorized and reachable)
# Run Command


You can run them all or use tags to run specific scenarios:

mvn clean test

Run only smoke tests

mvn clean test -Dcucumber.filter.tags="@smoke"

Run only regression tests

mvn clean test -Dcucumber.filter.tags="@regression"

Run negative tests

mvn clean test -Dcucumber.filter.tags="@negative"

# Assumptions
Transmission date fields (transmission_start, transmission_end) follow ISO 8601 format (yyyy-MM-dd'T'HH:mm:ss.SSSZ)

All campaigns being validated are already created and have a valid campaignId

# External Dependencies
REST-assured – for making and validating REST API calls
JUnit & Cucumber – for writing and organizing tests

# Known Limitations
The test suite uses API’s free tier, which has a 1,000-request monthly limit. Exceeding this quota will cause tests to fail. If tests fail, check for this error:

"Unpaid accounts are limited to 1000 Requests per month".