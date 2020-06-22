package com.mindera.school.mindgesment.exceptions;

/**
 * RegisterError class extends RuntimeException and
 * exists to be used when there was an error registering a user.
 */
public class RegisterError extends RuntimeException {

    public RegisterError(Throwable throwable) {
        super("Invalid user register.", throwable);
    }
}
