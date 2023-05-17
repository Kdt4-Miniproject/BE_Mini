package org.vacation.back.service;

import org.springframework.core.io.Resource;
import org.vacation.back.common.FileDTO;
import org.vacation.back.common.FileResponseData;

import java.io.IOException;
import java.util.List;

public interface FileService {

    public List<String> upload(FileDTO fileDTO);

    public List<String> tempUpload(FileDTO fileDTO);

    public FileResponseData viewFile(String fileName);
    public String S3ToTemp(String fileName) throws IOException;
}
