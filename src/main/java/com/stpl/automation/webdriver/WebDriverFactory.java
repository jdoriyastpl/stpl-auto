
package com.stpl.automation.webdriver;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.google.common.base.Preconditions;
import com.stpl.automation.exceptions.AutomationException;
import com.stpl.automation.webdriver.enumeration.TargetResource;


/**
 * @author jdoriya 05-06-2017
 *
 */
public final class WebDriverFactory {

    private static final Logger LOG = Logger.getLogger(WebDriverFactory.class.getSimpleName());

    private static final String BROWSER_NOT_SUPPORTED = "%s browser is not supported.";
    private static final String TARGET_RESOURCE_NOT_SUPPORTED = "Target resource %s is not supported.";
    private static final String INITIATING_NEW_WEB_DRIVER = "Instantiating web driver; configuration: %s.";
    private static final String WEB_DRIVER_TEST_IDENTIFIER = "Remote client Test Run Name - %s.";


    /**
     * Instantiate WebDriver based on webDriverConfiguration.
     *
     * @param webDriverConfiguration
     * @return
     * @throws AutomationException
     */
    public static WebDriver create(WebDriverConfig webDriverConfiguration) throws AutomationException {
        Preconditions.checkNotNull(webDriverConfiguration, "web driver configuration can not be null");
        LOG.info(String.format(INITIATING_NEW_WEB_DRIVER, webDriverConfiguration));
        LOG.info(String.format(WEB_DRIVER_TEST_IDENTIFIER, webDriverConfiguration.getCapability("name")));
        // For local tests only to avoid capturing logs for remote tests for now
        final LoggingPreferences logPrefs = new LoggingPreferences();
        if (System.getProperty("jsErrorsCheckFlag") != null) {
            logPrefs.enable(LogType.BROWSER, Level.ALL);
        }

        if (System.getProperty("perfLogsCheckFlag") != null) {
            logPrefs.enable(LogType.PERFORMANCE, Level.ALL);
        }

        final TargetResource targetResource = webDriverConfiguration.getTargetResource();
        if (targetResource.equals(TargetResource.LOCAL)) {
            final String browserName = webDriverConfiguration.getBrowserName().toLowerCase();
            if (browserName.equals(BrowserType.FIREFOX)) {
                if (System.getProperty("geckoDriverCheckFlag") != null
                        && System.getProperty("geckoDriverCheckFlag").equalsIgnoreCase(Boolean.TRUE.toString())) {
                    System.setProperty("webdriver.gecko.driver",
                            System.getProperty("webdriver.gecko.exe"));
                }
                final DesiredCapabilities caps = DesiredCapabilities.firefox();
                caps.setCapability(CapabilityType.SUPPORTS_ALERTS, true);
                caps.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);
                return getFirefoxWebDriver(caps);
            } else if (browserName.equals(BrowserType.CHROME)) {
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--kiosk");
                Map<String, Object> prefs = new HashMap<String, Object>();
                prefs.put("credentials_enable_service", false);
                prefs.put("password_manager_enabled", false);
                options.setExperimentalOption("prefs", prefs);
                final DesiredCapabilities caps = DesiredCapabilities.chrome();
                caps.setCapability(CapabilityType.SUPPORTS_ALERTS, true);
                caps.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);
                caps.setCapability(ChromeOptions.CAPABILITY, options);
                return getChromeWebDriver(caps);
            } else {
                throw new AutomationException(String.format(BROWSER_NOT_SUPPORTED, browserName));
            }
        } else if (targetResource.equals(TargetResource.REMOTE_CLIENT)) {
            final URL remoteWebDriver = webDriverConfiguration.getRemoteWebDriverUrl();
            return getRemoteWebDriver(remoteWebDriver, webDriverConfiguration);
        } else {
            throw new AutomationException(String.format(TARGET_RESOURCE_NOT_SUPPORTED, targetResource));
        }
    }

    private static WebDriver getChromeWebDriver(DesiredCapabilities caps) {
        final ChromeDriver webDriver = new ChromeDriver(caps);
        return webDriver;
    }

    private static WebDriver getFirefoxWebDriver(DesiredCapabilities caps) {
        final FirefoxDriver webDriver = new FirefoxDriver(caps);
        return webDriver;
    }

    private static WebDriver getRemoteWebDriver(URL remoteWebDriver, WebDriverConfig webDriverConfiguration) {
        final RemoteWebDriver webDriver = new RemoteWebDriver(remoteWebDriver, webDriverConfiguration);
        webDriver.setFileDetector(new LocalFileDetector());
        return webDriver;
    }
}
