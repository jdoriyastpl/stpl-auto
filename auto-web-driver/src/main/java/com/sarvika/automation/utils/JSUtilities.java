
package com.sarvika.automation.utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

/**
 * @author jdoriya
 */
public final class JSUtilities {

    public static <T> T execute(final WebDriver webDriver, final String script, final Class<T> returnClass) {
        final JavascriptExecutor javascriptExecutor = (JavascriptExecutor) webDriver;
        final Object returnObject = javascriptExecutor.executeScript(script);
        return returnClass.cast(returnObject);
    }

    public static void execute(final WebDriver webDriver, final String script) {
        final JavascriptExecutor javascriptExecutor = (JavascriptExecutor) webDriver;
        javascriptExecutor.executeScript(script);
    }
}
