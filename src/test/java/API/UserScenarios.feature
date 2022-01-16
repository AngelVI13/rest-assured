@functional
Feature: User Scenarios

  Scenario: I should be able to login to the system with correct credentials
    Given the login endpoint exists
    When I send a valid login request
    Then response status code should be 200
    And log in response should be valid
