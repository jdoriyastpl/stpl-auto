Feature: Test checkout process on Parex USA Store 


Scenario: To test checkout process with a valid registred user on Parex USA Store 
	
	Given I have Store url and valid registered user deatils 
	When I click on login 
	Then I should  navigate to login Page 
	When I fillup the login details 
	Then I should navigate to homepage 
	When I click on Parex Category 
	Then I should navigate to category page 
	When I click on a product 
	Then I should navigate to product page 
	And  I should validate product attributes 
	When I  click on Add to cart button 
    Then I should navigate to shopping cart page 
    When I click to Checkout 
    Then I shoud navigate to your Information page
    When I filled the valid deatils and submit the Your Information page 
    Then I should navigate to Payment page and verify Shipping address
    And  I should validate available Payment method
    When I choose credit card as Payment method and click on continue button
    Then I should navigate CCPay Page and validate the url
    When I submitted the CCPay page with valid credit card details
    Then I should navigate to checkout review page
    
    
     
   
   