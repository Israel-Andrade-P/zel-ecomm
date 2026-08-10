package com.zeldev.zel_e_comm.exception;

public class UnsupportedPaymentMethodException extends RuntimeException{
    public UnsupportedPaymentMethodException(String message) {
        super(message);
    }
}
