package com.stpl.automation.stplstores.storepages;

import java.util.Map;

import org.openqa.selenium.WebDriver;

import com.stpl.automation.exceptions.AutomationException;
import com.stpl.automation.webdriver.WebDriverConfig;
import com.stpl.automation.webdriver.page.BasePage;

public class StoreYourInfoPage extends BasePage{

	
	public StoreYourInfoPage(WebDriverConfig webDriverConfiguration, String scheme, String url)
			throws AutomationException {
		super(webDriverConfiguration, scheme, url);
		// TODO Auto-generated constructor stub
	}

	public StoreYourInfoPage(WebDriver webDriver, Map<String, String> pageElements) {
		super(webDriver, pageElements);
	}
	
	
	public void enterUSbillingInfo() throws AutomationException{
		enterAddress1();
		 enterCity();
		selectDropDownById("country_id", "USA");
		selectDropDownById("region_id","GEORGIA");
		enterZipCode();
	}

	public StorePaymentPage clickContinueButton() throws AutomationException{
		getWebElementByXpath("continue_button_xpath").click();
		return new StorePaymentPage(webDriver, pageElements);
	}

	public void enterAddress1() throws AutomationException{
		getWebElementById("Address1_id").clear();
		getWebElementById("Address1_id").sendKeys("Test Address 1");
	}
	
	public void enterCity() throws AutomationException{
		getWebElementById("city_id").clear();
		getWebElementById("city_id").sendKeys("Test City");
	}
	
	public void enterZipCode() throws AutomationException{
		getWebElementByXpath("zipcode_xpath").clear();
		getWebElementByXpath("zipcode_xpath").sendKeys("74152");
	}
}
