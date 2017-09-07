package com.stpl.automation.step_definitions_test;

import java.util.Hashtable;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.stpl.automation.constant.Constant;
import com.stpl.automation.exceptions.AutomationException;
import com.stpl.automation.stores.StoreTestBase;
import com.stpl.automation.stplstores.enumerations.Categories;
import com.stpl.automation.stplstores.storepages.StoreCategoryPage;
import com.stpl.automation.stplstores.storepages.StoreCcPayPage;
import com.stpl.automation.stplstores.storepages.StoreLandingPage;
import com.stpl.automation.stplstores.storepages.StoreLoginPage;
import com.stpl.automation.stplstores.storepages.StorePaymentPage;
import com.stpl.automation.stplstores.storepages.StoreProductDetailPage;
import com.stpl.automation.stplstores.storepages.StoreReviewOrderPage;
import com.stpl.automation.stplstores.storepages.StoreShoppingCartPage;
import com.stpl.automation.stplstores.storepages.StoreYourInfoPage;
import com.stpl.automation.webdriver.enumeration.ByLocators;
import com.thoughtworks.selenium.webdriven.commands.IsElementPresent;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class SwaTheStoreCucumberSteps extends StoreTestBase {
	private static final Logger LOG = Logger.getLogger(SwaTheStoreCucumberSteps.class.getSimpleName());

	Hashtable<String, String> storeUrl;
	StoreLandingPage storeLandingPageVid;
	StoreCategoryPage storeCategoryPage;
	StoreProductDetailPage storeProductDetailPage;
	StoreShoppingCartPage storeShoppingCartPage;
	StoreYourInfoPage yourInfoPage;
	StorePaymentPage storePaymentPage;
	StoreLoginPage storeLoginPage;
	StoreCcPayPage storeCcPayPage;
	StoreReviewOrderPage storeReviewOrderPage;
	
	static final String SWA_THE_STORE_VID = "20170504134";
	String itemTitle = null;
	String currentUrl;



	@Before
	public void setup() throws AutomationException {

		initializeSystemProperties();
		storeUrl = getStoreUri();
	}

	@After
	public void scenarioTearDown(Scenario scenario) {
		storeLandingPageVid.embedScreenshotOnFailure(scenario);
		storeLandingPageVid.quitWebDriver();
	}
	
	
	
	@Given("^I have Store url and valid registered user details$")
	public void i_have_Store_url_and_valid_registered_user_details() throws Throwable {
		storeLandingPageVid = (StoreLandingPage) applicationContext.getBean("storeLandingPageVid");
		currentUrl = storeLandingPageVid.getCurrentURL();
		LOG.info(currentUrl);
		storeLandingPageVid.goToStore(currentUrl + SWA_THE_STORE_VID);
		LOG.info("User is landed on Parex Store Successfully");

	}

	@When("^I click on Login$")
	public void i_click_on_login() throws Throwable {
		storeLoginPage = storeLandingPageVid.clickOnLoginCreateAccount();
		LOG.info("User has clicked on login");
	}

	@Then("^I should navigate to login Page$")
	public void i_should_navigate_to_login_Page() throws Throwable {
		Assert.assertNotNull(storeLoginPage);
		LOG.info("User is landed on login page");
	}

	@When("^I filled the login details$")
	public void i_filled_the_login_details() throws Throwable {
		storeLandingPageVid = storeLoginPage.loginToStore(Constant.STORE_TEST_EMAILADDRESS,
				Constant.STORE_TEST_PASSWORD);
		LOG.info("User is logged in to Parex Store Successfully");
	}

	@Then("^I should navigate to Homepage$")
	public void i_should_navigate_to_Homepage() throws Throwable {
		Assert.assertNotNull(storeLandingPageVid);
	}

	@When("^I click on Apparel Category$")
	public void i_click_on_Apparel_Category() throws Throwable {
		if(storeLandingPageVid.getWindowLocationHref().contains("basket.html")){
			storeShoppingCartPage=	storeLandingPageVid.gotoCartPage();
			storeShoppingCartPage.removeItem();
			storeShoppingCartPage.continueShopping();			
		}
		storeCategoryPage = storeLandingPageVid.clickOnCategoryName(Categories.APPAREL.getCategoryName());
		LOG.info("User has choosen one category ");
	}

	@Then("^I should navigate to Category Page$")
	public void i_should_navigate_to_Category_page() throws Throwable {
		storeCategoryPage.waitForPageToLoad("shop/apparel");
		LOG.info("User is landed on Category page Successfully");
	}

	@When("^I click on a Product$")
	public void i_click_on_a_Product() throws Throwable {
		itemTitle = storeCategoryPage.getItemTitle("Warrior_Spirit_T-Shirt_id");
		storeProductDetailPage = storeCategoryPage.clickOnItem("Warrior_Spirit_T-Shirt_id");
		LOG.info("User is clicked on one available item");
	}

	@Then("^I should navigate to Product page$")
	public void i_should_navigate_to_Product_page() throws Throwable {
		Assert.assertTrue(storeProductDetailPage.isStaticTextDisplayed(itemTitle));
		LOG.info("User has verify item on product page");
	}

	@Then("^I should validate Product attributes$")
	public void i_should_validate_Product_attributes() throws Throwable {
		if(storeProductDetailPage.isElementPresentOnPage("item-attr-section", ByLocators.BY_CSS)){
			storeProductDetailPage.selectSizeOptionAs("Medium");
		}
	}

	@When("^I click on Add to cart button$")
	public void i_click_on_Add_to_cart_button() throws Throwable {
		storeShoppingCartPage = storeProductDetailPage.clickOnAddToCart();
	}

	@Then("^I should navigate to Shopping cart page$")
	public void i_should_navigate_to_shopping_cart_page() throws Throwable {
		Assert.assertTrue(storeShoppingCartPage.isStaticTextDisplayed("Shopping Cart"));
	}

	@When("^I click to Checkout button$")
	public void i_click_to_Checkout_button() throws Throwable {
		yourInfoPage = storeShoppingCartPage.clickCheckOutButton();
	}

	@Then("^I should navigate to your Information page$")
	public void i_should_navigate_to_your_Information_page() throws Throwable {
		Assert.assertTrue(yourInfoPage.getPageTitle().contains(yourInfoPage.YOUR_INFO_TITLE));
	}

	@When("^I filled the valid details and submit the Your Information page$")
	public void i_filled_the_valid_details_and_submit_the_Your_Information_page() throws Throwable {
		yourInfoPage.enterUSbillingInfo();
		storePaymentPage = yourInfoPage.clickContinueButton();
	}

	@Then("^I should navigate to Payment page and Verify Shipping address$")
	public void i_should_navigate_to_Payment_page_and_verify_Shipping_address() throws Throwable {
		storePaymentPage.waitForPageToLoad("payment.html?");
	}

	@Then("^I should validate Available Payment method$")
	public void i_should_validate_Available_Payment_method() throws Throwable {
		Assert.assertTrue(storePaymentPage.isStaticTextDisplayed("Pay by Credit Card"));
	}

	@When("^I choose credit card as Payment Method and Click on continue button$")
	public void i_choose_credit_card_as_Payment_Method_and_Click_on_continue_button() throws Throwable {
		storeCcPayPage = storePaymentPage.clickOnContinueButton();
		Assert.assertTrue(storePaymentPage.isSecurityCCDialogBoxDisplayed());
	}

	@Then("^I should navigate CCPay Page and Validate the url$")
	public void i_should_navigate_CCPay_Page_and_Validate_the_url() throws Throwable {
		if (currentUrl.contains("clone") || currentUrl.contains("")) {
			storeCcPayPage.waitForPageToLoad(Constant.TEST_ENV_CCPAY_URL);
		} else {
			storeCcPayPage.waitForPageToLoad(Constant.PROD_ENV_CCPAY_URL);
		}
	}

	@When("^I submitted the CCPay page with Valid credit card details$")
	public void i_submitted_the_CCPay_page_with_Valid_credit_card_details() throws Throwable {
		LOG.info("User has entered the credit card details and submit cc pay page");
		storeReviewOrderPage = storeCcPayPage.enterCreditDetails(Constant.CREDIT_CARD_VISA_TYPE, Constant.NAME_ON_CREDIT_CARD, Constant.CVV_NO, Constant.EXPIRY_MONTH, Constant.EXPIRY_YEAR);
	}

	@Then("^I should navigate to Checkout review page$")
	public void i_should_navigate_to_checkout_review_page() throws Throwable {
		storeReviewOrderPage.waitForPageToLoad("revieworder.html?");
		Assert.assertTrue(storeReviewOrderPage.isPlaceOrderDisplayer());
	}
	
}
