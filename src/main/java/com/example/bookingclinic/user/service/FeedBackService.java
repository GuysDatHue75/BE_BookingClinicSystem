package com.example.bookingclinic.user.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.bookingclinic.user.entity.Calendar;
import com.example.bookingclinic.user.entity.FeedBack;
import com.example.bookingclinic.user.repository.CalendarRepository;
import com.example.bookingclinic.user.repository.FeedBackRepository;
@Service
public class FeedBackService {
    
    private FeedBackRepository feedBackRepository;
    private CalendarRepository calendarRepository;
    public FeedBackService(FeedBackRepository feedBackRepository, CalendarRepository calendarRepository){
        this.feedBackRepository = feedBackRepository;
        this.calendarRepository = calendarRepository;
    }

    public List<FeedBack> getAllFeedBackInClinic(String id){
        return feedBackRepository.findByMaPhongKham(id);
    }
    public void sendFeedback(FeedBack fBack){
        String idCalenda = fBack.getMaLichKham();
        Calendar calendar = calendarRepository.findById(idCalenda).orElse(null);
        if(calendar != null){
            calendar.setDanhGia(1);
            calendarRepository.save(calendar);
        }
        if(fBack.getMaDanhGia() == null){
            fBack.setMaDanhGia("DG" + System.currentTimeMillis());
        }
        feedBackRepository.save(fBack);
    }
}
