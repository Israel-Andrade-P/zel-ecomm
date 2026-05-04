package com.zeldev.zel_e_comm.exception;

public class APIException extends RuntimeException {
    private static final long serialVersionId = 1L;

    public APIException(String message) {
        super(message);
    }

    public APIException() {
    }
}
