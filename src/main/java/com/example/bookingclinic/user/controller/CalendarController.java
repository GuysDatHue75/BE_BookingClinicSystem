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

import com.example.bookingclinic.user.dto.CalendarDelDTO;
import com.example.bookingclinic.user.dto.CountBookingDTO;
import com.example.bookingclinic.user.entity.Calendar;
import com.example.bookingclinic.user.service.CalendarService;

@RestController
@RequestMapping("/api/v1")
public class CalendarController {
    private CalendarService calendarService;

    public CalendarController(CalendarService calendarService) {
        this.calendarService = calendarService;
    }

    @GetMapping("/calendar/history") // xem lịch sử khám bệnh
    public List<Calendar> getAllHistory(@RequestParam String id) {
        return calendarService.getAllHistory(id, "DaKham");
    }

    @GetMapping("/calendars") // xem lịch khám đã được bs xác nhận
    public List<Calendar> getAllCalendars(@RequestParam String id, @RequestParam String status) {
        return calendarService.getAllCalendar(id, status);
    }

    @DeleteMapping("/calendar") // xóa lịch khám đã đặt
    public String delCalendarByID(@RequestBody CalendarDelDTO body) {
        return calendarService.delCalendarByID(body);
    }

    @PostMapping("/calendar/{idSchedule}") // đặt lịch khám
    public String bookingCalendar(@RequestBody Calendar calendar, @PathVariable String idSchedule) {
        return calendarService.bookingCalendar(calendar, idSchedule);
    }

    @GetMapping("/c-calendar") // đếm lịch khám đã được xác nhận
    public int countCalendar(@RequestParam String id) {
        return calendarService.countCalendar(id, "DaXacNhan");
    }

    @GetMapping("/calendar-dt/today") // bác sĩ lấy ra các lịch khám online của mình của ngày hôm nay
    public List<Calendar> getTodayCalendar(
            @RequestParam String maBacSi) {
        return calendarService.getTodayConfirmedCalendars(maBacSi);
    }
    @GetMapping("c-booking-calendar") // check xem bệnh nhân đã đặt lịch ở phòng khám đó chưa
    public CountBookingDTO CountBooingInClinic(@RequestParam String idPatient, @RequestParam String idClinic){
        return calendarService.CountBooingInClinic(idPatient, idClinic);
    }
}
