package com.stpl.automation.stplstores.storepages;

import java.util.Map;

import org.openqa.selenium.WebDriver;

import com.stpl.automation.exceptions.AutomationException;
import com.stpl.automation.webdriver.WebDriverConfig;
import com.stpl.automation.webdriver.enumeration.ByLocators;
import com.stpl.automation.webdriver.page.BasePage;

public class StoreReviewOrderPage extends BasePage{

	public StoreReviewOrderPage(WebDriverConfig webDriverConfiguration,
			String scheme, String url) throws AutomationException {
		super(webDriverConfiguration, scheme, url);
		// TODO Auto-generated constructor stub
	}
	
	
	public StoreReviewOrderPage(WebDriver webDriver,
			Map<String, String> pageElements) {
		super(webDriver, pageElements);
	}

	public boolean isPlaceOrderDisplayer(){
		return isElementPresentOnPage("placeOrder", ByLocators.BY_ID);
	}
}
