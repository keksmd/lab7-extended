package com.alexkekiy.server.exceptions;

public class YouMustBeLoggedException extends Exception{
    public YouMustBeLoggedException() {
    }

    public YouMustBeLoggedException(String message) {
        super(message);
    }
}
