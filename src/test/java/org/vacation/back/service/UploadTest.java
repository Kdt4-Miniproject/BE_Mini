package org.vacation.back.service;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.vacation.back.utils.S3Uploder;

import java.io.IOException;

@SpringBootTest
public class UploadTest {

    @Autowired
    private S3Uploder s3Uploder;

    @Autowired
    private FileService fileService;


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
    void postTest() throws IOException {
        // given
        String temp = "5ca67270-07dc-4dc7-adf1-69a31a454a00_testData2.jpg";
        // when
        fileService.S3ToTemp(temp);
        // then
    }
}
