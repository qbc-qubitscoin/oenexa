Feature: User Profile Management

  Scenario: User fetches and updates their profile
    Given a registered user profile exists with first name "Sam" and last name "Taylor"
    When the user retrieves their profile
    Then the profile first name should be "Sam" and last name should be "Taylor"
