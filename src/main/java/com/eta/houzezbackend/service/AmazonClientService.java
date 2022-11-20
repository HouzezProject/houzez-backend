package com.eta.houzezbackend.service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.eta.houzezbackend.util.AmazonProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AmazonClientService {
    private AmazonS3 s3client;

    private final AmazonProperties amazonProperties;
    @PostConstruct
    private void initializeAmazon(){
        AWSCredentials credentials=new BasicAWSCredentials(this.amazonProperties.getAccessKey(),this.amazonProperties.getSecretKey());
        this.s3client = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(credentials)).withRegion(Regions.AP_SOUTHEAST_2).build();
    }

    public File convertMultiPartToFile(MultipartFile multipart, String fileName) throws IllegalStateException, IOException {
        File convFile = new File(System.getProperty("java.io.tmpdir")+fileName);
        multipart.transferTo(convFile);
        return convFile;
    }

    private void uploadFileToS3bucket (String fileName, File file){
        s3client.putObject(new PutObjectRequest(amazonProperties.getBucketName(), fileName, file).withCannedAcl(CannedAccessControlList.PublicRead));
    }

    public List<String> uploadMultipleFile (List<MultipartFile> multipartFiles){
        return multipartFiles.stream().map(this::uploadSingleFile).toList();
    }

    public String uploadSingleFile(MultipartFile multipartFile){
            String fileUrl;
            try {
                String fileName = UUID.randomUUID() +".jpeg";
                File file = convertMultiPartToFile(multipartFile, fileName);
                fileUrl = amazonProperties.getEndpointUrl() + "/" + fileName;
                uploadFileToS3bucket(fileName, file);
                Files.delete(Path.of(file.getPath()));
            } catch (Exception e) {
                return "upload failed, please try again";
            }
            return fileUrl;
    }

}
