package com.example.bookingclinic.doctor.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.bookingclinic.user.entity.*;
import com.example.bookingclinic.adminsystem.controller.system;
import com.example.bookingclinic.doctor.repository.AdviseRepository;
import com.example.bookingclinic.user.repository.AdvisoryRepository;
import com.example.bookingclinic.user.repository.UDoctorReponsitory;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdviseService {

    private final AdvisoryRepository advisoryRepository;
    private final UDoctorReponsitory uDoctorReponsitory;

    // lấy danh sách câu hỏi
    public List<Advisory> getListPending() {
        return advisoryRepository.findByTrangThaiTraLoiFalseOrderByThoiGianHoiAsc();
    }
    
    // Bác sĩ trả lời tư vấn
    @Transactional
    public Advisory replyAdvise(String maTuVan, String maBacSi, String noiDungTraLoi) {
        System.out.print(maTuVan);
        Advisory advise = advisoryRepository.findByMaTuVan(maTuVan);
        // if(advise == null){
            
        // }
        System.out.print(advise.getCauHoi());
        if (Boolean.TRUE.equals(advise.getTrangThaiTraLoi())) {
            throw new RuntimeException("Câu hỏi này đã được một bác sĩ khác trả lời rồi!");
        }
        UDoctor doctor = uDoctorReponsitory.findById(maBacSi).orElse(null);

        advise.setCauTraLoi(noiDungTraLoi);
        advise.setDoctor(doctor);
        advise.setThoiGianTraLoi(LocalDateTime.now());
        advise.setTrangThaiTraLoi(true); 

        return advisoryRepository.save(advise);
    }
}