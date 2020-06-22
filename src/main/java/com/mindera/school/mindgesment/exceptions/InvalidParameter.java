package com.mindera.school.mindgesment.exceptions;

/**
 * InvalidParameter class extends RuntimeException and
 * exists to be used when there was an invalid parameter.
 */
public class InvalidParameter extends RuntimeException {

    public InvalidParameter(String parameter, String mustBe) {
        super(String.format("Parameter %s is invalid. The parameter must be %s", parameter, mustBe));
    }
}
