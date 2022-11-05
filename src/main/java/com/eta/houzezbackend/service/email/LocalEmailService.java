package com.eta.houzezbackend.service.email;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(name = "system-param.aws-active",havingValue = "false")
public class LocalEmailService implements EmailService{
    @Override
    public void sendEmail(String receiverEmail, String link, String info) {
//      Sending email function is disabled in the local environment
    }
}
