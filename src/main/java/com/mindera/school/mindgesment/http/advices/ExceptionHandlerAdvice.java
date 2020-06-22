package com.mindera.school.mindgesment.http.advices;

import com.mindera.school.mindgesment.exceptions.*;
import com.mindera.school.mindgesment.http.models.ApiError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * ExceptionHandlerAdvice class exists to handle custom errors.
 */
@RestControllerAdvice
public class ExceptionHandlerAdvice {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandlerAdvice.class);

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(AddError.class)
    public ApiError addError(AddError e) {
        LOGGER.error(e.getMessage(), e.getCause().getMessage());
        return new ApiError("BAD_ADD_REQUEST", e.getMessage(), e.getCause().getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BalanceExceededTotalWish.class)
    public ApiError balanceExceededTotalWish(BalanceExceededTotalWish e) {
        LOGGER.error(e.getMessage());
        return new ApiError("BAD_ADD_BALANCE_REQUEST", e.getMessage(), null);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DeleteError.class)
    public ApiError deleteError(DeleteError e) {
        LOGGER.error(e.getMessage(), e.getCause().getMessage());
        return new ApiError("BAD_DELETE_REQUEST", e.getMessage(), e.getCause().getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DontHaveUserInformation.class)
    public ApiError dontHaveUserInformation(DontHaveUserInformation e) {
        LOGGER.error(e.getMessage());
        return new ApiError("BAD_REQUEST", e.getMessage(), null);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(EditError.class)
    public ApiError editError(EditError e) {
        LOGGER.error(e.getMessage(), e.getCause().getMessage());
        return new ApiError("BAD_EDIT_REQUEST", e.getMessage(), e.getCause().getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidPage.class)
    public ApiError invalidPage(InvalidPage e) {
        LOGGER.error(e.getMessage());
        return new ApiError("BAD_PAGE_REQUEST", e.getMessage(), null);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidToken.class)
    public ApiError invalidToken(InvalidToken e) {
        LOGGER.error(e.getMessage());
        return new ApiError("BAD_TOKEN_REQUEST", e.getMessage(), null);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidParameter.class)
    public ApiError invalidParameter(InvalidParameter e) {
        LOGGER.error(e.getMessage());
        return new ApiError("BAD_PARAMETER_REQUEST", e.getMessage(), null);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotExits.class)
    public ApiError notExits(NotExits e) {
        LOGGER.error(e.getMessage());
        return new ApiError("NOT_FOUND", e.getMessage(), null);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(RegisterError.class)
    public ApiError invalidRegister(RegisterError e) {
        LOGGER.error(e.getMessage(), e.getCause());
        return new ApiError("BAD_REGISTER_REQUEST", e.getMessage(), e.getCause().getMessage());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(Unauthorized.class)
    public ApiError unauthorized(Unauthorized e) {
        LOGGER.error(e.getMessage(), e.getCause());
        return new ApiError("UNAUTHORIZED", e.getMessage(), null);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserNotExists.class)
    public ApiError userNotFound(UserNotExists e) {
        LOGGER.error(e.getMessage(), e.getCause());
        return new ApiError("USER_NOT_FOUND", e.getMessage(), null);
    }
}
