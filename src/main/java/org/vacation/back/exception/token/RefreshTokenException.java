package org.vacation.back.exception.token;

import com.google.gson.Gson;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.vacation.back.dto.CodeEnum;
import org.vacation.back.dto.CommonResponse;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RefreshTokenException extends RuntimeException{

    private ErrorCase errorCase;

    public enum ErrorCase{
        NO_ACCESS, BAD_ACCESS, NO_REFRESH, OLD_REFRESH, BAD_REFRESH
    }

    public RefreshTokenException(ErrorCase errorCase){
        super(errorCase.name());
        this.errorCase =errorCase;      //new RefreshTokenException(ErrorCase.NO_ACCESS) 하면 NO_ACCESS가 Runtime으로 넘어감
    }


    public void sendResponseError(HttpServletResponse response){
        response.setStatus(HttpStatus.UNAUTHORIZED.value());    //인증 실패상태를 보냄
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        Gson gson = new Gson();

        String responseStr = gson.toJson(
                CommonResponse.builder().codeEnum(CodeEnum.UNAUTHORIZED).data(false)
        );

        try{
            response.getWriter().println(responseStr);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
