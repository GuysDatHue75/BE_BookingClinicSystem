package com.example.bookingclinic.auth.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.stereotype.Service;

@Service
public class OTPService {
    private Map<String, String> otpCache = new HashMap<>();

    public Map<String, String> renderOTP(String phone){
        String otp = String.valueOf(new Random().nextInt(899999) + 100000);
        otpCache.put(phone, otp);
        return otpCache;
    }

    public String getOtp(String phone){
        return otpCache.get(phone);
    }

    public void clearOtp(String phone){
        otpCache.remove(phone);
    }
}
