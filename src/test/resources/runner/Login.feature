Feature: Login to Lucky Bandit Club and verify the user balance

  Scenario: User logs in and verifies the balance
    Given the user navigates to the login page
    When the user logs in with valid credentials
    Then the user closes any modal pop-ups if present
    And the user checks the balance in the header
    Then the user verifies the balance matches the API balance
