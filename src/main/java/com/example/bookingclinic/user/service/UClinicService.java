package com.example.bookingclinic.user.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public Page<UClinic> getAllClinicInCity(String tp, Pageable pageable) {
        return clinicRepository.findByTinhThanhPhoContainingIgnoreCase(tp, pageable);
    }

    public void registerClinic(UClinic clinic){
        clinicRepository.save(clinic);
    }
    public Page<UClinic> findClinicsByCityAndSpecialty(String tp, String id, Pageable pageable) {
        return clinicRepository.findClinicByCityAndSpecialty(tp, id, pageable);
    }

    public List<UClinic> searchClinicWithAI(String specialty, String aiCity, String currentCity, int limit) {
        String finalCity = (aiCity == null || aiCity.isEmpty() || aiCity.equalsIgnoreCase("all")
                || aiCity.equalsIgnoreCase("value"))
                        ? currentCity
                        : aiCity;

        List<UClinic> result = clinicRepository
                .findBySpecicaltys_TenChuyenKhoaContainingIgnoreCaseAndTinhThanhPhoContainingIgnoreCase(
                        specialty,
                        finalCity);
        return result.stream().limit(limit).toList();
    }

}
