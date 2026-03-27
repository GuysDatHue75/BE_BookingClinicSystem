package com.example.bookingclinic.user.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import com.example.bookingclinic.user.dto.IntentDTO;
import com.fasterxml.jackson.databind.ObjectMapper;


@Service
public class AIIntentService {
    
    private AIService aiService;

    public AIIntentService(AIService aiService){
        this.aiService = aiService;
    }

     public IntentDTO extractIntent(String question) throws Exception {
        String prompt = """
        Extract information from user question.
        Do not add explanation.
        Return JSON only.
        {
         "intent":"clinic_search | medical_question",
         "entity":"doctor | clinic",
         "specialty":"value",
         "location":"value",
         "limit":5
        }

        Question:
        """ + question;

        String aiResponse = aiService.askAI(prompt);

        try {
            Pattern pattern = Pattern.compile("\\{.*\\}", Pattern.DOTALL); // Pattern là 1 class của java dùng để sử lý Regex - dùng để tìm chuỗi theo mẫu
            // regex ở đây là \\{.*\\} -> ký tự { + mọi ký tự(.*) + } tức là lấy toàn bộ từ { đến }
            // Pattern.DOTALL đọc được cả những đoạn xuống dòng nếu ko có thì ko đọc được
            Matcher matcher = pattern.matcher(aiResponse);// Matcher là 1 object dùng để áp dụng regex lên text
            // sau khi đã có pattern thì matcher tức là áp dụng mẫu đó lên 1 đoạn văn bảng nhưng chưa tìm gì cả
            if (!matcher.find()) {
                return null;
            }
            // matcher.find() tìm trong đoạn text đã được chỉ định xem có phù hợp với pattern đã được định nghĩa hay không trả về true nếu có kết quả ngược lại trả về false
            String json = matcher.group();
            // matcher.group() lấy kết quả
            ObjectMapper mapper = new ObjectMapper(); // ObjectMapper thuộc thư viên Jackson nó cho phép chuyển đổi JSON -> java object và ngược lại
            return mapper.readValue(json, IntentDTO.class); // chuyển Json -> java object

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
