//package com.sarvika.automation.step_definitions_test;
//
//import static org.junit.Assert.assertNotNull;
//
//import java.util.Hashtable;
//import java.util.Map;
//
//import javax.annotation.Resource;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.testng.annotations.Test;
//
//import com.sarvika.automation.StoreTestBase;
//import com.sarvika.automation.exceptions.AutomationException;
//import com.sarvika.automation.store.enumerations.Categories;
//import com.sarvika.automation.store.pages.StoreCategoryPage;
//import com.sarvika.automation.store.pages.StoreLandingPage;
//import com.sarvika.automation.webdriver.WebDriverConfig;
//
//public class DummyTest extends StoreTestBase{
//	Hashtable<String, String> storeUrl;
//	public void setup() throws AutomationException {
//		storeUrl = getStoreUri();
//	}
//
//	@Value("${store.site.scheme}")
//	protected String storeSiteUrlScheme;
//	@Value("${store.site.url}")
//	protected String storeSiteUrl;
//	@Resource
//	public WebDriverConfig webDriverConfig;
//	@Resource
//	protected Map<String, String> pageElements;
//	@Test
//	void testDummy1() throws AutomationException{
//
//		try (final StoreLandingPage storeLandingPage = new StoreLandingPage(
//				webDriverConfig, storeSiteUrlScheme, storeSiteUrl + storeUrl)) {
//			storeLandingPage.setPageElements(pageElements);
//
//			// User click on Category on Top Menu
//			StoreCategoryPage storeCategoryPage = storeLandingPage
//					.clickOnCategoryName(Categories.APPAREL.getCategoryName());
//
//			assertNotNull("Category Page is not Loaded", storeCategoryPage);
//
//		}
//	}
//}
