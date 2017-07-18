package com.stpl.automation.webdriver.page;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.stpl.automation.constant.Constant;
import com.stpl.automation.constant.XPathConstant;
import com.stpl.automation.exceptions.AutomationException;
import com.stpl.automation.utils.JSUtilities;
import com.stpl.automation.utils.PollingUtilities;
import com.stpl.automation.webdriver.WebDriverConfig;
import com.stpl.automation.webdriver.WebDriverFactory;
import com.stpl.automation.webdriver.enumeration.ByLocators;

import org.openqa.selenium.TakesScreenshot;


import cucumber.api.Scenario;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.Logs;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;

import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.io.Closeable;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import java.util.TimeZone;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

/**
 * @author jdoriya 05-06-2017
 */
public abstract class BasePage implements Closeable {

	// TODO: poll threshold to be retrieved from property files.
	protected static final int WEB_ELEMENT_POLL_THRESHOLD_IN_MILLIS = 38000;
	protected static final int WAIT_FOR_LOADIN_DIALOG_TODISMISS = 200;
	protected static final int WEB_ELEMENT_POLL_THRESHOLD_IN_SEC = 150;
	private static final Logger LOG = Logger.getLogger(BasePage.class);
	private static final List<Class> EXPECTED_EXCEPTION_TYPES = Lists
			.newArrayList(ElementNotVisibleException.class,
					NoSuchElementException.class);
	private static final String ELEMENT_XPATH_IDENTIFIER = "xpath: ";
	public static final String JAVASCRIPT_ERROR_MESSAGE = "[JSErrorLogger]: Javascript console errors found!";
	private final TreeSet<String> browserHandles = new TreeSet<String>();
	protected WebDriver webDriver;
	private String originUrl;
	protected Map<String, String> pageElements;
	@Resource
	private WebDriverConfig webDriverConfig;
	protected static boolean FLAG_TO_VERIFY_STATUS;

	// To generate Random string, characters, Alphanumerics
	public static enum Mode {
		ALPHA, ALPHANUMERIC, NUMERIC
	}

	protected BasePage(WebDriver webDriver) {
		this.webDriver = webDriver;
	}

	// All subclasses running UI tests should capture screenshot on error
	public void embedScreenshotOnFailure(Scenario scenario, WebDriver driver) {
		if (scenario.isFailed()) {
			try {
				byte[] screenshot = ((TakesScreenshot) driver)
						.getScreenshotAs(OutputType.BYTES);
				scenario.embed(screenshot, "image/gif");
			} catch (WebDriverException wde) {
				System.err.println(wde.getMessage());
			} catch (ClassCastException cce) {
				cce.printStackTrace();
			}
		}
	}

	public BasePage(WebDriverConfig webDriverConfiguration, String scheme,
			String url) throws AutomationException {
		this(WebDriverFactory.create(webDriverConfiguration));
		this.originUrl = String.format(Constant.SCHEME_URL_TEMPLATE, scheme,
				url);
		setWebDimensionForTest();
		this.webDriver.get(originUrl);
		// verifyJavascriptConsoleErrors();
	}

	public static String getWebTestViewAsMobile() {
		return System.getProperty("web.test.mobile.view");
	}

	/**
	 * Check test view is for Mobile or Desktop View
	 */
	public void setWebDimensionForTest() {
		// if
		// (getWebTestViewAsMobile().equalsIgnoreCase(Boolean.TRUE.toString()))
		// {
		// Mobile Responsive view
		// this.webDriver.manage().window().setSize(new Dimension(320, 480));
		// } else {
		this.webDriver.manage().window().maximize();
		// }
	}

	public BasePage(WebDriver webDriver, Map<String, String> pageElements) {
		this(webDriver);
		this.pageElements = pageElements;
	}

	public BasePage(String scheme, String url) throws AutomationException {
		this.webDriver = WebDriverFactory.create(webDriverConfig);
		this.webDriver.manage().window().maximize();
		this.webDriver.get(String.format(Constant.SCHEME_URL_TEMPLATE, scheme,
				url));
	}

	public final void setPageElements(Map<String, String> pageElements) {
		this.pageElements = pageElements;
	}

	protected final void selectDropDownById(String elementId, String visibleText)
			throws AutomationException {
		final Select select = new Select(getWebElementById(elementId));
		select.selectByVisibleText(visibleText);
	}

	protected final void selectDropDownByIndex(String elementId, int index)
			throws AutomationException {
		final Select select = new Select(getWebElementById(elementId));
		select.selectByIndex(index);
	}

	protected final void selectDropDownByIndex(WebElement element, int index)
			throws AutomationException {
		final Select select = new Select(element);
		select.selectByIndex(index);
	}

	protected final void pollBeforeClick(WebElement webElement)
			throws AutomationException {
		PollingUtilities.pollAsyncException(
				refineElementIdentifierForPollLogging(webElement.toString()),
				WEB_ELEMENT_POLL_THRESHOLD_IN_MILLIS,
				ElementNotVisibleException.class, () -> click(webElement));
	}

	protected final void pollBeforeGetValue(WebElement webElement)
			throws AutomationException {
		PollingUtilities.pollAsyncException(
				refineElementIdentifierForPollLogging(webElement.toString()),
				WEB_ELEMENT_POLL_THRESHOLD_IN_MILLIS,
				ElementNotVisibleException.class, () -> getText(webElement));
	}

	protected final void pollExceptionBeforeClick(WebElement webElement)
			throws AutomationException {
		PollingUtilities.pollAsyncException(
				refineElementIdentifierForPollLogging(webElement.toString()),
				WEB_ELEMENT_POLL_THRESHOLD_IN_MILLIS, WebDriverException.class,
				() -> click(webElement));
	}

	private final int click(final WebElement webElement) {
		webElement.click();
		return 1;
	}

	/**
	 * use the method to close after switching on windows
	 */
	public void closeAllButOneBrowser() {
		// make sure we close any multiple windows we have open
		while (webDriver.getWindowHandles().size() > 1) {
			webDriver.close();
		}
		// and make sure we end up pointing the webDriver at the open browser
		webDriver.switchTo().window(
				webDriver.getWindowHandles().iterator().next());
	}

	public void switchToNewlyOpenedWindowUsingTitle(String title) {
		final Set<String> allWindows = webDriver.getWindowHandles();
		final Iterator<String> it = allWindows.iterator();
		while (it.hasNext()) {
			// wait for new win to open fully and then perfom switch action
			PollingUtilities.sleep(2000);
			webDriver.switchTo().window(it.next());
			if (webDriver.getTitle().contains(title)) {
				break;
			}
		}
	}

	protected final WebElement getWebElementById(String elementId)
			throws AutomationException {
		return getWebElementBy(byElementId(elementId));
	}

	protected final WebElement getWebElementByName(String elementName)
			throws AutomationException {
		return getWebElementBy(byElementName(elementName));
	}

	protected final WebElement getWebElementByClass(String elementClass)
			throws AutomationException {
		return getWebElementBy(byElementClass(elementClass));
	}

	protected final WebElement getWebElementByCss(String elementCss)
			throws AutomationException {
		return getWebElementBy(byElementCss(elementCss));
	}

	protected final List<WebElement> getWebElementsByCss(String elementCss)
			throws AutomationException {
		return getWebElementsBy(byElementCss(elementCss));
	}

	protected final WebElement getWebElementByLinkText(String elementLinkTextId)
			throws AutomationException {
		return getWebElementBy(byElementLinkText(elementLinkTextId));
	}

	protected final List<WebElement> getWebElementsByClass(String elementClass)
			throws AutomationException {
		return getWebElementsBy(byElementClass(elementClass));
	}

	protected final List<WebElement> getWebElementsByType(String elementType)
			throws AutomationException {
		return getWebElementsBy(byElementType(elementType));
	}

	protected final List<WebElement> getWebElementsByXpath(String elementXpath)
			throws AutomationException {
		return getWebElementsBy(byElementXpath(elementXpath));
	}

	protected final WebElement getWebElementByXpath(String elementXpath)
			throws AutomationException {
		return getWebElementBy(byElementXpath(elementXpath));
	}

	private By byElementXpath(String elementXpath) {
		return By.xpath(pageElements.get(elementXpath));
	}

	protected final WebElement getWebElementByLinkTextDirectly(String linkText)
			throws AutomationException {
		return getWebElementBy(byElementLinkTextDirectly(linkText));
	}

	protected final WebElement getWebElementByType(String elementType)
			throws AutomationException {
		return getWebElementBy(byElementType(elementType));
	}

	private final By byElementType(String elementType) {
		return By.xpath(XPathConstant
				.buildSearchElementByTypeString(pageElements.get(elementType)));
	}

	private final By byElementCss(String elementCss) {
		return By.cssSelector(pageElements.get(elementCss));
	}

	private final By byElementClass(String elementClass) {
		return By
				.xpath(XPathConstant
						.buildSearchElementByClassString(pageElements
								.get(elementClass)));
	}

	private final By byElementId(String elementId) {
		return By.xpath(XPathConstant.buildSearchElementByIdString(pageElements
				.get(elementId)));
	}

	private final By byElementLinkText(String elementLinkTextId) {
		return By.linkText(pageElements.get(elementLinkTextId));
	}

	private final By byElementLinkTextDirectly(String elementLinkText) {
		return By.linkText(elementLinkText);
	}

	private final By byElementName(String elementName) {
		return By.xpath(XPathConstant
				.buildSearchElementByNameString(pageElements.get(elementName)));
	}

	private final WebElement getWebElementBy(By by) throws AutomationException {
		final WebElement webElement = PollingUtilities
				.pollNotNullIgnoreException(
						refineElementIdentifierForPollLogging(by.toString()),
						WEB_ELEMENT_POLL_THRESHOLD_IN_MILLIS,
						EXPECTED_EXCEPTION_TYPES,
						() -> webDriver.findElement(by));
		return webElement;
	}

	private final List<WebElement> getWebElementsBy(By by)
			throws AutomationException {
		final List<WebElement> webElement = PollingUtilities
				.pollNotNullIgnoreException(
						refineElementIdentifierForPollLogging(by.toString()),
						WEB_ELEMENT_POLL_THRESHOLD_IN_MILLIS,
						EXPECTED_EXCEPTION_TYPES,
						() -> webDriver.findElements(by));
		return webElement;
	}

	protected void jsClickByCss(String elementKey) throws AutomationException {
		PollingUtilities.sleep(1000);
		final WebElement element = getWebElementByCss(elementKey);
		final String cssSelector = pageElements.get(elementKey);
		// JSUtilities.execute(webDriver, "document.querySelector('" +
		// cssSelector
		// + "').click();");
	}

	public void hoverOver(WebElement element) {
		final Actions action = new Actions(webDriver);
		action.moveToElement(element).build().perform();
	}

	/**
	 * Hover over an element and click
	 *
	 * @param webElement
	 *            : Object of located element
	 */
	public void hoverClick(WebElement webElement) {
		final Actions action = new Actions(webDriver);
		action.moveToElement(webElement).click().perform();
	}

	public void scrollIntoView(WebElement element) {
		final String scrollElementIntoMiddle = "var viewPortHeight = Math.max(document.documentElement.clientHeight, window.innerHeight || 0);"
				+ "var elementTop = arguments[0].getBoundingClientRect().top;"
				+ "window.scrollBy(0, elementTop-(viewPortHeight/2));";
		((JavascriptExecutor) webDriver).executeScript(scrollElementIntoMiddle,
				element);
	}

	public void scrollingToBottomofAPage() {
		((JavascriptExecutor) webDriver)
				.executeScript("window.scrollTo(0, document.body.scrollHeight)");
	}

	public String getText(WebElement element) {
		// Assert.assertNotNull(element);
		return element.getText().replaceAll("[^\\x00-\\x7f]", "");
	}

	/**
	 * Get current date and time in specified format and timezone
	 *
	 * @param format
	 *            Desired format for e.g. MM-DD-YYYY
	 *
	 * @param timeZone
	 *            Desired TimeZone e.g America/Los_Angeles
	 *
	 * @return Current date in the format specified
	 */
	public String getCurrentTimeInFormat(String format, String timeZone) {
		final DateFormat df = new SimpleDateFormat(format);
		df.setTimeZone(TimeZone.getTimeZone(timeZone));
		return df.format(new Date());
	}

	/**
	 * Get Past date
	 * 
	 * @param format
	 * @param timeZone
	 * @return
	 */
	public String getPastDate(String format, String timeZone) {
		final Calendar cal = Calendar.getInstance();
		final DateFormat dateFormat = new SimpleDateFormat(format);
		dateFormat.setTimeZone(TimeZone.getTimeZone(timeZone));
		cal.add(Calendar.DATE, -30);
		return dateFormat.format(cal.getTime());
	}

	/**
	 * Get Future Date
	 * 
	 * @param format
	 * @param timeZone
	 * @return
	 */
	public String getFutureDate(String format, String timeZone) {
		final Calendar cal = Calendar.getInstance();
		final DateFormat dateFormat = new SimpleDateFormat(format);
		dateFormat.setTimeZone(TimeZone.getTimeZone(timeZone));
		cal.add(Calendar.DATE, 30);
		return dateFormat.format(cal.getTime());
	}

	@Override
	public final void close() {
		if (Objects.nonNull(this.webDriver)) {
			try {
				LOG.info(String.format("Closing web driver session %s",
						webDriver));
				webDriver.quit();
			} catch (final Exception ex) {
				LOG.error(ex.getMessage());
			}
		}
	}

	/**
	 * Extract xpath from the element to log for debugging purposes
	 *
	 * @param elementIdentifier
	 * @return xpath if found else the entire WebElement or By for logging
	 */
	private static String refineElementIdentifierForPollLogging(
			String elementIdentifier) {
		final int xPathStartIndex = elementIdentifier
				.indexOf(ELEMENT_XPATH_IDENTIFIER);
		if (xPathStartIndex >= 0) {
			// Find element's xpath for identifying the element which
			// PollingUtility waits for
			final String xpathString = elementIdentifier.substring(
					xPathStartIndex + ELEMENT_XPATH_IDENTIFIER.length(),
					(elementIdentifier.length())).replace("]]", "]");
			if (!Strings.isNullOrEmpty(xpathString)) {
				return xpathString;
			} else {
				return elementIdentifier;
			}
		} else {
			return elementIdentifier;
		} // Return the entire webelement or By used to build the webelement if
			// xpath is not found
	}

	public void setWebDriver(WebDriver webDriver) {
		this.webDriver = webDriver;
	}

	// public void verifyJavascriptConsoleErrors() {
	// if (System.getProperty("jsErrorsCheckFlag") == null) {
	// return;
	// }
	// if (System.getProperty("jsErrorsCheckFlag").equalsIgnoreCase("FALSE")) {
	// return;
	// }
	// final Logs logs = webDriver.manage().logs();
	// final LogEntries logEntries = logs.get(LogType.BROWSER);
	// final List<LogEntry> logEntriesErrors = new ArrayList<>();
	// // final ArrayList<LogEntry> validLogEntries =
	// removeSuppressedErrors(logEntries);
	// for (final LogEntry logEntry : validLogEntries) {
	// if (logEntry.getMessage().toUpperCase().contains("ERROR:")
	// || logEntry.getMessage().toUpperCase().contains("SEVERE")) {
	// logEntriesErrors.add(logEntry);
	// }
	// }
	// if (logEntriesErrors.size() != 0) {
	// final StringBuilder sb = new StringBuilder();
	// sb.append(JAVASCRIPT_ERROR_MESSAGE);
	// sb.append("\n");
	// sb.append("<br>");
	// final String url = getWindowLocationHref();
	// sb.append("URL: " + url);
	// sb.append("<br>");
	// for (final LogEntry error : logEntriesErrors) {
	// sb.append(LEVEL_SEPARATOR);
	// sb.append("<br>");
	// sb.append("Timestamp: " + error.getTimestamp());
	// sb.append(LEVEL_SEPARATOR);
	// sb.append("<br>");
	// sb.append("Level: " + error.getLevel());
	// sb.append(LEVEL_SEPARATOR);
	// sb.append("<br>");
	// sb.append("Message: " + error.getMessage());
	// sb.append("\n");
	// sb.append("<br>");
	// }
	//
	// }
	// }

	// private ArrayList<LogEntry> removeSuppressedErrors(LogEntries logEntries)
	// {
	// final ArrayList<LogEntry> validLogEntries = new ArrayList<LogEntry>(
	// logEntries.getAll());
	// final Iterator itr = validLogEntries.listIterator();
	// while (itr.hasNext()) {
	// final LogEntry entry = (LogEntry) itr.next();
	// for (final SuppressableJSError error : SuppressableJSError.values()) {
	// if (entry.getMessage().contains(error.getMessage())) {
	// itr.remove();
	// }
	// }
	// }
	// return validLogEntries;
	// }

	/**
	 * * Switched to newly opened window
	 */
	public void switchToNewlyOpenedWindow() {
		PollingUtilities.sleep(4000);
		final String parentWindow = webDriver.getWindowHandle();
		browserHandles.toString();
		if (browserHandles.contains(parentWindow) == false) {
			browserHandles.add(parentWindow);
		}
		final Set<String> newHandleIdentifierSet = webDriver.getWindowHandles();
		newHandleIdentifierSet.removeAll(browserHandles);
		final String popUpHandle = newHandleIdentifierSet.iterator().next();
		// wait for win to open full then switch
		PollingUtilities.sleep(3000);
		webDriver.switchTo().window(popUpHandle);
	}

	/**
	 * This function is used to switch back to previous window when multiple
	 * windows have opened.
	 *
	 * @return
	 */
	public void switchBackToPreviousWindow() {
		webDriver.close();

		PollingUtilities.sleep(2000);
		final Set<String> allWindows = webDriver.getWindowHandles();
		final Iterator<String> it = allWindows.iterator();
		while (it.hasNext()) {
			webDriver.switchTo().window(it.next());
		}
	}

	protected final List<WebElement> getWebElementsById(String elementId)
			throws AutomationException {
		return getWebElementsBy(byElementId(elementId));
	}

	public void deleteAllCookies() {
		webDriver.manage().deleteAllCookies();
	}

	public String getSessionCookie() {
		final Cookie idv = webDriver.manage()
				.getCookieNamed("prosper_info_dev");
		final String idvCookie = String.valueOf(idv);
		return idvCookie;

	}

	public void acceptAlert() {
		final Alert alert = webDriver.switchTo().alert();
		alert.accept();
	}

	public void dismissAlert() {
		try {
			final Alert alert = webDriver.switchTo().alert();
			alert.dismiss();
		} catch (final NoAlertPresentException Ex) {
			LOG.error(Ex.getMessage());
		}
	}

	public void goTo(String url) throws AutomationException {
		webDriver.navigate().to(url);
	}

	public void doubleClick(WebElement element) {
		final Actions action = new Actions(webDriver);
		action.doubleClick(element).perform();
	}

	public void dragAndDrop(WebElement element, int xOffsetValue,
			int yOffsetValue) throws InterruptedException {
		final Actions action = new Actions(webDriver);
		action.dragAndDropBy(element, xOffsetValue, yOffsetValue).build()
				.perform();
	}

	public void clickAndDrag(WebElement element, int xOffsetValue,
			int yOffsetValue) throws InterruptedException {
		final Actions action = new Actions(webDriver);
		action.clickAndHold(element).moveByOffset(xOffsetValue, yOffsetValue)
				.release().build().perform();
	}

	/**
	 * Use JS util for navigation to back web page.
	 */
	public void navigateToBack() {
		webDriver.navigate().back();
		// JSUtilities.execute(webDriver, "window.history.go(-1)");
	}

	/**
	 * Use default Selenium helper for navigation to previous URL
	 *
	 */
	public void navigateToBackToPreviousUrl() {
		webDriver.navigate().back();
	}

	public void hitForwardBrowserButton() {
		webDriver.navigate().forward();
	}

	/**
	 * Select desired option from user drop-down at the header(top-right corner)
	 *
	 * @param option
	 *            Drop-down option to be selected
	 * @throws AutomationException
	 */
	public void selectFromUserHeaderDropdown(String option)
			throws AutomationException {
		hoverOver(getWebElementByXpath("user-header_xpath"));
		// temp fix for click on Message from dropdown on pre-reg page
		final List<WebElement> listItems = getWebElementsByXpath("headerListItems_dropdown_xpath");
		for (final WebElement listItem : listItems) {
			if (listItem.isDisplayed()) {
				if (getText(listItem).equalsIgnoreCase(option)) {
					listItem.click();
					break;
				}
			}
		}
	}

	/**
	 * Select desired option from user drop-down at the header(top-right corner)
	 * when you are on .NET page
	 *
	 * @param option
	 *            Option to be selected
	 * @throws AutomationException
	 */
	public void selectFromUserHeaderDropdownDOTNET(String option)
			throws AutomationException {

		hoverOver(getWebElementByCss("dotNetUserHeaderDropDown_css"));
		for (final WebElement listItem : getWebElementsByCss("dotNetUserHeaderDropDownOptions_css")) {
			if (getText(listItem).equalsIgnoreCase(option)) {
				listItem.click();
				break;
			}
		}
	}

	public void reload() {
		webDriver.navigate().refresh();
	}

	public void clickOnLink(String element) throws AutomationException {
		getWebElementByLinkText(element).click();
	}

	/**
	 * Needed to test URL Param at test method level
	 *
	 * @return
	 * @throws MalformedURLException
	 */
	public String getCurrentURL() {
		return webDriver.getCurrentUrl();
	}

	/**
	 * Method to retrive the environment url
	 */

	public String getWindowLocationHref() {
		// webdriver.getCurrentUrl sometimes does not return the actual current
		// url but returns the instantiated url
		return JSUtilities.execute(webDriver, "return window.location.href",
				String.class);
	}

	public void waitForPageToLoad(String urlIdentifier) {
		final WebDriverWait wait = new WebDriverWait(webDriver,
				WEB_ELEMENT_POLL_THRESHOLD_IN_MILLIS / 1000);
		wait.until(ExpectedConditions.urlContains(urlIdentifier));
	}

	public void waitForElement(By by) {
		final WebDriverWait wait = new WebDriverWait(webDriver, 10000);
		wait.until(ExpectedConditions.presenceOfElementLocated(by));
	}

	public boolean isInvalidOfferPageDisplayed() {
		return (webDriver.getPageSource().contains("Invalid Offer") || getWindowLocationHref()
				.contains("/error/E-115"));
	}

	private void waitUntilPageStopsLoading(int timeout,
			String elementIdentifierByCss, String getClassName) {
		final By loadingIndicatorLocator = By
				.cssSelector(elementIdentifierByCss);
		LOG.info("Loader stops spinning at" + getClassName);
		final WebDriverWait wait = new WebDriverWait(webDriver, timeout);
		wait.until(ExpectedConditions
				.invisibilityOfElementLocated(loadingIndicatorLocator));
	}

	private boolean pageStartsLoading(int timeout,
			String elementIdentifierByCss, String getClassName) {
		WebElement element = null;
		try {
			LOG.info("Loader starts spinning at " + getClassName);
			final By loadingIndicatorLocator = By
					.cssSelector(elementIdentifierByCss);

			final WebDriverWait wait = new WebDriverWait(webDriver, 10);
			element = wait.until(ExpectedConditions
					.visibilityOfElementLocated(loadingIndicatorLocator));

		} catch (final TimeoutException e) {
			// Loader isn't displayed yet, or already in syc
		}

		return element != null;
	}

	public boolean isStaticTextDisplayed(String staticText) {
		return webDriver.getPageSource().contains(staticText);
	}

	public boolean isTextDisplayed(String staticText) {
		return webDriver.findElement(By.tagName("body")).getText()
				.contains(staticText);
	}

	public String getOriginUrlWithScheme() {
		return this.originUrl;
	}

	public boolean isElementEnabled(WebElement element) {
		return element.isEnabled();
	}

	/**
	 * Check Specific element present on page's DOM
	 *
	 * @param locatorKey
	 * @return
	 */
	public boolean isElementPresentOnPage(By locatorKey) {
		try {
			this.webDriver.findElement(locatorKey);
			return true;
		} catch (final org.openqa.selenium.NoSuchElementException e) {
			return false;
		}
	}

	public boolean isElementDisplayed(WebElement element) {
		return element.isDisplayed();
	}

	/**
	 * Using existing byElement<id><xpath><css> method
	 *
	 * @param locatorKey
	 * @param byLocator
	 * @return
	 */
	public boolean isElementPresentOnPage(String locatorKey,
			ByLocators byLocator) {
		By element = null;
		try {
			switch (byLocator.getByLocator()) {
			case "id":
				element = byElementId(locatorKey);
				break;
			case "css":
				element = byElementCss(locatorKey);
				break;
			case "xpath":
				element = byElementXpath(locatorKey);
				break;
			case "className":
				element = byElementClass(locatorKey);
				break;
			case "linkText":
				element = byElementLinkTextDirectly(locatorKey);
				break;
			default:
				break;
			}
			return this.webDriver.findElement(element).isDisplayed();
		} catch (final org.openqa.selenium.NoSuchElementException e) {
			return false;
		}
	}

	/**
	 * Validate broken href
	 *
	 * @param linkUrl
	 * @return
	 * @throws AutomationException
	 * @throws IOException
	 */
	public boolean isLinkActive(String linkUrl) throws AutomationException {
		HttpURLConnection httpURLConnect = null;
		try {
			final URL url = new URL(linkUrl);

			httpURLConnect = (HttpURLConnection) url.openConnection();
			httpURLConnect.setConnectTimeout(1000);

			httpURLConnect.connect();

			if (httpURLConnect.getResponseCode() == 200) {
				return true;
			}
		} catch (final Exception e) {
			LOG.info(e.getMessage());
		}
		return false;
	}

	public Cookie getCookie(String cookieName) {
		return this.webDriver.manage().getCookieNamed(cookieName);
	}

	public String getCookieValue(String cookieName) {
		String value = null;
		try {
			value = URLDecoder
					.decode(getCookie(cookieName).getValue(), "UTF-8");
		} catch (final UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return value;
	}

	public void waitForElementToDisappear(By by) {
		webDriver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
		int count = 0;
		while (webDriver.findElements(by).size() > 0) {
			count++;
			if (count >= WEB_ELEMENT_POLL_THRESHOLD_IN_SEC) {
				final WebDriverWait wait = new WebDriverWait(webDriver,
						WEB_ELEMENT_POLL_THRESHOLD_IN_SEC);
				wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
				break;
			}
		}
	}

	/**
	 * Setting clipboard Data
	 */
	public void setClipboardData(String string) {
		final StringSelection stringSelection = new StringSelection(string);
		Toolkit.getDefaultToolkit().getSystemClipboard()
				.setContents(stringSelection, null);
	}

	public boolean verifyOptionsInsideDropDown(WebElement elementDD,
			Set<String> expectedSetOfOptions) throws AutomationException {
		final Select select = new Select(elementDD);
		final List<WebElement> options = select.getOptions();
		for (int i = 0; i < expectedSetOfOptions.size(); i++) {
			FLAG_TO_VERIFY_STATUS = false;
			if (expectedSetOfOptions.contains(options.get(i).getText())) {
				FLAG_TO_VERIFY_STATUS = true;
			} else if (FLAG_TO_VERIFY_STATUS == false) {
				break;
			}
		}
		return FLAG_TO_VERIFY_STATUS;
	}

	/**
	 * To select a value from drop down box
	 *
	 * @param value
	 *            :value to be selected
	 */
	public void selectDropDownByVisibleText(WebElement element, String value) {
		final Select select = new Select(element);
		select.selectByVisibleText(value);
	}

	/**
	 * Verify WebElement Prefilled
	 *
	 * @param webelement
	 * @return
	 */
	public boolean isWebElementPrefilled(WebElement webelement) {
		return !webelement.getAttribute("value").isEmpty();
	}

	/**
	 * Get Text Box Value
	 * 
	 * @param element
	 * @return
	 */
	public String getTextBoxValue(WebElement element) {
		return element.getAttribute("value");
	}

	/**
	 * Perform Click Operation using Actions Class
	 * 
	 * @param element
	 */
	public void clickUsingAction(WebElement element) {
		final Actions action = new Actions(webDriver);
		action.click(element).build().perform();
	}

	public void switchToFrame(String frameId) throws AutomationException {
		webDriver.switchTo().frame(getWebElementById(frameId));
	}

	public String generateRandomString(int length, Mode mode) {

		final StringBuffer buffer = new StringBuffer();
		String characters = "";

		switch (mode) {

		case ALPHA:
			characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
			break;

		case ALPHANUMERIC:
			characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
			break;

		case NUMERIC:
			characters = "1234567890";
			break;
		}

		final int charactersLength = characters.length();

		for (int i = 0; i < length; i++) {
			final double index = Math.random() * charactersLength;
			buffer.append(characters.charAt((int) index));
		}
		return buffer.toString();
	}

	/**
	 * Get random number
	 */
	public int getRandomNumber(int minValue, int maxValue) {
		return new Random().nextInt(maxValue - minValue + 10) + minValue;
	}

	// Http response code - 200 OK
	public boolean checkResponse(String url) {
		final HttpClient client = HttpClientBuilder.create().build();
		final HttpGet request = new HttpGet(url);
		try {
			final HttpResponse response = client.execute(request);
			// verifying response code and The HttpStatus should be 200 if not,
			// increment invalid link count
			// // We can also check for 404 status code like
			// response.getStatusLine().getStatusCode() == 404
			if (response.getStatusLine().getStatusCode() == 200) {
				FLAG_TO_VERIFY_STATUS = true;
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return FLAG_TO_VERIFY_STATUS;
	}

	public String twoDecimalPlaces(double amount) {
		final DecimalFormat twoPlaces = new DecimalFormat("0.00");
		twoPlaces.setRoundingMode(RoundingMode.DOWN);
		return twoPlaces.format(amount);
	}

	/**
	 * Format Date
	 * 
	 * @param oldFormat
	 * @param oldDate
	 * @param newFormat
	 * @return
	 * @throws ParseException
	 */
	public String formatDate(String oldFormat, String oldDate, String newFormat)
			throws ParseException {

		final Date date = new SimpleDateFormat(oldFormat).parse(oldDate);
		return new SimpleDateFormat(newFormat).format(date);
	}

}
