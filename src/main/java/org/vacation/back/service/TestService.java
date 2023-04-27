package org.vacation.back.service;


import org.springframework.stereotype.Service;
import org.vacation.back.exception.CommonException;
import org.vacation.back.exception.ErrorCode;

@Service
public class TestService {


    public void test(){
        String str = "테스트 에러";
        throw new CommonException(ErrorCode.DUPLICATED_MEMBER_NAME,String.format("%s ",str));
    }
}
