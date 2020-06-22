package com.mindera.school.mindgesment.exceptions;

/**
 * DontHaveUserInformation class extends RuntimeException and
 * exists to be used when don't have the necessary information of a user.
 */
public class DontHaveUserInformation extends RuntimeException {

    public DontHaveUserInformation() {
        super("Don't have the necessary information of user.");
    }
}
