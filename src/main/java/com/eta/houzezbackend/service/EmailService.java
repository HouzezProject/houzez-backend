package com.eta.houzezbackend.service;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.SendTemplatedEmailRequest;
import com.amazonaws.services.simpleemail.model.SendTemplatedEmailResult;
import com.eta.houzezbackend.config.MailConfig;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmailService {
    private static MailConfig mailConfig = new MailConfig();
    private static AmazonSimpleEmailService ses = mailConfig.amazonSimpleEmailService();
    private static String senderEmail = "zhiyu_zhao7@163.com";
    private static String templateName = "registerTemplate";
    private static String receiverEmail = "zachary.zhao7@gmail.com";

    private static String templateData = "{ \"link\":\"www.google.com\",\"name\":\"Zachary\" }";

    public static void sendEmail() {
        Destination destination = new Destination();
        List<String> toAddresses = new ArrayList<String>();
        toAddresses.add(receiverEmail);
        destination.setToAddresses(toAddresses);
        SendTemplatedEmailRequest templatedEmailRequest = new SendTemplatedEmailRequest();
        templatedEmailRequest.withDestination(destination);
        templatedEmailRequest.withTemplate(templateName);
        templatedEmailRequest.withTemplateData(templateData);
        templatedEmailRequest.withSource(senderEmail);
        SendTemplatedEmailResult templatedEmailResult = ses.sendTemplatedEmail(templatedEmailRequest);
        System.out.println(templatedEmailResult.getMessageId());
    }

}
