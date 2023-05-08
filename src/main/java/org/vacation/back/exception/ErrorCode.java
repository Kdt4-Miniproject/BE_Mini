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
    ACCESS_RESOURCES_WITHOUT_PERMISSION(HttpStatus.FORBIDDEN,"Attempt to access resources without permission"),
    ID_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 ID의 정보를 찾을 수 없습니다."),
    DUPLICATED_START(HttpStatus.BAD_REQUEST, "신청한 연차 시작날짜가 이미 존재합니다.");
    private HttpStatus httpStatus;

    private String message;
}
