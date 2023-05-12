package org.vacation.back.service;


import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.vacation.back.common.VacationStatus;
import org.vacation.back.domain.Member;
import org.vacation.back.domain.Vacation;
import org.vacation.back.repository.MemberRepository;
import org.vacation.back.repository.VacationRepository;
import org.vacation.back.utils.S3Uploder;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
public class UploadTest {

    @Autowired
    private S3Uploder s3Uploder;

    @Autowired
    private FileService fileService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private VacationRepository vacationRepository;


    @Autowired
    MemberService memberService;

    @Test
    @DisplayName("")
    void test() {
        // given
          String filePath = "C:\\PROJECT\\api\\test.jpg";
          String uploadName = s3Uploder.upload(filePath);
        // when

        // then
    }

    @Test
    @DisplayName("")
    @Transactional
    void postTest() throws IOException {
        // given
        // when
        memberService.vacationFindByDepartment("인사");



    }


}
