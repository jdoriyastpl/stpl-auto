package com.sarvika.automation.requestBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Objects;

import org.apache.http.client.utils.URIBuilder;

import com.sarvika.automation.exceptions.AutomationException;

public final class RequestUriBuilder {

	 private static final String DEFAULT_ENCODING = "UTF-8";

	    private String scheme;
	    private String hostName;


	    public RequestUriBuilder(final String scheme, final String hostName) {
	        this.scheme = scheme;
	        this.hostName = hostName;
	    }

	    public URI buildUri(final String requestPath) throws AutomationException {
	        return buildUri(requestPath, null);
	    }

	    /**
	     * A method to build web uri request.
	     *
	     * @param requestPath
	     * @param queryParameters
	     * @return
	     * @throws AutomationException
	     */
	    public URI buildUri(final String requestPath, final Map<String, String> queryParameters) throws AutomationException {
	        try {
	            final URIBuilder uriBuilder =
	                    new URIBuilder(DEFAULT_ENCODING).setScheme(this.scheme).setHost(this.hostName).setPath(requestPath);
	            if (Objects.nonNull(queryParameters)) {
	                for (final Map.Entry<String, String> queryParameter : queryParameters.entrySet()) {
	                    uriBuilder.setParameter(queryParameter.getKey(), queryParameter.getValue());
	                }
	            }

	            return uriBuilder.build();
	        } catch (final URISyntaxException ex) {
	            throw new AutomationException("Unable to build URL", ex);
	        }
	    }

	    public void setHostName(final String hostName) {
	        this.hostName = hostName;
	    }
}
