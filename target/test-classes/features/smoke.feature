Feature: Test Store
  I want to test store

  Scenario: Test Store Landing Page
    Given I have Store url
    When I click on CategoryName
    Then I navigate to Category Page
    And I add an item into Cart
