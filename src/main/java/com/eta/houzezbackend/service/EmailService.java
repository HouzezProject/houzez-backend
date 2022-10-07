package com.eta.houzezbackend.service;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.SendTemplatedEmailRequest;
import com.amazonaws.services.simpleemail.model.SendTemplatedEmailResult;
import com.eta.houzezbackend.util.SystemParam;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public record EmailService(AmazonSimpleEmailService amazonSimpleEmailService, SystemParam systemParam) {
    private static final String templateName = "registerTemplate";

    public void sendEmail(String receiverEmail, String link) {
        String senderEmail = systemParam.getSENDER_EMAIL();
        String templateData = "{ \"link\":\"" + link + "\"}";
        Destination destination = new Destination();
        List<String> toAddresses = List.of(receiverEmail);
        destination.setToAddresses(toAddresses);
        SendTemplatedEmailRequest templatedEmailRequest = new SendTemplatedEmailRequest();
        templatedEmailRequest.withDestination(destination)
                .withTemplate(templateName)
                .withTemplateData(templateData)
                .withSource(senderEmail);
        amazonSimpleEmailService.sendTemplatedEmail(templatedEmailRequest);
    }
}
