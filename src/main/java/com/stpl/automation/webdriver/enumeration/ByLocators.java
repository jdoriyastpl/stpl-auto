package com.stpl.automation.webdriver.enumeration;

/**
 * 
 * @author jdoriya 05-06-2017
 *
 */
public enum ByLocators {

	BY_XPATH("xpath"), BY_CSS("css"), BY_LINK_TEXT("linkText"), BY_CLASSNAME(
			"className"), BY_ID("id");

	private String byLocator;

	private ByLocators(String byLocator) {
		this.byLocator = byLocator;
	}

	public String getByLocator() {
		return byLocator;
	}

}
