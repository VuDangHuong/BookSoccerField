package com.example.bookingsoccerfield.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendPasswordResetEmail(String toEmail, String resetToken) {
        String resetLink = "http://127.0.0.1:5500/pages/reset-password.html?token=" + resetToken;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Đặt lại mật khẩu - Booking Soccer Field");
        message.setText("Nhấn vào liên kết sau để đặt lại mật khẩu:\n" + resetLink + "\n\nNếu bạn không yêu cầu, hãy bỏ qua email này.");

        mailSender.send(message);
    }
}
