package com.latam.techoffice.testdrive.error;

/**
 *
 * @author Mauricio "Maltron" Leal <maltron@gmail.com> */
public class ServerInternalException extends Exception {

    public ServerInternalException() {
    }

    public ServerInternalException(String message) {
        super(message);
    }

    public ServerInternalException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServerInternalException(Throwable cause) {
        super(cause);
    }

    public ServerInternalException(String message, Throwable cause, 
                            boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
