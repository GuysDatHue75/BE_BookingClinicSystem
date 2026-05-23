package com.example.bookingclinic.user.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bookingclinic.user.dto.ChatResponseDTO;
import com.example.bookingclinic.user.dto.IntentDTO;
import com.example.bookingclinic.user.service.AIIntentService;
import com.example.bookingclinic.user.service.AIService;
import com.example.bookingclinic.user.service.RouterService;

// @RestController
// @RequestMapping("/api/v1/chat")
// public class ChatController {

//     @Autowired
//     private RouterService routerService;

//     @PostMapping
//     public Object chat(@RequestBody Map<String,String> body) throws Exception{

//         String question = body.get("question"); // lấy giá trị của key:"question" từ phía client gửi lên

//         return routerService.handleQuestion(question);

//     }
// }
@RestController
@RequestMapping("/api/v1")
public class ChatController {

    @Autowired
    private RouterService routerService;

    @PostMapping("/chat")
    public ResponseEntity<ChatResponseDTO> chat(@RequestBody Map<String, String> request) throws Exception {
        String sessionId = request.get("sessionId");
        String message = request.get("message");

        // Giả sử bạn lấy được thành phố hiện tại của người dùng từ đâu đó
        // (Session/Request/Header)
        String userCurrentCity = request.get("currentCity"); // Hoặc lấy từ biến có sẵn của bạn

        return ResponseEntity.ok(routerService.handleQuestion(sessionId, message, userCurrentCity));
    }
}
// User gửi request
// ↓
// {
// "question":"tìm phòng khám nhi"
// }
// ↓
// @RequestBody Map
// ↓
// body.get("question")
// ↓
// question = "tìm phòng khám nhi"
// ↓
// routerService.handleQuestion()
// ↓
// query DB hoặc gọi AI
// ↓
// result
// ↓
// ResponseEntity.ok(result)
// ↓
// HTTP response