package org.vacation.back;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.vacation.back.dto.CodeEnum;
import org.vacation.back.dto.CommonResponse;
import org.vacation.back.exception.CommonException;
import org.vacation.back.exception.ErrorCode;
import org.vacation.back.service.TestService;

@SpringBootTest
class BackApplicationTests {

	@Autowired
	TestService testService;

	@Test
	void contextLoads() {
		CommonResponse commonResponse = new CommonResponse(ErrorCode.DUPLICATED_MEMBER_NAME);


		try{
			testService.test();
		}catch (CommonException e){
			System.out.println(e.getErrorCode().getHttpStatus().value());
			System.out.println(e.getMessage());
			/*
			* 409
				Member name is duplicatged. 테스트임
			*
			* */
		}
	}

}
