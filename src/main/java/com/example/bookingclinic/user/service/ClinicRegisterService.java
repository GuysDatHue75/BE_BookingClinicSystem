package com.example.bookingclinic.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bookingclinic.user.entity.ClinicRegister;
import com.example.bookingclinic.user.repository.ClinicRegisterRepository;

@Service
public class ClinicRegisterService {
    
    @Autowired
    private ClinicRegisterRepository clinicRegisterRepository;

    public String registerClinic(ClinicRegister clinic) {
        if(clinic.getMaPhongKham() == null){
            clinic.setMaPhongKham("PK" + System.currentTimeMillis());
        }
        clinicRegisterRepository.save(clinic);
        return "Đăng ký thành công!";
    }
}
