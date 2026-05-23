package com.example.bookingclinic.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import com.example.bookingclinic.user.service.StringeeService;

// @Controller
// public class CallController {

//     @MessageMapping("/signal")
//     @SendTo("/topic/signal")
//     public String signal(String message) {

//         return message;
//     }
// }
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/stringee")
@CrossOrigin(originPatterns = "*")
public class CallController {

    @Autowired
    private StringeeService stringeeService;

    @GetMapping("/token")
    public ResponseEntity<Map<String, String>> getToken(@RequestParam String userId) {
        String token = stringeeService.generateToken(userId);
        Map<String, String> response = new HashMap<>();
        response.put("access_token", token);
        return ResponseEntity.ok(response);
    }
}