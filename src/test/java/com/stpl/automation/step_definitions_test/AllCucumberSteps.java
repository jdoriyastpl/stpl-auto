package com.stpl.automation.step_definitions_test;


import static org.testng.Assert.assertTrue;

import java.util.Hashtable;

import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.stpl.automation.constant.Constant;
import com.stpl.automation.exceptions.AutomationException;
import com.stpl.automation.stores.StoreTestBase;
import com.stpl.automation.stplstores.enumerations.Categories;
import com.stpl.automation.stplstores.storepages.StoreCategoryPage;
import com.stpl.automation.stplstores.storepages.StoreLandingPage;
import com.stpl.automation.stplstores.storepages.StoreLoginPage;
import com.stpl.automation.stplstores.storepages.StoreProductDetailPage;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * @author jdoriya 08-06-2017
 *
 */
public class AllCucumberSteps extends StoreTestBase {

	private static final Logger LOG = Logger.getLogger(AllCucumberSteps.class.getSimpleName());

	Hashtable<String, String> storeUrl;
	StoreLandingPage storeLandingPage;
	StoreCategoryPage storeCategoryPage;
	StoreProductDetailPage storeProductDetailPage;

	@Before
	public void setup() throws AutomationException {
		
		initializeSystemProperties();
		storeUrl = getStoreUri();
	}
	
	@After
	public void scenarioTearDown(Scenario scenario){
		storeLandingPage.embedScreenshotOnFailure(scenario);
		storeLandingPage.quitWebDriver();
	}

	private void initializeSystemProperties() {

		applicationContext = new ClassPathXmlApplicationContext("ApplicationContext.xml");
		applicationContext.getAutowireCapableBeanFactory().autowireBean(this);
		storeLandingPage = (StoreLandingPage) applicationContext.getBean("storeLandingPage");
	}

	@Given("^I have Store url$")
	public void i_have_Store_url() throws Throwable {
		String currentUrl = storeLandingPage.getCurrentURL();

		storeLandingPage.goToStore(currentUrl + "/" + storeUrl.get("URL"));
		LOG.info("User is landed on SWA Store Successfully");
		//Login to Store with valid email+Password
		StoreLoginPage storeLoginPage	=storeLandingPage.clickOnLoginCreateAccount();
		LOG.info("User is landed on SWA Login Page Successfully");
		storeLandingPage = storeLoginPage.loginToStore(Constant.STORE_TEST_EMAILADDRESS, Constant.STORE_TEST_PASSWORD);
		LOG.info("User is able to login to SWA store Successfully");
	}

	@When("^I click on CategoryName$")
	public void i_click_on_CategoryName() throws Throwable {
		storeCategoryPage = storeLandingPage.clickOnCategoryName(Categories.APPAREL.getCategoryName());
	}

	@Then("^I navigate to Category Page$")
	public void i_navigate_to_Category_Page() throws Throwable {
		
	}

	@Then("^I add an item into Cart$")
	public void i_add_an_item_into_Cart() throws Throwable {
		storeProductDetailPage = storeCategoryPage.clickOnItem("Warrior_Spirit_T-Shirt");
		// Verify if Item is having options available or Not i.e Colors, Size
		if(storeProductDetailPage.verifyColorOrSize()){
			storeProductDetailPage.selectSizeOptionAs("Medium");
		}
		storeProductDetailPage.clickOnAddToCart();
	}
}