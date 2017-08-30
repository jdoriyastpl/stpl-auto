package com.stpl.automation.stplstores.storepages;

import java.util.Map;

import org.openqa.selenium.WebDriver;

import com.stpl.automation.exceptions.AutomationException;
import com.stpl.automation.webdriver.WebDriverConfig;
import com.stpl.automation.webdriver.page.BasePage;

public class StoreShoppingCartPage extends BasePage{
	
	public StoreShoppingCartPage(WebDriverConfig webDriverConfiguration, String scheme, String url)
			throws AutomationException {
		super(webDriverConfiguration, scheme, url);
		// TODO Auto-generated constructor stub
	}

	public StoreShoppingCartPage(WebDriver webDriver, Map<String, String> pageElements) {
		super(webDriver, pageElements);
	}
	
	public StoreYourInfoPage clickCheckOutButton() throws AutomationException{
		getWebElementById("checkout_button_id").click();
		return new StoreYourInfoPage(webDriver, pageElements);
	}

	public void removeItem() throws AutomationException{
		getWebElementByCss("remove_item_css").click();
	}
	
	public void continueShopping()throws AutomationException{
		getWebElementByXpath("continue_shopping").click();
	}
}
