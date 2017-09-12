package com.stpl.automation.webdriver.enumeration;

/**
 * @author jdoriya
 */
public enum ScreenResolution {

	SCREEN_1280_1024("1280x1024"),
    SCREEN_1920_1080("2048x1536");

    private String screenResolution;


    ScreenResolution(String screenResolution) {
        this.screenResolution = screenResolution;
    }

    public String getScreenResolution() {
        return screenResolution;
    }
}
