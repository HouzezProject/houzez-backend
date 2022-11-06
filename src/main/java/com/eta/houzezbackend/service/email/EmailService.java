package com.eta.houzezbackend.service.email;


public interface EmailService {
    void sendEmail(String receiverEmail, String link, String info);
}
