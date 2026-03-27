package com.example.bookingclinic.user.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.bookingclinic.user.entity.UClinic;
import com.example.bookingclinic.user.repository.UClinicRepository;

@Service
public class UClinicService {
    private UClinicRepository clinicRepository;

    public UClinicService(UClinicRepository clinicRepository){
        this.clinicRepository = clinicRepository;
    }


    public UClinic getClinicDtail(String id){
        return clinicRepository.findById(id).orElse(null);
    }

    public List<UClinic> getAllClinicInCity(String tp){
        return clinicRepository.findByTinhThanhPhoContainingIgnoreCase(tp);
    }

    public void registerClinic(UClinic clinic){
        clinicRepository.save(clinic);
    }
    public List<UClinic> findClinicsByCityAndSpecialty(String tp, String id){
        return clinicRepository.findClinicByCityAndSpecialty(tp, id);
    }

     public List<UClinic> searchClinicWithAI(String name, String city, int limit) {

        List<UClinic> result;

        result = clinicRepository.findBySpecicaltys_TenChuyenKhoaContainingIgnoreCaseAndTinhThanhPhoContainingIgnoreCase(name,"Thừa Thiên Huế");
        
        return result.stream().limit(limit).toList();
    }
}
