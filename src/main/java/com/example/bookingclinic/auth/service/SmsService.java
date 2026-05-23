package com.example.bookingclinic.auth.service;

import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Value;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

import jakarta.annotation.PostConstruct;

@Service
public class SmsService {
    @Value("${twilio.account.sid}")
    private String accountSid;

    @Value("${twilio.auth.token}")
    private String authToken;

    @Value("${twilio.phone.number}")
    private String twilioPhone;

    @PostConstruct
    public void init() {
        Twilio.init(accountSid, authToken);
    }

    private String formatPhoneNumber(String phone) {

        if (phone.startsWith("0")) {
            return "+84" + phone.substring(1);
        }

        return phone;
    }

    public void sendSms(String toPhone, String otp) {
        String formattedPhone = formatPhoneNumber(toPhone);
        Message.creator(
                new com.twilio.type.PhoneNumber(formattedPhone),
                new com.twilio.type.PhoneNumber(twilioPhone),
                "Mã OTP của bạn là: " + otp).create();
    }

}
