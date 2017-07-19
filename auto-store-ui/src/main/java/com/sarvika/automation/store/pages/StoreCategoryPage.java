package com.sarvika.automation.store.pages;

import java.util.Map;

import org.openqa.selenium.WebDriver;

import com.sarvika.automation.exceptions.AutomationException;
import com.sarvika.automation.webdriver.WebDriverConfig;
import com.sarvika.automation.webdriver.page.BasePage;

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
	
	
	
	
}
