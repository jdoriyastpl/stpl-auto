Feature: Test checkout process on SWA Store for US user

  Scenario: To test checkout process with a valid registered US user on SWA Store
    Given I have Store url and valid registered user details
    When I click on Login
    Then I should navigate to login Page
    When I filled the login details
    Then I should navigate to Homepage
    When I click on Apparel Category
    Then I should navigate to Category Page
    When I click on a Product
    Then I should navigate to Product page
    And I should validate Product attributes
    When I click on Add to cart button
    Then I should navigate to Shopping cart page
    When I click to Checkout button
    Then I should navigate to your Information page
    When I filled the valid details and submit the Your Information page
    Then I should navigate to Payment page and Verify Shipping address
    And I should validate Available Payment method
    When I choose credit card as Payment Method and Click on continue button
    Then I should navigate CCPay Page and Validate the url
    When I submitted the CCPay page with Valid credit card details
    Then I should navigate to Checkout review page
