package com.stpl.automation.constant;

/**
 * @author jdoriya
 */
public final class XPathConstant {

    public static String buildSearchElementByTypeString(String type) {
        return String.format("//*[@type='%s']", type);
    }

    public static String buildSearchElementByIdString(String id) {
        return String.format("//*[@id='%s']", id);
    }

    public static String buildSearchElementByNameString(String name) {
        return String.format("//*[@name='%s']", name);
    }

    public static String buildSearchElementByClassString(String className) {
        return String.format("//*[@class='%s']", className);
    }
}
