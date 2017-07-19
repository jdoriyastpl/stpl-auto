package com.sarvika.automation.store.pages;

import java.util.Map;

import org.openqa.selenium.WebDriver;

import com.sarvika.automation.exceptions.AutomationException;
import com.sarvika.automation.webdriver.WebDriverConfig;
import com.sarvika.automation.webdriver.page.BasePage;

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
		getWebElementByLinkText(CategoryName).click();
		return new StoreCategoryPage(webDriver, pageElements);
	}
	
	public void goToStore(String storeName) throws AutomationException{
		goTo(storeName);
	}
}
