package com.example.bookingclinic.user.service;

import org.springframework.stereotype.Service;

import com.example.bookingclinic.user.dto.IntentDTO;

@Service
public class RouterService {
    private AIIntentService aiIntentService;
    private UDoctorService doctorService;
    private UClinicService clinicService;
    private AIService aiService;
    public RouterService(AIIntentService aiIntentService, UDoctorService doctorService, UClinicService clinicService,AIService aiService){
        this.aiIntentService = aiIntentService;
        this.clinicService = clinicService;
        this.doctorService = doctorService;
        this.aiService = aiService;
    }

    public Object handleQuestion(String question) throws Exception {

        IntentDTO intent = aiIntentService.extractIntent(question);

        if(intent == null){
            return "Không hiểu câu hỏi";
        }

        if("clinic_search".equalsIgnoreCase(intent.getIntent())){

            if("doctor".equalsIgnoreCase(intent.getEntity())){
                return doctorService.searchDoctorWithAI(
                        intent.getSpecialty(),
                        intent.getLocation(),
                        intent.getLimit() == null ? 1 : intent.getLimit()
                );
            }

            if("clinic".equalsIgnoreCase(intent.getEntity())){
                return clinicService.searchClinicWithAI(
                        intent.getSpecialty(),
                        intent.getLocation(),
                        intent.getLimit() == null ? 1 : intent.getLimit()
                );
            }
        }

        return aiService.askAI(question);
    }
}
