package com.zeldev.zel_e_comm.exception;

public class ConfirmationKeyExpiredException extends RuntimeException {
    public ConfirmationKeyExpiredException(String message) {
        super(message);
    }
}
