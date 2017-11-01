package com.latam.techoffice.testdrive.error;

import java.util.logging.Logger;

/**
 *
 * @author Mauricio "Maltron" Leal <maltron at gmail dot com>
 */
public class InvalidException extends Exception {

    private static final Logger LOG = Logger.getLogger(InvalidException.class.getName());

    public InvalidException() {
        super();
    }

    public InvalidException(String message) {
        super(message);
    }

    public InvalidException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidException(Throwable cause) {
        super(cause);
    }

    public InvalidException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
