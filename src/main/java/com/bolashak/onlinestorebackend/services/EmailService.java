package com.bolashak.onlinestorebackend.services;

public interface EmailService {
    void sendEmail(String toEmail, String subject, String body);
}
