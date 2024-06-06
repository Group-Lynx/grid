package com.grid.utils;

public enum ErrorResponse {
    STUDENT_ALREADY_EXISTS("409 Conflict","Student already exists"),
    STUDENT_NOT_FOUND("404 Not Found","Student not found"),
    TEACHER_ALREADY_EXISTS("409 Conflict","Teacher already exists"),
    TEACHER_NOT_FOUND("404 Not Found","Teacher not found"),
    CLASS_NOT_FOUND("404 Not Found","Class not found"),
    MAIL_NOT_FOUND("404 Not Found","Mail not found"),
    CODE_NOT_FOUND("404 Not Found","Code not found");
    private final String errorCode;
    private final String message;

    ErrorResponse(String errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }
}
