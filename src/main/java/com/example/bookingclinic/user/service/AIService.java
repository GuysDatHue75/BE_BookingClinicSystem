package com.example.bookingclinic.user.service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List; 
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class AIService {

    private String API_URL = "https://openrouter.ai/api/v1/chat/completions";

    @Value("${ai.api.key}")
    private String apiKey;

    public String askAI(String question) throws Exception {

        RestTemplate restTemplate = new RestTemplate();// RestTemplate là 1 class của spring dùng để gọi HTTP API cho phép java gọi GET,POST,PUT,DELETE

        HttpHeaders headers = new HttpHeaders(); // là 1 class của spring nó chứa: contentTtype, Authorization, User-Agent
        headers.setContentType(MediaType.APPLICATION_JSON);// Nói với server rằng body là JSON
        headers.setBearerAuth(apiKey);// thêm headers authorization: Bearer apikey

        Map<String, Object> body = new HashMap<>(); // tạo body JSON request 

        body.put("model", "deepseek/deepseek-chat"); // chọn AI model -> ở đây là deepseek

        List<Map<String,String>> messages = new ArrayList<>(); // tạo messages list

        Map<String,String> msg = new HashMap<>();
        msg.put("role","user"); // set role, có 3 loại role đó là user(đặt câu hỏi), system(hướng dẫn AI), assistent(AI trả lời)
        msg.put("content",question); // set câu hỏi (tức câu hỏi từ người dùng)

        messages.add(msg);

        body.put("messages", messages);

        HttpEntity<Map<String,Object>> request = new HttpEntity<>(body,headers);
        //HttpEntity là 1 đối tượng đại diện cho HTTP Request, nó chứa headers, body

        ResponseEntity<String> response = restTemplate.postForEntity(
                API_URL,
                request,
                String.class
        );
        // ReponseEntity là 1 đối tượng chứa HTTP response nó chứa: headers, status code, body

        ObjectMapper mapper = new ObjectMapper();

        JsonNode root = mapper.readTree(response.getBody());

        return root
                .path("choices")
                .get(0)
                .path("message")
                .path("content")
                .asText();
    }
}

// Java Backend
//      │
//      ▼
// Gửi HTTP request → OpenRouter API
//      │
//      ▼
// OpenRouter gọi AI model (DeepSeek)
//      │
//      ▼
// AI trả JSON response
//      │
//      ▼
// Java đọc JSON
//      │
//      ▼
// Trả về câu trả lời AI