package com.stpl.automation.webdriver;

import java.io.Closeable;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.context.ApplicationContext;

import com.google.common.collect.Sets;
import com.stpl.automation.exceptions.AutomationException;
import com.stpl.automation.webdriver.page.BasePage;

/**
 * @author jdoriya
 */
public final class StorePageFactory extends PageFactory implements Closeable {

	private static final Set<BasePage> PAGE_COLLECTIONS = Sets.newConcurrentHashSet();

	public static <T extends BasePage> T create(ApplicationContext applicationContext, String pageName)
			throws AutomationException {
		final Map<String, String> pageElements = (Map<String, String>) applicationContext.getBean("pageElements");
		final WebDriverConfig webDriverConfig = (WebDriverConfig) applicationContext.getBean("webDriverConfig");
		final WebDriver webDriver = WebDriverFactory.create(webDriverConfig);

		final T page = (T) applicationContext.getBean(pageName);

		page.setWebDriver(webDriver);
		page.setPageElements(pageElements);

		PAGE_COLLECTIONS.add(page);
		return page;
	}

	@Override
	public void close() throws IOException {
		PAGE_COLLECTIONS.forEach(BasePage::close);
	}
}
