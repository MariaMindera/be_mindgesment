package com.mindera.school.mindgesment.http.models;

/**
 * ApiError class exists to allow other custom exceptions to be created by implementing this class.
 * <p>
 * This project uses Exceptions to define http status code of the responses, thus this class ensures that all
 * exceptions that will be used to define the response status code will have a error code, message and a reason defined.
 */
public class ApiError {

    private final String errorCode;

    private final String message;

    private final String reason;

    public ApiError(String errorCode, String message, String reason) {
        this.errorCode = errorCode;
        this.message = message;
        this.reason = reason;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }

    public String getReason() {
        return reason;
    }

    @Override
    public String toString() {
        return "ApiError{" +
                "errorCode='" + errorCode + '\'' +
                ", message='" + message + '\'' +
                ", error=" + reason +
                '}';
    }
}
