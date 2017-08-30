package com.stpl.automation.stplstores.storepages;

import java.util.Map;

import org.openqa.selenium.WebDriver;

import com.stpl.automation.exceptions.AutomationException;
import com.stpl.automation.webdriver.WebDriverConfig;
import com.stpl.automation.webdriver.page.BasePage;

public class StoreCcPayPage extends BasePage{

	public StoreCcPayPage(WebDriverConfig webDriverConfiguration,
			String scheme, String url) throws AutomationException {
		super(webDriverConfiguration, scheme, url);
		// TODO Auto-generated constructor stub
	}
	
	
	public StoreCcPayPage(WebDriver webDriver,
			Map<String, String> pageElements) {
		super(webDriver, pageElements);
	}
	
	
	/**
	 * @param creditCardNo
	 * @param name
	 * @param cvv
	 * @param expiryMonth
	 * @param expiryyear
	 * @return
	 * @throws AutomationException
	 */
	public StoreReviewOrderPage enterCreditDetails(String creditCardNo , String name, String cvv,String expiryMonth,String expiryyear) throws AutomationException{
		
		getWebElementById("cardno_id").sendKeys(creditCardNo);
		getWebElementById("nameoncard_id").sendKeys(name);
		getWebElementById("cvv_id").sendKeys(cvv);
		selectDropDownById("Month_id", expiryMonth);
		selectDropDownById("year_id", expiryyear);
		getWebElementByXpath("continue_xpath").click();
		return new StoreReviewOrderPage(webDriver, pageElements);
	}
}
