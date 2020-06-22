package com.mindera.school.mindgesment.exceptions;

/**
 * InvalidPage class extends RuntimeException and
 * exists to be used when the page is invalid.
 */
public class InvalidPage extends RuntimeException {

    public InvalidPage(Integer page) {
        super(String.format("Invalid Page. No data in this page: %d.", page));
    }
}
