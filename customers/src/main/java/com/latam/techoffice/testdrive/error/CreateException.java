package com.latam.techoffice.testdrive.error;

/**
 *
 * @author Mauricio "Maltron" Leal <maltron at gmail dot com> */
public class CreateException extends Exception {

    public CreateException() {
    }

    public CreateException(String message) {
        super(message);
    }

    public CreateException(String message, Throwable cause) {
        super(message, cause);
    }

    public CreateException(Throwable cause) {
        super(cause);
    }

    public CreateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
