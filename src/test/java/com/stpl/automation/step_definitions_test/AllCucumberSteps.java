package com.stpl.automation.step_definitions_test;

import java.util.Hashtable;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.stpl.automation.exceptions.AutomationException;
import com.stpl.automation.stores.StoreTestBase;
import com.stpl.automation.stplstores.storepages.StoreLandingPage;

import cucumber.api.java.Before;
import cucumber.api.java.en.Given;

/**
 * @author jdoriya 08-06-2017
 *
 */
public class AllCucumberSteps extends StoreTestBase {

	private static final Logger LOG = Logger.getLogger(AllCucumberSteps.class.getSimpleName());

	Hashtable<String, String> storeUrl;

	@Before
	public void setup() throws AutomationException {
		initializeSystemProperties();
		storeUrl = getStoreUri();
	}

	private void initializeSystemProperties() {

		applicationContext = new ClassPathXmlApplicationContext("ApplicationContext.xml");
		applicationContext.getAutowireCapableBeanFactory().autowireBean(this);
	}

	@Given("^I have Store url$")
	public void i_have_Store_url() throws Throwable {
		// final ApplicationContext jobContext = new
		// ClassPathXmlApplicationContext("ApplicationContext.xml");

		final StoreLandingPage storeLandingPage = (StoreLandingPage) applicationContext.getBean("storeLandingPage");

		String currentUrl = storeLandingPage.getCurrentURL();

		storeLandingPage.goToStore(currentUrl + "/" + storeUrl.get("URL"));

		/*
		 * try (final StoreLandingPage storeLandingPage = new StoreLandingPage(
		 * webDriverConfig, storeSiteUrlScheme, storeSiteUrl + storeUrl)) {
		 * LOG.info("Landing Store");
		 * storeLandingPage.setPageElements(pageElements);
		 * 
		 * // User click on Category on Top Menu StoreCategoryPage
		 * storeCategoryPage = storeLandingPage
		 * .clickOnCategoryName(Categories.APPAREL.getCategoryName());
		 * 
		 * assertNotNull("Category Page is not Loaded", storeCategoryPage);
		 * 
		 * }
		 */
	}
}
