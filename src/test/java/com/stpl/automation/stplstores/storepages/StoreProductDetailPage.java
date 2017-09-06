package com.stpl.automation.stplstores.storepages;

import java.util.Map;

import org.openqa.selenium.WebDriver;

import com.stpl.automation.exceptions.AutomationException;
import com.stpl.automation.webdriver.WebDriverConfig;
import com.stpl.automation.webdriver.enumeration.ByLocators;
import com.stpl.automation.webdriver.page.BasePage;

public class StoreProductDetailPage extends BasePage {

	public StoreProductDetailPage(WebDriverConfig webDriverConfiguration, String scheme, String url)
			throws AutomationException {
		super(webDriverConfiguration, scheme, url);
		// TODO Auto-generated constructor stub
	}

	public StoreProductDetailPage(WebDriver webDriver, Map<String, String> pageElements) {
		super(webDriver, pageElements);
	}

	public Boolean verifyColorOrSize() {
		return isElementPresentOnPage("choose_size_xpath", ByLocators.BY_XPATH)
				|| isElementPresentOnPage("color_css", ByLocators.BY_CSS);
	}

	public StoreShoppingCartPage clickOnAddToCart() throws AutomationException {
		try{
		getWebElementByXpath("add_to_cart_button_xpath").click();
		}
		catch(Exception e){
			getWebElementByXpath("Add_to_Cart_xpath").click();
		}
		return new StoreShoppingCartPage(webDriver, pageElements);
	}

	public void selectSizeOptionAs(String size) throws AutomationException {
		selectDropDownById("choose_size_id", size);
	}

	public Boolean isQuantityEmpty() throws AutomationException {
		if (getWebElementByCss("quantity_input_css").getText().isEmpty()) {
			return true;
		} else {
			return false;
		}
	}
}
