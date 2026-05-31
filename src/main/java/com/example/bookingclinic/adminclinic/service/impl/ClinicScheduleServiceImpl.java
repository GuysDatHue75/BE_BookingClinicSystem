// package com.example.bookingclinic.adminclinic.service.impl;

// import java.time.DayOfWeek;
// import java.time.LocalDate;
// import java.time.temporal.TemporalAdjusters;
// import java.util.ArrayList;
// import java.util.Comparator;
// import java.util.HashSet;
// import java.util.List;
// import java.util.Map;
// import java.util.Optional;
// import java.util.Set;
// import java.util.UUID;
// import java.util.stream.Collectors;

// import org.springframework.stereotype.Service;
// import org.springframework.transaction.annotation.Transactional;

// import com.example.bookingclinic.adminclinic.dto.request.ShiftAssignmentRequest;
// import com.example.bookingclinic.adminclinic.dto.request.WeeklyScheduleUpdateRequest;
// import com.example.bookingclinic.adminclinic.dto.response.DoctorSimpleResponse;
// import com.example.bookingclinic.adminclinic.dto.response.NextWeekAvailableResponse;
// import com.example.bookingclinic.adminclinic.dto.response.WeeklyScheduleResponse;
// import com.example.bookingclinic.adminclinic.entity.ClinicEntity;
// import com.example.bookingclinic.adminclinic.entity.DoctorEntity;
// import com.example.bookingclinic.adminclinic.entity.DoctorScheduleEntity;
// import com.example.bookingclinic.adminclinic.repository.ClinicDoctorRepository;
// import com.example.bookingclinic.adminclinic.repository.ClinicDoctorScheduleRepository;
// import com.example.bookingclinic.adminclinic.repository.ClinicTimeSlotRepository;
// import com.example.bookingclinic.adminclinic.service.ClinicScheduleService;
// import com.example.bookingclinic.doctor.entity.Schedule.TimeSlot;
// import com.example.bookingclinic.doctor.entity.Schedule.WorkShift;
// import com.example.bookingclinic.exception.ScheduleException;

// import lombok.RequiredArgsConstructor;

// @Service
// @RequiredArgsConstructor
// @Transactional
// public class ClinicScheduleServiceImpl implements ClinicScheduleService{
//     private final ClinicDoctorScheduleRepository doctorScheduleRepository;
//     private final ClinicTimeSlotRepository timeSlotRepository;

//     @Override
//     @Transactional(readOnly = true)
//     public WeeklyScheduleResponse getWeeklySchedule(String maPhongKham, int page){
//         LocalDate today = LocalDate.now();
//         LocalDate mondayOfCurrentWeek = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));

//         int weeksToSubtract = page - 1;
//         LocalDate targetMonday = mondayOfCurrentWeek.minusWeeks(weeksToSubtract);
//         LocalDate targetSunday = targetMonday.plusDays(6);

//         List<DoctorScheduleEntity> rawSchedules = doctorScheduleRepository.findByClinic_MaPhongKhamAndNgayLamViecBetween(maPhongKham, targetMonday, targetSunday);

//         List<WeeklyScheduleResponse.DailyScheduleDto> dailySchedulesList = rawSchedules.stream()
//             .collect(Collectors.groupingBy(DoctorScheduleEntity::getNgayLamViec))
//             .entrySet().stream()
//             .sorted(Map.Entry.comparingByKey())
//             .map(dateEntity -> {
//                 LocalDate date = dateEntity.getKey();
//                 List<DoctorScheduleEntity> schedulesInDay = dateEntity.getValue();
//                 List<WeeklyScheduleResponse.ShiftScheduleDto> shiftSchedulesList = schedulesInDay.stream()
//                     .collect(Collectors.groupingBy(schedule -> schedule.getTimeslot().getCaLamViec()))
//                     .entrySet().stream()
//                     .sorted(Comparator.comparing(shiftEntry -> shiftEntry.getKey().getGioBatDau()))
//                     .map(shiftEntry -> {
//                         WorkShift shift = shiftEntry.getKey();
//                         List<DoctorScheduleEntity> schedulesInShift = shiftEntry.getValue();
//                         List<WeeklyScheduleResponse.DoctorBasicDto> doctorBasicDtos = schedulesInShift.stream()
//                             .map(DoctorScheduleEntity::getDoctor)
//                             .collect(Collectors.toMap(
//                                 DoctorEntity::getMaBacSi,
//                                 doc -> doc,
//                                 (existing, replacement) -> existing
//                             ))
//                             .values().stream()
//                             .map(doc -> WeeklyScheduleResponse.DoctorBasicDto.builder()
//                                 .maBacSi(doc.getMaBacSi())
//                                 .tenBacSi(doc.getTenBacSi())
//                                 .build()
//                             ).toList();
//                         return WeeklyScheduleResponse.ShiftScheduleDto.builder()
//                             .maCaLamViec(shift.getMaCaLamViec())
//                             .tenCaLamViec(shift.getTenCaLamViec())
//                             .doctors(doctorBasicDtos)
//                             .build();
//                     }).toList();
//                 return WeeklyScheduleResponse.DailyScheduleDto.builder()
//                     .ngay(date)
//                     .shifts(shiftSchedulesList)
//                     .build();
//             }).toList();

//         return WeeklyScheduleResponse.builder()
//                 .ngayDauTuan(targetMonday)
//                 .ngayCuoiTuan(targetSunday)
//                 .dailySchedules(dailySchedulesList)
//                 .build();
//     }

//     public void validateUpdateDeadline(LocalDate targetWorkingDate){
//         LocalDate today = LocalDate.now();
//         LocalDate startOfTargetWeek = targetWorkingDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
//         LocalDate deadline = startOfTargetWeek.minusDays(1);

//         if(today.isAfter(deadline)){
//             throw new RuntimeException("Đã quá hạn thay đổi lịch! Hạn cuối là " + deadline);
//         }
//     }

//     @Override
//     public void updateSchedule(String maPhongKham, WeeklyScheduleUpdateRequest request){
//         // Check hạn lưu 1 lần cho cả tuần dựa vào ngày đầu tuần
//         validateUpdateDeadline(request.getStartDate()); 
        
//         List<DoctorScheduleEntity> newSchedulesToSave = new ArrayList<>();
//         ClinicEntity clinicRef = ClinicEntity.builder().maPhongKham(maPhongKham).build();

//         // Lặp qua danh sách 21 ca làm việc được đẩy lên từ UI
//         for (ShiftAssignmentRequest shiftReq : request.getShiftAssignments()) {
//             List<DoctorScheduleEntity> currentSchedules = doctorScheduleRepository
//                 .findByClinic_MaPhongKhamAndNgayLamViecAndTimeslot_CaLamViec_MaCaLamViec(maPhongKham, shiftReq.getNgayLamViec(), shiftReq.getMaCaLamViec());
            
//             Set<String> currentDoctorIds = currentSchedules.stream()
//                 .map(schedule -> schedule.getDoctor().getMaBacSi())
//                 .collect(Collectors.toSet());
            
//             Set<String> newDoctorIds = shiftReq.getDanhSachMaBacSi() != null
//                 ? new HashSet<>(shiftReq.getDanhSachMaBacSi())
//                 : new HashSet<>();
            
//             // XỬ LÝ XÓA BÁC SĨ BỊ BỎ TICK
//             Set<String> doctorsToRemove = new HashSet<>(currentDoctorIds);
//             doctorsToRemove.removeAll(newDoctorIds);

//             if(!doctorsToRemove.isEmpty()){
//                 List<DoctorScheduleEntity> schedulesToRemove = currentSchedules.stream()
//                     .filter(schedule -> doctorsToRemove.contains(schedule.getDoctor().getMaBacSi()))
//                     .toList();
                
//                 boolean hasBooked = schedulesToRemove.stream()
//                     .anyMatch(schedule -> !"DaDat".equals(schedule.getTrangThai()));
                
//                 if(hasBooked) {
//                     throw new RuntimeException("Không thể lưu! Có bác sĩ bị xóa đã có bệnh nhân đặt lịch trong ca " + shiftReq.getMaCaLamViec() + " ngày " + shiftReq.getNgayLamViec());
//                 }
//                 doctorScheduleRepository.deleteAll(schedulesToRemove);
//             }

//             // XỬ LÝ THÊM BÁC SĨ MỚI TICK
//             Set<String> doctorsToAdd = new HashSet<>(newDoctorIds);
//             doctorsToAdd.removeAll(currentDoctorIds);

//             if(!doctorsToAdd.isEmpty()){
//                 List<TimeSlot> slots = timeSlotRepository.findByCaLamViec_MaCaLamViecAndIsDeletedFalse(shiftReq.getMaCaLamViec());
//                 if(slots == null || slots.isEmpty()) {
//                     throw new ScheduleException("Không tìm thấy các khung giờ khám đang hoạt động cho ca: " + shiftReq.getMaCaLamViec());
//                 }
                
//                 for(String maBacSi : doctorsToAdd){
//                     DoctorEntity doctorRef = DoctorEntity.builder().maBacSi(maBacSi).build();
                    
//                     for(TimeSlot slot: slots) {
//                         newSchedulesToSave.add(DoctorScheduleEntity.builder()
//                             // ĐÃ FIX: Dùng UUID tạo ID độc nhất chống trùng lặp thay vì timestamp
//                             .maLichLamViec("LLV_" + UUID.randomUUID().toString().substring(0, 8).toUpperCase()) 
//                             .clinic(clinicRef)
//                             .doctor(doctorRef)
//                             .ngayLamViec(shiftReq.getNgayLamViec())
//                             .timeslot(slot)
//                             // ĐÃ FIX: Mặc định lúc vừa phân lịch là chưa có ai đặt
//                             .trangThai("HoatDong") 
//                             .build()
//                         );
//                     }
//                 }
//             }
//         }
        
//         // Lưu toàn bộ lịch mới (của cả tuần) xuống DB trong 1 lần query
//         if (!newSchedulesToSave.isEmpty()) {
//             doctorScheduleRepository.saveAll(newSchedulesToSave);
//         }
//     }

//     @Override
//     @Transactional(readOnly = true)
//     public NextWeekAvailableResponse getNextAvailableWeek(String maPhongKham) {
//         Optional<LocalDate> maxDateOpt = doctorScheduleRepository.findMaxNgayLamViecByClinicMaPhongKham(maPhongKham);

//         LocalDate nextMonday;
//         if (maxDateOpt.isPresent() && maxDateOpt.get().isAfter(LocalDate.now())) {
//             nextMonday = maxDateOpt.get().with(TemporalAdjusters.next(DayOfWeek.MONDAY));
//         } else {
//             nextMonday = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY));
//         }
        
//         LocalDate nextSunday = nextMonday.plusDays(6);

//         return NextWeekAvailableResponse.builder()
//                 .startDate(nextMonday)
//                 .endDate(nextSunday)
//                 .build();
//     }
// }
package com.example.bookingclinic.adminclinic.service.impl;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.bookingclinic.adminclinic.dto.request.ShiftAssignmentRequest;
import com.example.bookingclinic.adminclinic.dto.request.WeeklyScheduleUpdateRequest;
import com.example.bookingclinic.adminclinic.dto.response.NextWeekAvailableResponse;
import com.example.bookingclinic.adminclinic.dto.response.WeeklyScheduleResponse;
import com.example.bookingclinic.adminclinic.entity.ClinicEntity;
import com.example.bookingclinic.adminclinic.entity.DoctorEntity;
import com.example.bookingclinic.adminclinic.entity.DoctorScheduleEntity;
import com.example.bookingclinic.adminclinic.repository.ClinicDoctorScheduleRepository;
import com.example.bookingclinic.adminclinic.repository.ClinicTimeSlotRepository;
import com.example.bookingclinic.adminclinic.service.ClinicScheduleService;
import com.example.bookingclinic.doctor.entity.Schedule.TimeSlot;
import com.example.bookingclinic.doctor.entity.Schedule.WorkShift;
import com.example.bookingclinic.exception.ScheduleException;

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
    // Luôn lấy mốc Trang 1 là Tuần Hiện Tại
    LocalDate today = LocalDate.now();
    LocalDate mondayOfCurrentWeek = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));

    // Tính toán số tuần cần cộng thêm (Page 1 = +0 tuần, Page 2 = +1 tuần, Page 0 = -1 tuần)
    int weeksToAdd = page - 1; 
    LocalDate targetMonday = mondayOfCurrentWeek.plusWeeks(weeksToAdd);
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
    public void updateSchedule(String maPhongKham, WeeklyScheduleUpdateRequest request){
        validateUpdateDeadline(request.getStartDate()); 
        
        List<DoctorScheduleEntity> newSchedulesToSave = new ArrayList<>();
        ClinicEntity clinicRef = ClinicEntity.builder().maPhongKham(maPhongKham).build();

        for (ShiftAssignmentRequest shiftReq : request.getShiftAssignments()) {
            List<DoctorScheduleEntity> currentSchedules = doctorScheduleRepository
                .findByClinic_MaPhongKhamAndNgayLamViecAndTimeslot_CaLamViec_MaCaLamViec(maPhongKham, shiftReq.getNgayLamViec(), shiftReq.getMaCaLamViec());
            
            Set<String> currentDoctorIds = currentSchedules.stream()
                .map(schedule -> schedule.getDoctor().getMaBacSi())
                .collect(Collectors.toSet());
            
            Set<String> newDoctorIds = shiftReq.getDanhSachMaBacSi() != null
                ? new HashSet<>(shiftReq.getDanhSachMaBacSi())
                : new HashSet<>();
            
            Set<String> doctorsToRemove = new HashSet<>(currentDoctorIds);
            doctorsToRemove.removeAll(newDoctorIds);

            if(!doctorsToRemove.isEmpty()){
                List<DoctorScheduleEntity> schedulesToRemove = currentSchedules.stream()
                    .filter(schedule -> doctorsToRemove.contains(schedule.getDoctor().getMaBacSi()))
                    .toList();
                
                boolean hasBooked = schedulesToRemove.stream()
                    .anyMatch(schedule -> "DaDat".equals(schedule.getTrangThai()));
                
                if(hasBooked) {
                    throw new RuntimeException("Không thể lưu! Có bác sĩ bị xóa đã có bệnh nhân đặt lịch trong ca " + shiftReq.getMaCaLamViec() + " ngày " + shiftReq.getNgayLamViec());
                }
                doctorScheduleRepository.deleteAll(schedulesToRemove);
            }

            Set<String> doctorsToAdd = new HashSet<>(newDoctorIds);
            doctorsToAdd.removeAll(currentDoctorIds);

            if(!doctorsToAdd.isEmpty()){
                List<TimeSlot> slots = timeSlotRepository.findByCaLamViec_MaCaLamViecAndIsDeletedFalse(shiftReq.getMaCaLamViec());
                if(slots == null || slots.isEmpty()) {
                    throw new ScheduleException("Không tìm thấy các khung giờ khám đang hoạt động cho ca: " + shiftReq.getMaCaLamViec());
                }
                
                for(String maBacSi : doctorsToAdd){
                    DoctorEntity doctorRef = DoctorEntity.builder().maBacSi(maBacSi).build();
                    
                    for(TimeSlot slot: slots) {
                        newSchedulesToSave.add(DoctorScheduleEntity.builder()
                            .maLichLamViec("LLV_" + UUID.randomUUID().toString().substring(0, 8).toUpperCase()) 
                            .clinic(clinicRef)
                            .doctor(doctorRef)
                            .ngayLamViec(shiftReq.getNgayLamViec())
                            .timeslot(slot)
                            .trangThai("HoatDong") 
                            .build()
                        );
                    }
                }
            }
        }
        
        if (!newSchedulesToSave.isEmpty()) {
            doctorScheduleRepository.saveAll(newSchedulesToSave);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public NextWeekAvailableResponse getNextAvailableWeek(String maPhongKham) {
        Optional<LocalDate> maxDateOpt = doctorScheduleRepository.findMaxNgayLamViecByClinicMaPhongKham(maPhongKham);

        LocalDate nextMonday;
        if (maxDateOpt.isPresent() && maxDateOpt.get().isAfter(LocalDate.now())) {
            nextMonday = maxDateOpt.get().with(TemporalAdjusters.next(DayOfWeek.MONDAY));
        } else {
            nextMonday = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY));
        }
        
        LocalDate nextSunday = nextMonday.plusDays(6);

        return NextWeekAvailableResponse.builder()
                .startDate(nextMonday)
                .endDate(nextSunday)
                .build();
    }
}