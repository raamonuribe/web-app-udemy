package com.ramon.mobileappws.exceptions;

public class AddressServiceException extends RuntimeException{
    private static final long serialVersionUID = -5960250361995478628L;

    public AddressServiceException(String message) {
        super(message);
    }
}