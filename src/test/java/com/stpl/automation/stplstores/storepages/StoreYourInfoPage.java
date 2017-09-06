package com.stpl.automation.stplstores.storepages;

import java.util.Map;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;

import com.stpl.automation.constant.Constant;
import com.stpl.automation.exceptions.AutomationException;
import com.stpl.automation.webdriver.WebDriverConfig;
import com.stpl.automation.webdriver.enumeration.ByLocators;
import com.stpl.automation.webdriver.page.BasePage;

public class StoreYourInfoPage extends BasePage {
	public final String YOUR_INFO_TITLE ="Checkout Your Information";

	public StoreYourInfoPage(WebDriverConfig webDriverConfiguration, String scheme, String url)
			throws AutomationException {
		super(webDriverConfiguration, scheme, url);
		waitForPageToLoad("yourinfo.html");
		getWebElementByClass("my-cart");
		Assert.assertTrue(getPageTitle().contains(YOUR_INFO_TITLE));
	}

	public StoreYourInfoPage(WebDriver webDriver, Map<String, String> pageElements) throws AutomationException {
		super(webDriver, pageElements);
		waitForPageToLoad("yourinfo.html");
		Assert.assertTrue(getPageTitle().contains(YOUR_INFO_TITLE));
	}

	public void enterUSbillingInfo() throws AutomationException {
		enterAddress1();
		enterCity();
		enterPhoneNumber();
		selectDropDownById("country_id", Constant.USA_COUNTRY_NAME);
		selectDropDownById("region_id", Constant.USA_STATE_NAME);
		enterZipCode(Constant.USA_GEORGIA_ZIP_CODE_NAME);
	}

	public StorePaymentPage clickContinueButton() throws AutomationException {
		getWebElementByXpath("continue_button_xpath").click();
		return new StorePaymentPage(webDriver, pageElements);
	}

	public void enterAddress1() throws AutomationException {
		getWebElementById("Address1_id").clear();
		getWebElementById("Address1_id").sendKeys("Test Address 1");
	}

	public void enterCity() throws AutomationException {
		getWebElementById("city_id").clear();
		getWebElementById("city_id").sendKeys("Test City");
	}

	public void enterZipCode(String zipCode) throws AutomationException {
		getWebElementByXpath("zipcode_xpath").clear();
		getWebElementByXpath("zipcode_xpath").sendKeys(zipCode);
	}
	
	public void enterPhoneNumber() throws AutomationException{
		if(isElementPresentOnPage("phone_id",ByLocators.BY_NAME)){
			getWebElementByName("phone_id").clear();
			getWebElementByName("phone_id").sendKeys(Constant.USA_PHONE_NO);
		}
	}
}
