package com.example.bookingclinic.user.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.bookingclinic.user.entity.Calendar;
import com.example.bookingclinic.user.service.CalendarService;

@RestController
@RequestMapping("api/v1")
public class CalendarController {
    private CalendarService calendarService;

    public CalendarController(CalendarService calendarService){
        this.calendarService = calendarService;
    }

    @GetMapping("/calendar/history") // xem lịch sử khám bệnh
    public List<Calendar> getAllHistory(@RequestParam String id){
        return calendarService.getAllHistory(id, "DaKham");
    }

    @GetMapping("/calendars") // xem lịch khám đã được bs xác nhận
    public List<Calendar> getAllCalendars(@RequestParam String id, @RequestParam String status){
        return calendarService.getAllCalendar(id, status);
    }

    @DeleteMapping("/calendar/{id}") // xóa lịch khám đã đặt
    public void delCalendarByID(@PathVariable String id){
        calendarService.delCalendarByID(id);
    }

    @PostMapping("/calendar") // đặt lịch khám
    public void bookingCalendar(@RequestBody Calendar calendar){
        calendarService.bookingCalendar(calendar);
    }
}
