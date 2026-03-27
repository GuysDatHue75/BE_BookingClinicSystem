package com.example.bookingclinic.user.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.bookingclinic.user.entity.Advisory;
import com.example.bookingclinic.user.repository.AdvisoryRepository;

@Service
public class AdvisoryService {
    private AdvisoryRepository advisoryRepository;

    public AdvisoryService(AdvisoryRepository advisoryRepository){
        this.advisoryRepository = advisoryRepository;
    }

    public Advisory sendQuessionToClinic(Advisory advisory){
        Advisory newAdvisory = new Advisory();
        newAdvisory.setMaTuVan("TV" + System.currentTimeMillis());
        newAdvisory.setCauHoi(advisory.getCauHoi());
        newAdvisory.setCauTraLoi(null);
        newAdvisory.setClinic(advisory.getClinic());
        newAdvisory.setDoctor(null);
        newAdvisory.setPatient(advisory.getPatient());
        newAdvisory.setThoiGianHoi(LocalDateTime.now());
        newAdvisory.setTrangThaiTraLoi(0);
        return advisoryRepository.save(newAdvisory);
    }

    public List<Advisory> getAllAnswerAndQuessionByClinic(){
        return advisoryRepository.findAnsweredAdvisories();
    }
}
