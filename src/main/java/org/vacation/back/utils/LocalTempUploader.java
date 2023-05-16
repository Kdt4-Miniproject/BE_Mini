package org.vacation.back.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.vacation.back.common.FileDTO;
import org.vacation.back.exception.FileUploadFailException;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Component
public class LocalTempUploader {



    @Value("${fild.path}")
    private String path;

    public List<String> uploadLocal(FileDTO fileDTO){
        if(!fileDTO.getFileNames().isEmpty()){
            final List<String> list = new ArrayList<>();

            fileDTO.getFileNames().forEach(multipartFile -> {
                String uuid = UUID.randomUUID().toString();
                Path savePath = Paths.get(path,uuid+"_"+multipartFile.getOriginalFilename());

                try{
                    multipartFile.transferTo(savePath);

                    list.add(savePath.toFile().getAbsolutePath());
                } catch (IOException e) {
                    throw new FileUploadFailException();
                }
            });
            return list;
        }
        return null;
    }
    public List<String> uploadTempLocal(FileDTO fileDTO){
        if(!fileDTO.getFileNames().isEmpty()){
            final List<String> list = new ArrayList<>();

            fileDTO.getFileNames().forEach(multipartFile -> {
                String uuid = UUID.randomUUID().toString();
                Path savePath = Paths.get(path,uuid+"_"+multipartFile.getOriginalFilename());

                try{
                    multipartFile.transferTo(savePath);

                    list.add(savePath.getFileName().toString());
                } catch (IOException e) {
                    throw new FileUploadFailException();
                }
            });
            return list;
        }
        return null;
    }


}
