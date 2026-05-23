package com.example.bookingclinic.user.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bookingclinic.user.entity.FeedBack;
import com.example.bookingclinic.user.service.FeedBackService;

@RestController
@RequestMapping("api/v1")
public class FeedbackController {
    private FeedBackService fBackService;

    public FeedbackController(FeedBackService fBackService){
        this.fBackService = fBackService;
    }

    @GetMapping("/clinic/feedbacks") //xem toàn bộ feedback của 1 phòng khám
    public List<FeedBack> getAllFBackInClinic(String id){
        return fBackService.getAllFeedBackInClinic(id);
    }

    @PostMapping("/feedback") // gửi 1 feedback đến phòng bác sĩ or phòng khám
    public void sendFeedBack(@RequestBody FeedBack feedBack){
        fBackService.sendFeedback(feedBack);
    }
}
