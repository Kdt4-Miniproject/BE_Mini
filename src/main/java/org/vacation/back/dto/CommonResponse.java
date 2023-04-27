package org.vacation.back.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.vacation.back.exception.ErrorCode;

@Getter
@AllArgsConstructor
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommonResponse<T> {
    private Integer status;
    private String message;
    private T data;


    @Builder
    public CommonResponse(CodeEnum codeEnum,T data){
        setData(data);
        setStatus(codeEnum.getCode());
        setMessage(codeEnum.getMessage());
    }


    public CommonResponse(ErrorCode errorCode){
        setStatus(errorCode.getHttpStatus().value());
        setMessage(errorCode.getMessage());
    }

}
