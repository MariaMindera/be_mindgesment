package com.mindera.school.mindgesment.http.advices;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

import java.time.DateTimeException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ExceptionHandlerControllerTest {

    private ListAppender<ILoggingEvent> listAppender;

    private ExceptionHandlerController subject;

    @BeforeEach
    void setUp() {
        this.subject = new ExceptionHandlerController();

        Logger logger = (Logger) LoggerFactory.getLogger(ExceptionHandlerController.class);
        listAppender = new ListAppender<>();
        listAppender.start();
        logger.addAppender(listAppender);
    }

    @Test
    void handleUnknown() {
        //given
        var throwable = new Throwable("Some Problem");
        var error = new Exception("Test", throwable);

        //when
        var response = subject.handleUnknown(error);

        //then
        assertEquals("INTERNAL_SERVER_ERROR", response.getErrorCode());
        assertEquals(error.getMessage(), response.getMessage());
        assertEquals(error.getCause().getMessage(), response.getReason());

        assertEquals(error.getMessage(), listAppender.list.get(0).getMessage());
        assertEquals(Level.ERROR, listAppender.list.get(0).getLevel());
    }

    @Test
    void handleNullPointerException() {
        //given
        var error = new NullPointerException();

        //when
        var response = subject.handleNullPointerException(error);

        //then
        assertEquals("BAD_REQUEST", response.getErrorCode());
        assertNull(response.getMessage());
        assertNull(response.getReason());

        assertNull(listAppender.list.get(0).getMessage());
        assertEquals(Level.ERROR, listAppender.list.get(0).getLevel());
    }

    @Test
    void dateTimeException() {
        //given
        var throwable = new Throwable("Some Problem");
        var error = new DateTimeException("Test", throwable);

        //when
        var response = subject.dateTimeException(error);

        //then
        assertEquals("BAD_DATE_REQUEST", response.getErrorCode());
        assertEquals(error.getMessage(), response.getMessage());
        assertEquals(error.getCause().getMessage(), response.getReason());

        assertEquals(error.getMessage(), listAppender.list.get(0).getMessage());
        assertEquals(Level.ERROR, listAppender.list.get(0).getLevel());
    }
}