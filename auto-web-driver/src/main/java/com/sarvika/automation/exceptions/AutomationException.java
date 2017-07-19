
package com.sarvika.automation.exceptions;

import org.apache.logging.log4j.ThreadContext;

/**
 * @author jdoriya
 * 
 */
public final class AutomationException extends Exception {

	public AutomationException(final String message) {
		super(String.format("[%s]", message));
	}

	public AutomationException(final String message, final Exception exception) {
		super(String.format("[%s] %s", message, exception));
	}
}
