package com.zeldev.zel_e_comm.exception;

public class CartIsEmptyException extends RuntimeException {
    public CartIsEmptyException(String message) {
        super(message);
    }
}
