package br.com.fingestio.api.utils;

public enum ErrorCode {
    VALIDATION_ERROR("VALIDATION_ERROR"),
    MISSING_REQUEST_BODY("MISSING_REQUEST_BODY"),
    AUTHENTICATION_ERROR("AUTHENTICATION_ERROR"),
    INVALID_PASSWORD("INVALID_PASSWORD"),
    INTERNAL_SERVER_ERROR("INTERNAL_SERVER_ERROR"),
    BAD_REQUEST("BAD_REQUEST"),
    NOT_FOUND("NOT_FOUND");

    private final String code;

    ErrorCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String toString() {
        return code;
    }
}