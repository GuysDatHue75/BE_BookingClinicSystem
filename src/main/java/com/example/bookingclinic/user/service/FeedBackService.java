package com.example.bookingclinic.user.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.bookingclinic.user.entity.FeedBack;
import com.example.bookingclinic.user.repository.FeedBackRepository;
@Service
public class FeedBackService {
    
    private FeedBackRepository feedBackRepository;

    public FeedBackService(FeedBackRepository feedBackRepository){
        this.feedBackRepository = feedBackRepository;
    }

    public List<FeedBack> getAllFeedBackInClinic(String id){
        return feedBackRepository.findByMaDoiTuong(id);
    }
    public void sendFeedback(FeedBack fBack){
        feedBackRepository.save(fBack);
    }
}
