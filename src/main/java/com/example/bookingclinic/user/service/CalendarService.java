package com.example.bookingclinic.user.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bookingclinic.adminclinic.entity.DoctorScheduleEntity;
import com.example.bookingclinic.doctor.repository.DDoctorScheduleRepository;
import com.example.bookingclinic.user.dto.CalendarDelDTO;
import com.example.bookingclinic.user.dto.CountBookingDTO;
import com.example.bookingclinic.user.entity.Calendar;
import com.example.bookingclinic.user.repository.CalendarRepository;

import jakarta.transaction.Transactional;

@Service
public class CalendarService {
    @Autowired
    private CalendarRepository calendarRepository;

    @Autowired
    private DDoctorScheduleRepository doctorScheduleRepository;

    public List<Calendar> getAllHistory(String id, String status) {
        return calendarRepository.findByPatient_MaBenhNhanAndTrangThaiOrderByNgayKhamDesc(id, "DaKham");
    }
    public List<Calendar> getAllCalendar(String id, String status) {
        return calendarRepository.findByPatient_MaBenhNhanAndTrangThaiOrderByNgayKhamDesc(id, status);

    }

    @Transactional
    public String delCalendarByID(CalendarDelDTO body) {
        System.out.print(body.getIdCalendar());
        if (!calendarRepository.existsById(body.getIdCalendar())) {
            throw new RuntimeException("Không tìm thấy lịch khám: " + body.getIdCalendar());
        }
        try {
            calendarRepository.deleteById(body.getIdCalendar());
            DoctorScheduleEntity doctorSchedule = doctorScheduleRepository.findById(body.getIdSchedule()).orElse(null);
            doctorSchedule.setTrangThai("HoatDong");
            return "Hủy lịch khám thành công";
        } catch (Exception e) {
            return "Hủy lịch khám thất bại: " + e.getMessage();
        }
    }

    @Transactional
    public String bookingCalendar(Calendar calendar, String idChedule) {
        DoctorScheduleEntity doctorSchedule = doctorScheduleRepository
                .findById(idChedule)
                .orElse(null);

        if (doctorSchedule == null) {
            return "Không tìm thấy lịch làm việc";
        }
        try {
            System.out.print("Đã vào chức năng đặt lịch");
            Calendar c = new Calendar();
            String idCalendar = "LK" + System.currentTimeMillis();
            c.setMaLichKham(idCalendar);
            c.setPatient(calendar.getPatient());
            c.setBacSi(calendar.getBacSi());
            c.setGioKham(calendar.getGioKham());
            c.setNgayKham(calendar.getNgayKham());
            c.setLoaiKham(calendar.getLoaiKham());
            c.setTrangThai(calendar.getTrangThai());
            c.setNgayTao(LocalDateTime.now());
            c.setDanhGia(0);
            c.setDaGuiThongBao(0);
            c.setDoctorSchedule(doctorSchedule);
            c.setLyDoKham(calendar.getLyDoKham());

            doctorSchedule.setTrangThai("DaDat");
            doctorScheduleRepository.save(doctorSchedule);
            calendarRepository.save(c);

            return "Đặt lịch thành công";

        } catch (Exception e) {

            e.printStackTrace();

            return "Đặt lịch thất bại: " + e.getMessage();
        }
    }

    public int countCalendar(String id, String status) {
        return calendarRepository.countByPatient_MaBenhNhanAndTrangThai(id, status);
    }

    public List<Calendar> getTodayConfirmedCalendars(String maBacSi) {
        return calendarRepository
                .findByBacSi_MaBacSiAndTrangThaiAndNgayKham(
                        maBacSi,
                        "DaXacNhan",
                        LocalDate.now());
    }

    public CountBookingDTO CountBooingInClinic(String idPatient, String idClinic) {
        Integer total = calendarRepository
                .countByPatient_MaBenhNhanAndBacSi_PhongKham_MaPhongKhamAndTrangThaiIn(
                        idPatient,
                        idClinic,
                        Arrays.asList("DaXacNhan", "ChoXacNhan"));
        CountBookingDTO c = new CountBookingDTO();
        c.setMaPhongKham(idClinic);
        c.setSoLanDat(total);
        return c;
    }
}
