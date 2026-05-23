package com.example.bookingclinic.adminclinic.service.impl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.bookingclinic.adminclinic.dto.response.DailyScheduleResponse;
import com.example.bookingclinic.adminclinic.dto.response.DoctorWeeklyScheduleResponse;
import com.example.bookingclinic.adminclinic.dto.response.SlotItemDTO;
import com.example.bookingclinic.adminclinic.repository.ClinicDoctorScheduleRepository;
import com.example.bookingclinic.adminclinic.service.ClinicDoctorScheduleService;
import com.example.bookingclinic.adminclinic.utils.DateUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClinicDoctorScheduleServiceImpl implements ClinicDoctorScheduleService{
    private final ClinicDoctorScheduleRepository doctorScheduleRepository;

    public List<DailyScheduleResponse> getFormattedWeeklySchedule(String maPhongKham, String maBacSi, LocalDate targetDate){
        LocalDate dateToQuery = (targetDate != null) ? targetDate : LocalDate.now();
        LocalDate startDate = DateUtils.getStartOfWeek(dateToQuery);
        LocalDate endDate = DateUtils.getEndOfWeek(dateToQuery);

        List<DoctorWeeklyScheduleResponse> flatList = doctorScheduleRepository.getWeeklySchedule(maBacSi, maPhongKham, startDate, endDate);

        Map<LocalDate, List<DoctorWeeklyScheduleResponse>> groupedByDate = flatList.stream()
            .collect(Collectors.groupingBy(DoctorWeeklyScheduleResponse::getNgayLamViec));

        List<DailyScheduleResponse> response = new ArrayList<>();

        for(LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)){
            List<DoctorWeeklyScheduleResponse> slotsOfDay = groupedByDate.getOrDefault(date, new ArrayList<>());

            List<SlotItemDTO> caSang = slotsOfDay.stream()
                .filter(this::isMorningSlot)
                .map(this::convertToSlotItem)
                .collect(Collectors.toList());
            
            List<SlotItemDTO> caChieu = slotsOfDay.stream()
                .filter(slot -> !isMorningSlot(slot))
                .map(this::convertToSlotItem)
                .collect(Collectors.toList());
            
            response.add(DailyScheduleResponse.builder()
                .ngay(date)
                .thu(DateUtils.getVietNameseDayOfWeek(date))
                .caSang(caSang)
                .caChieu(caChieu)
                .build()
            );
        }
        return response;
    }

    private boolean isMorningSlot(DoctorWeeklyScheduleResponse dto){
        if (dto.getGioBatDau() == null) return true;
        return dto.getGioBatDau().isBefore(LocalTime.of(12, 0));
    }

    private SlotItemDTO convertToSlotItem(DoctorWeeklyScheduleResponse dto){
        boolean isBooked = (dto.getMaLichKham() != null);
        return SlotItemDTO.builder()
                .maLichLamViec(dto.getMaLichLamViec())
                .thoiGian(dto.getGioBatDau())
                .daDat(isBooked)
                .tenBenhNhan(isBooked ? dto.getTenBenhNhan() : null)
                .build();
    }
    
}
