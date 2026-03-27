 package com.example.bookingclinic.user.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bookingclinic.user.service.RouterService;

@RestController
@RequestMapping("/api/v1/chat")
public class ChatController {

    @Autowired
    private RouterService routerService;

    @PostMapping
    public Object chat(@RequestBody Map<String,String> body) throws Exception{

        String question = body.get("question"); // lấy giá trị của key:"question" từ phía client gửi lên

        return routerService.handleQuestion(question);

    }
}
// User gửi request
//       ↓
// {
//  "question":"tìm phòng khám nhi"
// }
//       ↓
// @RequestBody Map
//       ↓
// body.get("question")
//       ↓
// question = "tìm phòng khám nhi"
//       ↓
// routerService.handleQuestion()
//       ↓
// query DB hoặc gọi AI
//       ↓
// result
//       ↓
// ResponseEntity.ok(result)
//       ↓
// HTTP response