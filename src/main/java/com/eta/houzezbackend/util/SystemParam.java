package com.eta.houzezbackend.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@Getter
public class SystemParam {
    @Value("${system-param.secret-key}")
    private String SECRET_KEY;

    @Value("${system-param.aws-access-key}")
    private String AWS_ACCESS_KEY;

    @Value("${system-param.aws-secret-key}")
    private String AWS_SECRET_KEY;

    @Value("${system-param.sender-email}")
    private String SENDER_EMAIL;

    @Value("${system-param.base-url}")
    private String BASE_URL;

    @Value("${system-param.aws-active}")
    private String AWS_ACTIVE;

}
