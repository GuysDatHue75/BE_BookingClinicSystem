package com.example.bookingclinic.user.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.bookingclinic.user.entity.Calendar;
import com.example.bookingclinic.user.repository.CalendarRepository;

import jakarta.transaction.Transactional;

@Service
public class CalendarService {
    private CalendarRepository calendarRepository;

    public CalendarService(CalendarRepository calendarRepository){
        this.calendarRepository = calendarRepository;
    }

    public List<Calendar> getAllHistory(String id, String status){
        return calendarRepository.findByPatient_MaBenhNhanAndTrangThai(id, "DaKham");
    }
    public List<Calendar> getAllCalendar(String id, String status){
        return calendarRepository.findByPatient_MaBenhNhanAndTrangThai(id, status);
    }
    @Transactional
    public void delCalendarByID(String id){
        if(!calendarRepository.existsById(id)){
            throw new RuntimeException("Không tìm thấy lịch khám: " + id);
        }
        calendarRepository.deleteById(id);
    }
    public void bookingCalendar(Calendar calendar){
        calendarRepository.save(calendar);
    }
}
