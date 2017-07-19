package com.sarvika.automation.webdriver.client;

import com.sarvika.automation.enumerations.HttpMediaType;
import com.sarvika.automation.httpClient.AbstractHttpClient;

/**
 * @author user 12-06-2017
 *
 */
public class BrowserStackClient extends AbstractHttpClient {
	private String browserStackUserName;

	public String getBrowserStackUserName() {
		return browserStackUserName;
	}

	public void setBrowserStackUserName(String browserStackUserName) {
		this.browserStackUserName = browserStackUserName;
	}

	public BrowserStackClient(String scheme, String hostName,
			int connectionTimeout, int socketTimeout) {
		super(scheme, hostName, connectionTimeout, socketTimeout);
		setHttpMediaType(HttpMediaType.JSON);
		setHostName(hostName);
	}

}
