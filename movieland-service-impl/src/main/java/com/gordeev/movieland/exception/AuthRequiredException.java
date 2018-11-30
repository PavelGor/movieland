package com.gordeev.movieland.exception;

public class AuthRequiredException extends RuntimeException {
    public AuthRequiredException(String message) {
        super(message);
    }
}
