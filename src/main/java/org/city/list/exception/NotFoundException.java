package org.city.list.exception;

public class NotFoundException extends RuntimeException {

    public NotFoundException(String reason) {
        super(reason);
    }

    public NotFoundException(String reason, Throwable cause) {
        super(reason, cause);
    }
}
