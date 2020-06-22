package com.mindera.school.mindgesment.exceptions;

/**
 * NotExits class extends RuntimeException and
 * exists to be used when something not exists.
 */
public class NotExits extends RuntimeException {

    public NotExits(String object, String id) {
        super(String.format("%s not found with this id=%s.", object, id));
    }
}
