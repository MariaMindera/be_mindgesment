package com.mindera.school.mindgesment.http.advices;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import com.mindera.school.mindgesment.exceptions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


class ExceptionHandlerAdviceTest {

    private ListAppender<ILoggingEvent> listAppender;

    private ExceptionHandlerAdvice subject;

    @BeforeEach
    void setUp() {
        this.subject = new ExceptionHandlerAdvice();

        Logger logger = (Logger) LoggerFactory.getLogger(ExceptionHandlerAdvice.class);
        listAppender = new ListAppender<>();
        listAppender.start();
        logger.addAppender(listAppender);
    }

    @Test
    void addError() {
        //given
        var throwable = new Throwable("Some Reason");
        var error = new AddError("Test", throwable);

        //when
        var response = subject.addError(error);

        //then
        assertEquals("BAD_ADD_REQUEST", response.getErrorCode());
        assertEquals(error.getMessage(), response.getMessage());
        assertEquals(error.getCause().getMessage(), response.getReason());

        assertEquals(error.getMessage(), listAppender.list.get(0).getMessage());
        assertEquals(Level.ERROR, listAppender.list.get(0).getLevel());
    }

    @Test
    void balanceExceededTotalWish() {
        //given
        var error = new BalanceExceededTotalWish(10L, 10L);

        //when
        var response = subject.balanceExceededTotalWish(error);

        //then
        assertEquals("BAD_ADD_BALANCE_REQUEST", response.getErrorCode());
        assertEquals(error.getMessage(), response.getMessage());
        assertNull(response.getReason());

        assertEquals(error.getMessage(), listAppender.list.get(0).getMessage());
        assertEquals(Level.ERROR, listAppender.list.get(0).getLevel());
    }

    @Test
    void deleteError() {
        //given
        var throwable = new Throwable("Some Reason");
        var error = new DeleteError("Test", throwable);

        //when
        var response = subject.deleteError(error);

        //then
        assertEquals("BAD_DELETE_REQUEST", response.getErrorCode());
        assertEquals(error.getMessage(), response.getMessage());
        assertEquals(error.getCause().getMessage(), response.getReason());

        assertEquals(error.getMessage(), listAppender.list.get(0).getMessage());
        assertEquals(Level.ERROR, listAppender.list.get(0).getLevel());
    }

    @Test
    void dontHaveUserInformation() {
        //given
        var error = new DontHaveUserInformation();

        //when
        var response = subject.dontHaveUserInformation(error);

        //then
        assertEquals("BAD_REQUEST", response.getErrorCode());
        assertEquals(error.getMessage(), response.getMessage());
        assertNull(response.getReason());

        assertEquals(error.getMessage(), listAppender.list.get(0).getMessage());
        assertEquals(Level.ERROR, listAppender.list.get(0).getLevel());
    }

    @Test
    void editError() {
        //given
        var throwable = new Throwable("Some Reason");
        var error = new EditError("Test", throwable);

        //when
        var response = subject.editError(error);

        //then
        assertEquals("BAD_EDIT_REQUEST", response.getErrorCode());
        assertEquals(error.getMessage(), response.getMessage());
        assertEquals(error.getCause().getMessage(), response.getReason());

        assertEquals(error.getMessage(), listAppender.list.get(0).getMessage());
        assertEquals(Level.ERROR, listAppender.list.get(0).getLevel());
    }

    @Test
    void invalidPage() {
        //given
        var error = new InvalidPage(1);

        //when
        var response = subject.invalidPage(error);

        //then
        assertEquals("BAD_PAGE_REQUEST", response.getErrorCode());
        assertEquals(error.getMessage(), response.getMessage());
        assertNull(response.getReason());

        assertEquals(error.getMessage(), listAppender.list.get(0).getMessage());
        assertEquals(Level.ERROR, listAppender.list.get(0).getLevel());
    }

    @Test
    void invalidToken() {
        //given
        var error = new InvalidToken();

        //when
        var response = subject.invalidToken(error);

        //then
        assertEquals("BAD_TOKEN_REQUEST", response.getErrorCode());
        assertEquals(error.getMessage(), response.getMessage());
        assertNull(response.getReason());

        assertEquals(error.getMessage(), listAppender.list.get(0).getMessage());
        assertEquals(Level.ERROR, listAppender.list.get(0).getLevel());
    }

    @Test
    void invalidParameter() {
        //given
        var error = new InvalidParameter("Test", "Test");

        //when
        var response = subject.invalidParameter(error);

        //then
        assertEquals("BAD_PARAMETER_REQUEST", response.getErrorCode());
        assertEquals(error.getMessage(), response.getMessage());
        assertNull(response.getReason());

        assertEquals(error.getMessage(), listAppender.list.get(0).getMessage());
        assertEquals(Level.ERROR, listAppender.list.get(0).getLevel());
    }

    @Test
    void notExits() {
        //given
        var error = new NotExits("Test", "TestId");

        //when
        var response = subject.notExits(error);

        //then
        assertEquals("NOT_FOUND", response.getErrorCode());
        assertEquals(error.getMessage(), response.getMessage());
        assertNull(response.getReason());

        assertEquals(error.getMessage(), listAppender.list.get(0).getMessage());
        assertEquals(Level.ERROR, listAppender.list.get(0).getLevel());
    }

    @Test
    void invalidRegister() {
        //given
        var throwable = new Throwable("Some Reason");
        var error = new RegisterError(throwable);

        //when
        var response = subject.invalidRegister(error);

        //then
        assertEquals("BAD_REGISTER_REQUEST", response.getErrorCode());
        assertEquals(error.getMessage(), response.getMessage());
        assertEquals(error.getCause().getMessage(), response.getReason());

        assertEquals(error.getMessage(), listAppender.list.get(0).getMessage());
        assertEquals(Level.ERROR, listAppender.list.get(0).getLevel());
    }

    @Test
    void userNotFound() {
        //given
        var error = new UserNotExists("Test");

        //when
        var response = subject.userNotFound(error);

        //then
        assertEquals("USER_NOT_FOUND", response.getErrorCode());
        assertEquals(error.getMessage(), response.getMessage());
        assertNull(response.getReason());

        assertEquals(error.getMessage(), listAppender.list.get(0).getMessage());
        assertEquals(Level.ERROR, listAppender.list.get(0).getLevel());
    }
}