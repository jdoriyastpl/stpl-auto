package com.sarvika.automation.httpClient;

import org.apache.http.client.config.RequestConfig;

import com.google.common.base.Preconditions;
import com.sarvika.automation.enumerations.HttpMediaType;
import com.sarvika.automation.requestBuilder.RequestUriBuilder;

/**
 * @author user 12-06-2017
 *
 */
public abstract class AbstractHttpClient {

	private HttpMediaType httpMediaType;
	private final RequestUriBuilder requestUriBuilder;
	private final RequestConfig requestConfig;
	private static final int MILLIS_IN_SECOND = 1000;
	private static final String REQUEST_URI_TEMPLATE = "Request URI: %s";

	public AbstractHttpClient(final String scheme, final String hostName,
			final int connectionTimeout, final int socketTimeout) {
		Preconditions.checkNotNull(scheme, "scheme can not be null");
		Preconditions.checkNotNull(hostName, "hostName can not be null");
		requestUriBuilder = new RequestUriBuilder(scheme, hostName);
		requestConfig = RequestConfig.custom()
				.setConnectTimeout(connectionTimeout * MILLIS_IN_SECOND)
				.setSocketTimeout(socketTimeout * MILLIS_IN_SECOND).build();
	}

	protected final void setHttpMediaType(final HttpMediaType httpMediaType) {
		Preconditions.checkNotNull(httpMediaType,
				"httpMediaType can not be null");
		this.httpMediaType = httpMediaType;
	}
		
	protected final void setHostName(final String hostName) {
        Preconditions.checkNotNull(hostName, "hostName can not be null");
        requestUriBuilder.setHostName(hostName);
    }
}
