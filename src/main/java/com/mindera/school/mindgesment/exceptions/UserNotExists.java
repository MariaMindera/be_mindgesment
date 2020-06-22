package com.mindera.school.mindgesment.exceptions;

/**
 * UserNotExists class extends RuntimeException and
 * exists to be used when a user not exists with that username.
 */
public class UserNotExists extends RuntimeException {

    public UserNotExists(String username) {
        super(String.format("User with username=%s not found.", username));
    }
}
