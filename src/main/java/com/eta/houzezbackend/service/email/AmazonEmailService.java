package com.eta.houzezbackend.service.email;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.SendTemplatedEmailRequest;
import com.eta.houzezbackend.util.SystemParam;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@ConditionalOnProperty(name = "system-param.aws-active",havingValue = "true")
public record AmazonEmailService (AmazonSimpleEmailService amazonSimpleEmailService, SystemParam systemParam) implements EmailService {
    private static final String templateName = "registerTemplate";
    private static final String forgetPasswordTemplateName = "resetpasswordTemplate";

    @Override
    public void sendEmail(String receiverEmail, String link, String info) {
        String senderEmail = systemParam.getSenderEmail();
        String templateData = "{ \"link\":\"" + link + "\"}";
        Destination destination = new Destination();
        List<String> toAddresses = List.of(receiverEmail);
        destination.setToAddresses(toAddresses);
        SendTemplatedEmailRequest templatedEmailRequest = new SendTemplatedEmailRequest();
        if(info.equals("register")) {
            templatedEmailRequest.withDestination(destination)
                    .withTemplate(templateName)
                    .withTemplateData(templateData)
                    .withSource(senderEmail);
        }else{
            templatedEmailRequest.withDestination(destination)
                    .withTemplate(forgetPasswordTemplateName)
                    .withTemplateData(templateData)
                    .withSource(senderEmail);
        }

        amazonSimpleEmailService.sendTemplatedEmail(templatedEmailRequest);
    }
}
