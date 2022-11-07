package com.eta.houzezbackend.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.eta.houzezbackend.util.AmazonProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AmazonClientService {
    private final AmazonS3 s3client;
    private final AmazonProperties amazonProperties;
    private File convertMultiPartToFile (MultipartFile file) throws IOException {
        File convFile= new File((Objects.requireNonNull(file.getOriginalFilename())));
        FileOutputStream fos=new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;

    }

    private String generateFileName(MultipartFile file) {

        return new Date().getTime()+"-"+ Objects.requireNonNull(file.getOriginalFilename()).replace(" ","_");
    }

    private void uploadFileToS3bucket(String fileName, File file){
        s3client.putObject(new PutObjectRequest(amazonProperties.getBucketName(),fileName,file).withCannedAcl(CannedAccessControlList.PublicRead));
    }
    public String uploadFile (MultipartFile multipartFile){
        String fileUrl="";
        try{
            File file=convertMultiPartToFile(multipartFile);
            String fileName=generateFileName(multipartFile);
            fileUrl=amazonProperties.getEndpointUrl()+"/"+amazonProperties.getBucketName()+"/"+fileName;
            uploadFileToS3bucket(fileName,file);
            
        } catch (Exception e) {
            e.printStackTrace();
        }

        return fileUrl;
    }

}
