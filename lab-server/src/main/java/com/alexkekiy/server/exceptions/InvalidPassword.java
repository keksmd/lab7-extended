package com.alexkekiy.server.exceptions;

public class InvalidPassword extends Exception{
    public InvalidPassword() {
    }

    public InvalidPassword(String message) {
        super(message);
    }
}
