package com.eta.houzezbackend.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.eta.houzezbackend.util.SystemParam;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class MailConfig {
    private final SystemParam systemParam;
    @Bean
    @ConditionalOnProperty(name = "system-param.aws-active",havingValue = "true")
    public AmazonSimpleEmailService amazonSimpleEmailService() {
        String awsAccessKey = systemParam.getAWS_ACCESS_KEY();
        String awsSecretKey = systemParam.getAWS_SECRET_KEY();
        return AmazonSimpleEmailServiceClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(
                        new BasicAWSCredentials(
                               awsAccessKey,awsSecretKey)))
                .withRegion(Regions.AP_SOUTHEAST_2)
                .build();
    }

}
