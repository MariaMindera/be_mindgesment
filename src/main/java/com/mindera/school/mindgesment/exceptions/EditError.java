package com.mindera.school.mindgesment.exceptions;

/**
 * EditError class extends RuntimeException and
 * exists to be used when there is an error in the method of editing.
 */
public class EditError extends RuntimeException {

    public EditError(String object, Throwable throwable) {
        super(String.format("Invalid edit %s.", object), throwable);
    }
}
