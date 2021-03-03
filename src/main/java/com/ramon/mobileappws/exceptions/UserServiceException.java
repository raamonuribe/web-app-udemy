package com.ramon.mobileappws.exceptions;

public class UserServiceException extends RuntimeException{
    private static final long serialVersionUID = -5960250361995478628L;

    public UserServiceException(String message) {
        super(message);
    }
}
