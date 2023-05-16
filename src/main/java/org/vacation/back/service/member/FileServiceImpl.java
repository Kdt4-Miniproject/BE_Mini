package org.vacation.back.service.member;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.vacation.back.common.FileDTO;
import org.vacation.back.common.FileResponseData;
import org.vacation.back.exception.FileNotFoundException;
import org.vacation.back.exception.S3UploadException;
import org.vacation.back.service.FileService;
import org.vacation.back.utils.LocalTempUploader;
import org.vacation.back.utils.S3Uploder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Component
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {
    private final LocalTempUploader localTempUploader;
    private final S3Uploder s3Uploader;



    @Value("${fild.path}")
    private String path;

    @Override
    public List<String> upload(FileDTO fileDTO) {

        List<MultipartFile> files = fileDTO.getFileNames() ;
        List<String> uploadeFilePaths = new ArrayList<>();

        if(fileDTO == null || files.isEmpty()){
            return null;
        }
        files.forEach(multipartFile -> {
            uploadeFilePaths.addAll(localTempUploader.uploadLocal(fileDTO));
        });
        List<String> s3Paths =
                uploadeFilePaths.stream().map(s3Uploader::upload).toList();

        return s3Paths;

    }

    @Override
    public List<String> tempUpload(FileDTO fileDTO) {

        List<MultipartFile> files = fileDTO.getFileNames();
        List<String> uploadeFilePaths = new ArrayList<>();

        files.forEach(multipartFile -> {
            uploadeFilePaths.addAll(localTempUploader.uploadTempLocal(fileDTO));
        });

        return uploadeFilePaths;
    }

    @Override
    public FileResponseData viewFile(String fileName) {

        Resource resource = new FileSystemResource( path+File.separator+fileName);

        String resourceName = resource.getFilename();
        HttpHeaders headers = new HttpHeaders();

        try{
            headers.add("Content-Type", Files.probeContentType(resource.getFile().toPath()));
        } catch (IOException e){
            throw new FileNotFoundException();
        }
        return FileResponseData.builder().headers(headers).resource(resource).build();
    }


    public String S3ToTemp(String fileName) throws IOException {
        Resource resource = new FileSystemResource( path+File.separator+fileName);
        if(!resource.exists()) throw new RuntimeException();

        try{
            return s3Uploader.upload(resource.getFile().getAbsolutePath());
        }catch (Exception e){
           throw new S3UploadException();
        }

    }
}
