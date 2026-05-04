package com.zeldev.zel_e_comm.exception;

public class CartItemAlreadyAddedException extends RuntimeException{
    public CartItemAlreadyAddedException(String message) {
        super(message);
    }
}
