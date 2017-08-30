package com.stpl.automation.stplstores.storepages;

import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

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
		getWebElementByXpath(CategoryName).click();
		return new StoreCategoryPage(webDriver, pageElements);
	}
	
	public void goToStore(String storeName) throws AutomationException{
		goTo(storeName);
	
	}
	
	public StoreLoginPage clickOnLoginCreateAccount() throws AutomationException{
		for (WebElement element : getWebElementsByXpath("login_create_account_xpath")) {
			if (element.isDisplayed()){
				element.click();
			}
		}
		return new StoreLoginPage(webDriver, pageElements);
	}
	
	public StoreShoppingCartPage gotoCartPage(){
		return new StoreShoppingCartPage(webDriver, pageElements);
	}
}
