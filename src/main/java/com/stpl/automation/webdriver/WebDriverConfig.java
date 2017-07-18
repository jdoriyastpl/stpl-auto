
package com.stpl.automation.webdriver;


import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.stereotype.Component;

import com.stpl.automation.exceptions.AutomationException;
import com.stpl.automation.webdriver.enumeration.ScreenResolution;
import com.stpl.automation.webdriver.enumeration.TargetResource;


/**
 * @author jdoriya 05-06-2017
 *
 */
@Component
public final class WebDriverConfig extends DesiredCapabilities {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// TODO: use the following in the Spring context
    private static final String TARGET_RESOURCE_KEY = "targetResource";
    private static final String BROWSER_NAME_KEY = "browserName";
    private static final String BROWSER_VERSION = "browserVersion";
    private static final String PLATFORM_KEY = "platform";
    private static final String BROWSER_STACK_USER_NAME_KEY = "browserStackUserName";
    private static final String BROWSER_STACK_ACCESS_KEY = "accessKey";
    private static final String REMOTE_CLIENT_URL_KEY = "remoteWebDriverUrlTemplate";
    private final Map<String, Object> testConfiguration;
   
    public WebDriverConfig(final Map<String, Object> testConfiguration) {
        this.testConfiguration = testConfiguration;
        setCapability(CapabilityType.VERSION, String.valueOf(testConfiguration.get(BROWSER_VERSION)));
        setBrowserName(String.valueOf(testConfiguration.get(BROWSER_NAME_KEY)));
        // SAUCE :This setting specifies which screen resolution should be used during the test session.
        setCapability("screen-resolution", ScreenResolution.SCREEN_1920_1080.getScreenResolution());
        setPlatform((Platform) testConfiguration.get(PLATFORM_KEY));
    }


    public TargetResource getTargetResource() {
        return (TargetResource) testConfiguration.get(TARGET_RESOURCE_KEY);
    }

    @Override
    public String getBrowserName() {
        return (String) testConfiguration.get(BROWSER_NAME_KEY);
    }

    public URL getRemoteWebDriverUrl() throws AutomationException {
        final String remoteSeleniumWebDriverUrlTemplate = String.valueOf(testConfiguration.get(REMOTE_CLIENT_URL_KEY));
        final String remoteSeleniumWebDriverUrl = String.format(remoteSeleniumWebDriverUrlTemplate,
                testConfiguration.get(BROWSER_STACK_USER_NAME_KEY), testConfiguration.get(BROWSER_STACK_ACCESS_KEY));
        try {
            return new URL(remoteSeleniumWebDriverUrl);
        } catch (MalformedURLException ex) {
            throw new AutomationException(ex.getMessage());
        }
        
    }

    @Override
    public String toString() {
        return "WebDriverConfig { " + "testConfiguration = " + testConfiguration + " }";
    }
}
