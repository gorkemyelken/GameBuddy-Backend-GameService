package com.gamebuddy.GameService.exception;

public class GameStatNotFoundException extends RuntimeException {
    public GameStatNotFoundException(String message) {
        super(message);
    }
}
