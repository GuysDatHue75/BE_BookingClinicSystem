    package com.example.bookingclinic.adminclinic.service.impl;

    import java.time.DayOfWeek;
    import java.time.LocalDate;
    import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
    import org.springframework.transaction.annotation.Transactional;

    import com.example.bookingclinic.adminclinic.dto.request.DoctorSchedulesRequest;
    import com.example.bookingclinic.adminclinic.dto.response.WeeklyScheduleResponse;
import com.example.bookingclinic.adminclinic.entity.ClinicEntity;
import com.example.bookingclinic.adminclinic.entity.DoctorEntity;
import com.example.bookingclinic.adminclinic.entity.DoctorScheduleEntity;
import com.example.bookingclinic.adminclinic.entity.ShiftsEntity;
import com.example.bookingclinic.adminclinic.repository.ClinicDoctorScheduleRepository;
import com.example.bookingclinic.adminclinic.repository.ClinicTimeSlotRepository;
import com.example.bookingclinic.adminclinic.service.ClinicScheduleService;
import com.example.bookingclinic.doctor.entity.Schedule.TimeSlot;
import com.example.bookingclinic.doctor.entity.Schedule.WorkShift;

import lombok.RequiredArgsConstructor;

    @Service
    @RequiredArgsConstructor
    @Transactional
    public class ClinicScheduleServiceImpl implements ClinicScheduleService{
        private final ClinicDoctorScheduleRepository doctorScheduleRepository;
        private final ClinicTimeSlotRepository timeSlotRepository;

        @Override
        @Transactional(readOnly = true)
        public WeeklyScheduleResponse getWeeklySchedule(String maPhongKham, int page){
            LocalDate today = LocalDate.now();
            LocalDate mondayOfCurrentWeek = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));

            int weeksToSubtract = page - 1;
            LocalDate targetMonday = mondayOfCurrentWeek.minusWeeks(weeksToSubtract);
            LocalDate targetSunday = targetMonday.plusDays(6);

            List<DoctorScheduleEntity> rawSchedules = doctorScheduleRepository.findByClinic_MaPhongKhamAndNgayLamViecBetween(maPhongKham, targetMonday, targetSunday);

            List<WeeklyScheduleResponse.DailyScheduleDto> dailySchedulesList = rawSchedules.stream()
                .collect(Collectors.groupingBy(DoctorScheduleEntity::getNgayLamViec))
                .entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(dateEntity -> {
                    LocalDate date = dateEntity.getKey();
                    List<DoctorScheduleEntity> schedulesInDay = dateEntity.getValue();
                    List<WeeklyScheduleResponse.ShiftScheduleDto> shiftSchedulesList = schedulesInDay.stream()
                        .collect(Collectors.groupingBy(schedule -> schedule.getTimeslot().getCaLamViec()))
                        .entrySet().stream()
                        .sorted(Comparator.comparing(shiftEntry -> shiftEntry.getKey().getGioBatDau()))
                        .map(shiftEntry -> {
                            WorkShift shift = shiftEntry.getKey();
                            List<DoctorScheduleEntity> schedulesInShift = shiftEntry.getValue();
                            List<WeeklyScheduleResponse.DoctorBasicDto> doctorBasicDtos = schedulesInShift.stream()
                                .map(DoctorScheduleEntity::getDoctor)
                                .collect(Collectors.toMap(
                                    DoctorEntity::getMaBacSi,
                                    doc -> doc,
                                    (existing, replacement) -> existing
                                ))
                                .values().stream()
                                .map(doc -> WeeklyScheduleResponse.DoctorBasicDto.builder()
                                    .maBacSi(doc.getMaBacSi())
                                    .tenBacSi(doc.getTenBacSi())
                                    .build()
                                ).toList();
                            return WeeklyScheduleResponse.ShiftScheduleDto.builder()
                                .maCaLamViec(shift.getMaCaLamViec())
                                .tenCaLamViec(shift.getTenCaLamViec())
                                .doctors(doctorBasicDtos)
                                .build();
                        }).toList();
                    return WeeklyScheduleResponse.DailyScheduleDto.builder()
                        .ngay(date)
                        .shifts(shiftSchedulesList)
                        .build();
                }).toList();

            return WeeklyScheduleResponse.builder()
                    .ngayDauTuan(targetMonday)
                    .ngayCuoiTuan(targetSunday)
                    .dailySchedules(dailySchedulesList)
                    .build();
        }

        public void validateUpdateDeadline(LocalDate targetWorkingDate){
            LocalDate today = LocalDate.now();

            LocalDate startOfTargetWeek = targetWorkingDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));

            LocalDate deadline = startOfTargetWeek.minusDays(1);

            if(today.isAfter(deadline)){
                throw new RuntimeException("Đã quá hạn thay đổi lịch! Hạn cuối là " + deadline);
            }
        }

        @Override
        public void updateSchedule(String maPhongKham, DoctorSchedulesRequest request){
            validateUpdateDeadline(request.getNgayLamViec());
            
            List<DoctorScheduleEntity> currentSchedules = doctorScheduleRepository
                .findByClinic_MaPhongKhamAndNgayLamViecAndTimeslot_CaLamViec_MaCaLamViec(maPhongKham, request.getNgayLamViec(), request.getMaCaLamViec());
            
            Set<String> currentDoctorIds = currentSchedules.stream()
                .map(schedule -> schedule.getDoctor().getMaBacSi())
                .collect(Collectors.toSet());
            
            Set<String> newDoctorIds = request.getDanhSachMaBacSi() != null
                ? new HashSet<>(request.getDanhSachMaBacSi())
                : new HashSet<>();
            
            Set<String> doctorsToRemove = new HashSet<>(currentDoctorIds);
            doctorsToRemove.removeAll(newDoctorIds);

            if(!doctorsToRemove.isEmpty()){
                List<DoctorScheduleEntity> schedulesToRemove = currentSchedules.stream()
                    .filter(schedule -> doctorsToRemove.contains(schedule.getDoctor().getMaBacSi()))
                    .toList();
                
                boolean hasBooked = schedulesToRemove.stream()
                    .anyMatch(schedule -> !"Trống".equals(schedule.getTrangThai()));
                
                if(hasBooked) {
                    throw new RuntimeException("Không thể lưu! Có bác sĩ bị xóa đã có bệnh nhân đặt lịch.");
                }
                doctorScheduleRepository.deleteAll(schedulesToRemove);
            }

            Set<String> doctorsToAdd = new HashSet<>(newDoctorIds);
            doctorsToAdd.removeAll(currentDoctorIds);

            if(!doctorsToAdd.isEmpty()){
                List<TimeSlot> slots = timeSlotRepository.findByCaLamViec_MaCaLamViec(request.getMaCaLamViec());
                ClinicEntity clinicRef = ClinicEntity.builder().maPhongKham(maPhongKham).build();
                long currentTimeStamp = System.currentTimeMillis();
                AtomicInteger counter = new AtomicInteger(1);
                List<DoctorScheduleEntity> newSchedulesToSave = new ArrayList<>();
                
                for(String maBacSi : doctorsToAdd){
                    DoctorEntity doctorRef = DoctorEntity.builder().maBacSi(maBacSi).build();
                    
                    for(TimeSlot slot: slots) {
                        newSchedulesToSave.add(DoctorScheduleEntity.builder()
                            .maLichLamViec("LV" +  currentTimeStamp + "_" + counter.getAndIncrement())
                            .clinic(clinicRef)
                            .doctor(doctorRef)
                            .ngayLamViec(request.getNgayLamViec())
                            .timeslot(slot)
                            .trangThai("Trống")
                            .build()
                        );
                    }
                }
                doctorScheduleRepository.saveAll(newSchedulesToSave);

            }
        }
    }
