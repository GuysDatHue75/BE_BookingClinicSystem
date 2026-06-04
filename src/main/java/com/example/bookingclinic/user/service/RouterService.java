package com.example.bookingclinic.user.service;

import org.springframework.stereotype.Service;

import com.example.bookingclinic.user.dto.ChatResponseDTO;
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

    // Giả sử handleQuestion nhận thêm tham số currentCity từ Controller
public ChatResponseDTO handleQuestion(String sessionId, String question, String currentCity) throws Exception {
    
    IntentDTO intent = aiIntentService.extractIntent(sessionId + "_intent", question);

    if (intent != null && "clinic_search".equalsIgnoreCase(intent.getIntent())) {
        Object data = null;
        int limit = (intent.getLimit() == null) ? 5 : intent.getLimit();

        if ("clinic".equalsIgnoreCase(intent.getEntity())) {
            // Truyền cả location bóc từ AI và currentCity lấy từ hệ thống
            data = clinicService.searchClinicWithAI(
                    intent.getSpecialty(),
                    intent.getLocation(), // Địa điểm AI tìm thấy (có thể null)
                    currentCity,          // Địa điểm hiện tại bạn đã có
                    limit
            );
        }
        if ("doctor".equalsIgnoreCase(intent.getEntity())) {
            // Truyền cả location bóc từ AI và currentCity lấy từ hệ thống
            data = doctorService.searchDoctorWithAI(
                    intent.getSpecialty(),
                    intent.getLocation(), // Địa điểm AI tìm thấy (có thể null)
                    currentCity,          // Địa điểm hiện tại bạn đã có
                    limit
            );
        }
        
        return new ChatResponseDTO("Đây là danh sách tôi tìm được:", "clinic_search", intent.getEntity(), data);
    }

    String aiAnswer = aiService.askAI(sessionId, question, true);
    return new ChatResponseDTO(aiAnswer, "medical_question", "none", null);
}
}