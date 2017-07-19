
package com.sarvika.automation.utils;


import org.apache.log4j.Logger;

import com.sarvika.automation.exceptions.AutomationException;

import java.util.List;
import java.util.Objects;

/**
 * Created by pbudiono on 10/23/15.
 */
public final class PollingUtilities {

    private static final Logger LOG = Logger.getLogger(PollingUtilities.class.getSimpleName());

    private static final int POLL_FACTOR = 10;
    private static final int MILLIS_IN_SECOND = 100;

    private static final String POLL_FAILURE_LOG_MESSAGE = "Unable to find item by polling.";
    private static final String POLL_PROGRESS_LOG_MESSAGE = "[Waiting for element: %s] [Sleeping for %d ms]";
    private static final String UNABLE_TO_FIND_ELEMENT_MESSAGE = "Unable to retrieve object after %d ms of polling.";
    private static final String STRING_RETRIEVAL_ERROR_LOG = "String retrieval error.";
    private static final String OBJECT_RETRIEVAL_ERROR_LOG = "Object retrieval error.";
    private static final String UNABLE_TO_RETRIEVE_OBJECT_MESSAGE = "Unable to retrieve object after %d ms of polling.";


    private PollingUtilities() {
    }

    public static <T extends List> T pollListNotEmpty(String elementIdentifier, final int pollTimeInMillis,
                                                      final PollMethodReference<T> pollMethodReference)
                                                              throws AutomationException {
        int binarySequence = 0;
        int sleepTimeInMillis = 0;
        while (pollTimeInMillis >= sleepTimeInMillis) {
            try {
                final T returnValue = pollMethodReference.execute();
                if (returnValue.size() > 0) {
                    return returnValue;
                }
            } catch (Exception ex) {
                sleepTimeInMillis = ((int) Math.pow(2.0, binarySequence) * MILLIS_IN_SECOND);
                sleep(elementIdentifier, sleepTimeInMillis);
                binarySequence++;
            }
        }
        throw new AutomationException(POLL_FAILURE_LOG_MESSAGE);
    }

    public static <T> T pollNotNullIgnoreException(String elementIdentifier, int pollTimeInMillis,
                                                   List<Class> expectedExceptionClassTypes,
                                                   final PollMethodReference<T> pollMethodReference) throws AutomationException {
        int binarySequence = 0;
        int sleepTimeInMillis = 0;
        while (pollTimeInMillis >= sleepTimeInMillis) {
            try {
                final T returnValue = pollMethodReference.execute();
                if (Objects.nonNull(returnValue)) {
                    return returnValue;
                }
            } catch (Exception ex) {
                final Class actualExceptionClass = ex.getClass();
                if (!expectedExceptionClassTypes.contains(actualExceptionClass)) {
                    throw new AutomationException(
                            String.format("Unexpected %s is thrown.", actualExceptionClass.getSimpleName()));
                }
                sleepTimeInMillis = ((int) Math.pow(2.0, binarySequence) * MILLIS_IN_SECOND);
                sleep(elementIdentifier, sleepTimeInMillis);
                binarySequence++;
            }
        }

        throw new AutomationException(String.format(UNABLE_TO_FIND_ELEMENT_MESSAGE, sleepTimeInMillis));
    }

    public static <T> void pollAsyncException(String elementIdentifier, final int pollTimeInMillis, Class exceptionClass,
                                              final PollMethodReference<T> pollMethodReference) throws AutomationException {
        int binarySequence = 0;
        int sleepTimeInMillis = 0;
        while (pollTimeInMillis >= sleepTimeInMillis) {
            try {
                pollMethodReference.execute();
                return;
            } catch (Exception ex) {
                if (!Objects.equals(ex.getClass(), exceptionClass)) {
                    throw ex;
                }
            }
            sleepTimeInMillis = ((int) Math.pow(2.0, binarySequence) * MILLIS_IN_SECOND);
            sleep(elementIdentifier, sleepTimeInMillis);
            binarySequence++;
        }
        throw new AutomationException(String.format(UNABLE_TO_FIND_ELEMENT_MESSAGE, sleepTimeInMillis));
    }

    private static void sleep(String elementIdentifier, int millisecond) {
        LOG.info(String.format(POLL_PROGRESS_LOG_MESSAGE, elementIdentifier, millisecond));
        sleep(millisecond);
    }

    public static void sleep(int millisecond) {
        try {
            Thread.sleep(millisecond);
        } catch (InterruptedException e) {
            LOG.error(e.getMessage());
        }
    }

    public static <T> String pollStringNotNull(int pollTimeInMillis, final PollMethodReference<T> pollMethodReference)
            throws AutomationException {

        int binarySequence = 0;
        int sleepTimeInMillis = 0;
        while (pollTimeInMillis >= sleepTimeInMillis) {
            try {
                final T returnValue = pollMethodReference.execute();
                if (Objects.nonNull(returnValue)) {
                    return (String) returnValue;
                }
            } catch (Exception ex) {
                throw new AutomationException(STRING_RETRIEVAL_ERROR_LOG, ex);
            }
            sleepTimeInMillis = ((int) Math.pow(2.0, binarySequence) * MILLIS_IN_SECOND);
            sleep(sleepTimeInMillis);
            binarySequence++;
        }

        throw new AutomationException(String.format(UNABLE_TO_FIND_ELEMENT_MESSAGE, sleepTimeInMillis));
    }

    public static <T, U> U pollObjectNotNull(int pollTimeInMillis, final PollMethodReference<T> pollMethodReference,
            final Class<U> returnClassObject) throws AutomationException {

        int binarySequence = 0;
        int sleepTimeInMillis = 0;
        while (pollTimeInMillis >= sleepTimeInMillis) {
            try {
                final T returnValue = pollMethodReference.execute();
                if (Objects.nonNull(returnValue)) {
                    return (U) returnValue;
                }
            } catch (Exception ex) {
                throw new AutomationException(OBJECT_RETRIEVAL_ERROR_LOG, ex);
            }
            sleepTimeInMillis = ((int) Math.pow(2.0, binarySequence) * MILLIS_IN_SECOND);
            sleep(sleepTimeInMillis);
            binarySequence++;
        }

        throw new AutomationException(String.format(UNABLE_TO_RETRIEVE_OBJECT_MESSAGE, sleepTimeInMillis));
    }

    public interface PollMethodReference<T> {

        T execute() throws AutomationException;
    }
}
