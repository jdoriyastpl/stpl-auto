package com.stpl.automation.stplstores.storepages;

import java.util.Map;

import org.openqa.selenium.WebDriver;

import com.stpl.automation.exceptions.AutomationException;
import com.stpl.automation.webdriver.WebDriverConfig;
import com.stpl.automation.webdriver.enumeration.ByLocators;
import com.stpl.automation.webdriver.page.BasePage;

public class StorePaymentPage extends BasePage{

	
	public StorePaymentPage(WebDriverConfig webDriverConfiguration, String scheme, String url)
			throws AutomationException {
		super(webDriverConfiguration, scheme, url);
		// TODO Auto-generated constructor stub
	}

	public StorePaymentPage(WebDriver webDriver, Map<String, String> pageElements) {
		super(webDriver, pageElements);
	}
	
	public StoreCcPayPage clickOnContinueButton() throws AutomationException{
		getWebElementById("continue_button_paymentpage_id").click();
		return new StoreCcPayPage(webDriver, pageElements);
	}
	
	public boolean isSecurityCCDialogBoxDisplayed(){
		return isElementPresentOnPage("security_credit_card_box", ByLocators.BY_ID);
	}
}
