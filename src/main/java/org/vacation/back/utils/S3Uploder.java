package org.vacation.back.utils;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
@RequiredArgsConstructor
public class S3Uploder {

        private final AmazonS3Client amazonS3Client;

        @Value("${cloud.aws.s3.bucket}")
        private String bucketName;

        public String upload(String filePath) throws RuntimeException{
            File targetFile = new File(filePath);

            String uploadImageUrl = putS3(targetFile,targetFile.getName());

            removeOriginalFile(targetFile);

            return uploadImageUrl;

        }

        private String putS3(File uploadFile,String fileName) throws RuntimeException{
            amazonS3Client.putObject(new PutObjectRequest(bucketName,fileName,uploadFile).withCannedAcl(CannedAccessControlList.PublicRead));

            return amazonS3Client.getUrl(bucketName,fileName).toString();
        }

        private void removeOriginalFile(File targetFile){
            if(targetFile.exists() && targetFile.delete()){
                return;
            }
        }

        private void removeS3File(String fileName){
            final DeleteObjectRequest deleteObjectRequest =
                    new DeleteObjectRequest(bucketName,fileName);
            amazonS3Client.deleteObject(deleteObjectRequest);
        }

}


