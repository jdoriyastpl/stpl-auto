package com.stpl.automation.step_definitions_test;

import java.util.Hashtable;

import org.apache.log4j.Logger;
import org.junit.AfterClass;
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

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class ParexUsaCucumberSteps extends StoreTestBase {
	
	private static final Logger LOG = Logger.getLogger(ParexUsaCucumberSteps.class.getSimpleName());

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
	
	static final String PAREX_USA_VID = "20170113734";
	String itemTitle = null;
	String currentUrl;

	

	@Before
	public void setup() throws AutomationException {

		initializeSystemProperties();
		storeUrl = getStoreUri();
	}

	@AfterClass
	public void scenarioTearDown(Scenario scenario) {
		storeLandingPageVid.embedScreenshotOnFailure(scenario);
		storeLandingPageVid.quitWebDriver();
	}

	@Given("^I have Store url and valid registered user deatils$")
	public void i_have_Store_url_and_valid_registered_user_deatils() throws Throwable {
		storeLandingPageVid = (StoreLandingPage) applicationContext.getBean("storeLandingPageVid");
		currentUrl = storeLandingPageVid.getCurrentURL();
		LOG.info(currentUrl);
		storeLandingPageVid.goToStore(currentUrl + PAREX_USA_VID);
		LOG.info("User is landed on Parex Store Successfully");

	}

	@When("^I click on login$")
	public void i_click_on_login() throws Throwable {
		storeLoginPage = storeLandingPageVid.clickOnLoginCreateAccount();
		LOG.info("User has clicked on login");
	}

	@Then("^I should  navigate to login Page$")
	public void i_should_navigate_to_login_Page() throws Throwable {
		Assert.assertNotNull(storeLoginPage);
		LOG.info("User is landed on login page");
	}

	@When("^I fillup the login details$")
	public void i_fillup_the_login_details() throws Throwable {
		storeLandingPageVid = storeLoginPage.loginToStore(Constant.STORE_TEST_EMAILADDRESS,
				Constant.STORE_TEST_PASSWORD);
		LOG.info("User is logged in to Parex Store Successfully");
	}

	@Then("^I should navigate to homepage$")
	public void i_should_navigate_to_homepage() throws Throwable {
		Assert.assertNotNull(storeLandingPageVid);
	}

	@When("^I click on Parex Category$")
	public void i_click_on_Parex_Category() throws Throwable {
		if(storeLandingPageVid.getWindowLocationHref().contains("basket.html")){
			storeShoppingCartPage=	storeLandingPageVid.gotoCartPage();
			storeShoppingCartPage.removeItem();
			storeShoppingCartPage.continueShopping();			
		}
		storeCategoryPage = storeLandingPageVid.clickOnCategoryName(Categories.PAREX_MERKRETE.getCategoryName());
		LOG.info("User has choosen one category ");
		
	}

	@Then("^I should navigate to category page$")
	public void i_should_navigate_to_category_page() throws Throwable {
		storeCategoryPage.waitForPageToLoad("shop/merkrete");
		LOG.info("User is landed on Category page Successfully");
	}

	@When("^I click on a product$")
	public void i_click_on_a_product() throws Throwable {
		itemTitle = storeCategoryPage.getItemTitle("product_MER0006_id");
		storeProductDetailPage = storeCategoryPage.clickOnItem("product_MER0006_id");
		LOG.info("User is clicked on one available item");
	}

	@Then("^I should navigate to product page$")
	public void i_should_navigate_to_product_page() throws Throwable {
		Assert.assertTrue(storeProductDetailPage.isStaticTextDisplayed(itemTitle));
		LOG.info("User has verify item on product page");
	}

	@Then("^I should validate product attributes$")
	public void i_should_validate_product_attributes() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
	}

	@When("^I  click on Add to cart button$")
	public void i_click_on_Add_to_cart_button() throws Throwable {
		// Assert.assertFalse(storeProductDetailPage.isQuantityEmpty());
		storeShoppingCartPage = storeProductDetailPage.clickOnAddToCart();
	}

	@Then("^I should navigate to shopping cart page$")
	public void i_should_navigate_to_shopping_cart_page() throws Throwable {
		Assert.assertTrue(storeShoppingCartPage.isStaticTextDisplayed("Shopping Cart"));
	}

	@When("^I click to Checkout$")
	public void i_click_to_Checkout() throws Throwable {
		yourInfoPage = storeShoppingCartPage.clickCheckOutButton();
	}

	@Then("^I shoud navigate to your Information page$")
	public void i_shoud_navigate_to_your_Information_page() throws Throwable {
		Assert.assertTrue(yourInfoPage.getPageTitle().contains(yourInfoPage.YOUR_INFO_TITLE));
	}

	@When("^I filled the valid deatils and submit the Your Information page$")
	public void i_filled_the_valid_deatils_and_submit_the_Your_Information_page() throws Throwable {
		yourInfoPage.enterUSbillingInfo();
		storePaymentPage = yourInfoPage.clickContinueButton();
	}

	@Then("^I should navigate to Payment page and verify Shipping address$")
	public void i_should_navigate_to_Payment_page_and_verify_Shipping_address() throws Throwable {
		storePaymentPage.waitForPageToLoad("payment.html?");

	}

	@Then("^I should validate available Payment method$")
	public void i_should_validate_available_Payment_method() throws Throwable {
		Assert.assertTrue(storePaymentPage.isStaticTextDisplayed("Pay by Credit Card"));
	}

	@When("^I choose credit card as Payment method and click on continue button$")
	public void i_choose_credit_card_as_Payment_method_and_click_on_continue_button() throws Throwable {
		storeCcPayPage = storePaymentPage.clickOnContinueButton();
		Assert.assertTrue(storePaymentPage.isSecurityCCDialogBoxDisplayed());
	}

	@Then("^I should navigate CCPay Page and validate the url$")
	public void i_should_navigate_CCPay_Page_and_validate_the_url() throws Throwable {
		if (currentUrl.contains("clone") || currentUrl.contains("")) {
			storeCcPayPage.waitForPageToLoad(Constant.TEST_ENV_CCPAY_URL);
		} else {
			storeCcPayPage.waitForPageToLoad(Constant.PROD_ENV_CCPAY_URL);
		}
	}

	@When("^I submitted the CCPay page with valid credit card details$")
	public void i_submitted_the_CCPay_page_with_valid_credit_card_details() throws Throwable {
		LOG.info("User has entered the credit card details and submit cc pay page");
		storeReviewOrderPage = storeCcPayPage.enterCreditDetails(Constant.CREDIT_CARD_VISA_TYPE, Constant.NAME_ON_CREDIT_CARD, Constant.CVV_NO, Constant.EXPIRY_MONTH, Constant.EXPIRY_YEAR);
	}

	@Then("^I should navigate to checkout review page$")
	public void i_should_navigate_to_checkout_review_page() throws Throwable {
		storeReviewOrderPage.waitForPageToLoad("revieworder.html?");
		Assert.assertTrue(storeReviewOrderPage.isPlaceOrderDisplayer());
	}

}
