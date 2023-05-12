package org.vacation.back.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
@AllArgsConstructor
public enum ErrorCode {
    /**
     * 추가될 에러가 생기면 아래 양식처럼 알아볼 수 있는 이름과 HttpStatus 랑 메시지를 선택해야합니다.
     * */


    DUPLICATED_MEMBER_NAME(HttpStatus.BAD_REQUEST,"Member name is duplicatged"),
    ID_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 ID의 정보를 찾을 수 없습니다."),
    DUPLICATED_START(HttpStatus.BAD_REQUEST, "신청한 연차 시작날짜가 이미 존재합니다."),
    DTO_IS_NULL(HttpStatus.BAD_REQUEST, "입력값 확인해주세요."),
    ALREADY_DELETED_VACATION(HttpStatus.BAD_REQUEST, "이미 삭제된 연차입니다."),
    ALREADY_OK_VACATION(HttpStatus.BAD_REQUEST, "이미 승인된 연차입니다."),
    ALREADY_REJECTED_VACATION(HttpStatus.BAD_REQUEST, "이미 반려된 연차입니다."),
    ALREADY_DELETED_DUTY(HttpStatus.BAD_REQUEST, "이미 삭제된 당직입니다."),
    ALREADY_OK_DUTY(HttpStatus.BAD_REQUEST, "이미 승인된 당직입니다."),
    ALREADY_REJECTED_DUTY(HttpStatus.BAD_REQUEST, "이미 거절된 당직입니다."),
    NO_RESULT(HttpStatus.BAD_REQUEST, "당직을 신청한 사람이 없습니다."),
    NOTFOUND_ID(HttpStatus.BAD_REQUEST, "ID를 찾을 수 없습니다."),
    NOTFOUND_USERNAME(HttpStatus.BAD_REQUEST, "유저를 찾을 수 없습니다."),
    EXIST_DUTY(HttpStatus.BAD_REQUEST, "이미 당직이 존재합니다."),

    ACCESS_RESOURCES_WITHOUT_PERMISSION(HttpStatus.FORBIDDEN,"권한이 없습니다.");
    private HttpStatus httpStatus;

    private String message;
}
