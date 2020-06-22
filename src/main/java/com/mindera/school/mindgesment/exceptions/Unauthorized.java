package com.mindera.school.mindgesment.exceptions;

/**
 * Unauthorized class extends RuntimeException and
 * exists to be used when a user is not allowed to access an endpoint.
 */
public class Unauthorized extends RuntimeException {

    public Unauthorized() {
        super("Unauthorized method.");
    }
}
