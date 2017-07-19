package com.stpl.automation.stplstores.storepages;

import java.util.Map;

import org.openqa.selenium.WebDriver;

import com.stpl.automation.exceptions.AutomationException;
import com.stpl.automation.webdriver.WebDriverConfig;
import com.stpl.automation.webdriver.page.BasePage;

public class StoreLoginPage  extends BasePage{

	public StoreLoginPage(WebDriver webDriver,
			Map<String, String> pageElements) {
		super(webDriver, pageElements);
	}

	public StoreLoginPage(WebDriverConfig webDriverConfiguration, String scheme, String url)
			throws AutomationException {
		super(webDriverConfiguration, scheme, url);
		// TODO Auto-generated constructor stub
	}
	
	public StoreLandingPage loginToStore(String emailAddress , String password) throws AutomationException{
		getWebElementByXpath("emailAddress_xpath").sendKeys(emailAddress);
		getWebElementById("password").sendKeys(password);
		getWebElementByXpath("Login_xpath").click();
		return new StoreLandingPage(webDriver, pageElements);
	}

}
