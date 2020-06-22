package com.mindera.school.mindgesment.exceptions;

/**
 * InvalidToken class extends RuntimeException and
 * exists to be used when the user's token is invalid.
 */
public class InvalidToken extends RuntimeException {

    public InvalidToken() {
        super("Invalid Token. Please try again");
    }
}
