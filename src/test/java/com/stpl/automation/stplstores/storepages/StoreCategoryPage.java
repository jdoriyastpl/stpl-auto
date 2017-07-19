package com.stpl.automation.stplstores.storepages;

import java.util.Map;

import org.openqa.selenium.WebDriver;

import com.stpl.automation.exceptions.AutomationException;
import com.stpl.automation.webdriver.WebDriverConfig;
import com.stpl.automation.webdriver.page.BasePage;

public class StoreCategoryPage extends BasePage{

	public StoreCategoryPage(WebDriverConfig webDriverConfiguration,
			String scheme, String url) throws AutomationException {
		super(webDriverConfiguration, scheme, url);
		// TODO Auto-generated constructor stub
	}
	
	
	public StoreCategoryPage(WebDriver webDriver,
			Map<String, String> pageElements) {
		super(webDriver, pageElements);
	}
	
	public StoreProductDetailPage clickOnItem(String ItemName) throws AutomationException{
		scrollIntoView(getWebElementById(ItemName));
		getWebElementById(ItemName).click();
		return new StoreProductDetailPage(webDriver, pageElements);
		
	}
	
	
}
