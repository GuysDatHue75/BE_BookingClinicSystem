package com.example.bookingclinic.user.service;


import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bookingclinic.user.entity.ClinicRegister;
import com.example.bookingclinic.user.repository.ClinicRegisterRepository;

@Service
public class ClinicRegisterService {
    @Autowired
    private ClinicRegisterRepository clinicRegisterRepository;

    public String registerClinic(ClinicRegister clinic) {
        try {
            if (clinic.getMaPhongKham() == null || clinic.getMaPhongKham().isEmpty()) {
                clinic.setMaPhongKham("PK" + System.currentTimeMillis());
            }
            clinic.setNgayDangKy(LocalDate.now());

            if (clinic.getTrangThai() == null || clinic.getTrangThai().isEmpty()) {
                clinic.setTrangThai("ChoDuyet");
            }
            clinicRegisterRepository.save(clinic);

            return "Đăng ký thành công!";

        } catch (Exception e) {
            return "Đăng ký thất bại: " + e.getMessage();
        }
    }
}
