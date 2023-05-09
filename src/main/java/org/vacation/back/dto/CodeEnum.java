package org.vacation.back.dto;

public enum CodeEnum {
    SUCCESS(200,"success"),
    UNKNOWN_ERROR(500, "An unknown error has occurred."),
    INVALID_ARGUMENT(400, "알 수 없는 값이 넘어왔습니다."),
    NOT_FOUND(404, "The requested resource was not found."),
    UNAUTHORIZED(401, "The operation requires authentication."),
    FORBIDDEN(403, "The operation is forbidden."),
    INTERNAL_SERVER_ERROR(500, "An internal server error has occurred."),
    LOGIN_FAIL(401,"비밀번호 혹은 아이디가 틀렸습니다");


    private final int code;
    private final String message;

    private CodeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
