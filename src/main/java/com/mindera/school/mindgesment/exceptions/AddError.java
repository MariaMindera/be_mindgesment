package com.mindera.school.mindgesment.exceptions;

/**
 * AddError class extends RuntimeException and
 * exists to be used when there is an error in the method of adding.
 */
public class AddError extends RuntimeException {

    public AddError(String object, Throwable throwable) {
        super(String.format("Invalid add %s.", object), throwable);
    }
}
