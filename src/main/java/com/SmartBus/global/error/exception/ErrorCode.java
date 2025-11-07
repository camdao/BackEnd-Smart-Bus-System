package com.SmartBus.global.error.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    SAMPLE_ERROR(HttpStatus.BAD_REQUEST, "Sample Error Message"),

    // Common
    METHOD_ARGUMENT_TYPE_MISMATCH(HttpStatus.BAD_REQUEST, "The requested value type is incorrect, causing binding failure."),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "The HTTP method is not supported."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Server error, please contact the administrator."),

    // Member
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "The member could not be found."),

    // Security
    PASSWORD_NOT_MATCHES(HttpStatus.UNAUTHORIZED, "The password does not match."),

    //Schedule
    SCHEDULE_NOT_FOUND(HttpStatus.NOT_FOUND, "The schedule could not be found."),

    //Bus
    BUS_NOT_FOUND(HttpStatus.NOT_FOUND, "The bus could not be found."),
    ;

    private final HttpStatus status;
    private final String message;
}