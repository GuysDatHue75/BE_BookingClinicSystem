package com.example.bookingclinic.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender javaMailSender;  

    public void sendOtp(String toMail, String otp){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toMail);
        message.setSubject("Mã OTP Để đổi mật khẩu trong hệ thống Booking-Clinic-System của bạn là: ");
        message.setText("Mã OTP là: " + otp);
        javaMailSender.send(message);
    }
}
