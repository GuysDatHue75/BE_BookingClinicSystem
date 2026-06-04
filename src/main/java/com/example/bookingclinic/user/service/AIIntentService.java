package com.example.bookingclinic.user.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bookingclinic.user.dto.IntentDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class AIIntentService {
    @Autowired
    private AIService aiService;

    public IntentDTO extractIntent(String sessionId, String question) {
        try {
            String prompt = "Analyze this question and return ONLY JSON: " +
                    "{ \"intent\": \"clinic_search\" or \"medical_question\", " +
                    "\"entity\": \"doctor\" or \"clinic\", \"specialty\": \"string\", \"location\": \"string\" }. " +
                    "Question: " + question;
            
            // Gọi AI nhưng saveToHistory = false để không làm rối bộ nhớ
            String response = aiService.askAI(sessionId + "_intent", prompt, false);
            
            Pattern pattern = Pattern.compile("\\{.*\\}", Pattern.DOTALL);
            Matcher matcher = pattern.matcher(response);
            if (matcher.find()) {
                return new ObjectMapper().readValue(matcher.group(), IntentDTO.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}