package com.stpl.automation.stplstores.storepages;

import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

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
		clickOnLogIn();
		return new StoreLandingPage(webDriver, pageElements);
	}

	public void clickOnLogIn() throws AutomationException{
		for (WebElement element : getWebElementsByXpath("Login_xpath")) {
			if( element.isDisplayed()){
				element.click();
			}
		}
	}
}
