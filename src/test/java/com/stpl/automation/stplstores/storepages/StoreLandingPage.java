package com.stpl.automation.stplstores.storepages;

import java.util.Map;

import org.openqa.selenium.WebDriver;

import com.stpl.automation.exceptions.AutomationException;
import com.stpl.automation.webdriver.WebDriverConfig;
import com.stpl.automation.webdriver.page.BasePage;

public class StoreLandingPage extends BasePage {

	public StoreLandingPage(WebDriverConfig webDriverConfiguration,
			String scheme, String url) throws AutomationException {
		super(webDriverConfiguration, scheme, url);
		// TODO Auto-generated constructor stub
	}

	public StoreLandingPage(WebDriver webDriver,
			Map<String, String> pageElements) {
		super(webDriver, pageElements);
	}
	
	
	public StoreCategoryPage clickOnCategoryName(String CategoryName) throws AutomationException{
		getWebElementByCss(CategoryName).click();
		return new StoreCategoryPage(webDriver, pageElements);
	}
	
	public void goToStore(String storeName) throws AutomationException{
		goTo(storeName);
	
	}
	
	public StoreLoginPage clickOnLoginCreateAccount() throws AutomationException{
		getWebElementByXpath("login_create_account_xpath").click();
		return new StoreLoginPage(webDriver, pageElements);
	}
}
