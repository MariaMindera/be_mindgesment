package com.mindera.school.mindgesment.exceptions;

/**
 * DeleteError class extends RuntimeException and
 * exists to be used when there is an error in the method of deleting.
 */
public class DeleteError extends RuntimeException {

    public DeleteError(String object, Throwable throwable) {
        super(String.format("Invalid delete %s.", object), throwable);
    }
}
