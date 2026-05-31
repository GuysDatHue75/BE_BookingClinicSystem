package com.example.bookingclinic.doctor.service;

import com.example.bookingclinic.doctor.repository.DoctorRepository;
import com.example.bookingclinic.doctor.repository.ScheduleRepository.DoctorScheduleRepository;
import com.example.bookingclinic.doctor.repository.ScheduleRepository.DTimeSlotRepository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

import com.example.bookingclinic.doctor.dto.schedule.DailyScheduleDTO;
import com.example.bookingclinic.doctor.dto.schedule.GroupedScheduleDTO;
import com.example.bookingclinic.doctor.dto.schedule.ScheduleResponseDTO;
import com.example.bookingclinic.doctor.dto.schedule.SimpleDoctorScheduleDTO;
import com.example.bookingclinic.doctor.dto.schedule.UpdateSchedulesDTO;
import com.example.bookingclinic.doctor.dto.schedule.WeeklyScheduleRequestDTO;
import com.example.bookingclinic.doctor.entity.Schedule.DoctorSchedule;
import com.example.bookingclinic.doctor.entity.Schedule.TimeSlot;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DoctorScheduleService {

    private final DoctorRepository doctorRepository;
    private final DoctorScheduleRepository doctorScheduleRepository;
    private final DTimeSlotRepository timeSlotRepository; // Bổ sung Repository lấy Khung Giờ

    // BẮT BUỘC CÓ: Đảm bảo nếu lỗi ở giữa chừng thì sẽ HỦY BỎ toàn bộ
    @Transactional
    // 1. Tạo Lịch làm việc
    public List<DoctorSchedule> createWeeklySchedule(WeeklyScheduleRequestDTO requestDTO) {

        List<DoctorSchedule> schedulesToSave = new ArrayList<>();
        String maBacSi = requestDTO.getMaBacSi();
        LocalDate homNay = LocalDate.now();

        // VÒNG FOR 1: Duyệt qua từng NGÀY
        for (DailyScheduleDTO ngayDTO : requestDTO.getDanhSachNgayLamViec()) {
            LocalDate ngayLamViec = ngayDTO.getNgayLamViec();
            if (ngayLamViec.isBefore(homNay)) {
                throw new RuntimeException("Không thể lên lịch cho ngày trong quá khứ: " + ngayLamViec);
            }
            if (ngayLamViec.getDayOfWeek() == DayOfWeek.SUNDAY) {
                throw new RuntimeException("Hệ thống nghỉ Chủ Nhật. Không thể lên lịch ngày: " + ngayLamViec);
            }

            // VÒNG FOR 2: Duyệt qua từng MÃ KHUNG GIỜ
            // (Lưu ý: Đổi tên biến ngayDTO.getKhungGio() thành danh sách Mã Khung Giờ cho
            // rõ nghĩa)
            for (String maKhungGio : ngayDTO.getKhungGio()) {

                // VALIDATION: Kiểm tra trùng lặp (Phải update lại hàm trong Repository nhé sếp)
                boolean isExist = doctorScheduleRepository
                        .existsByBacSi_MaBacSiAndNgayLamViecAndKhungGioKham_MaKhungGio(
                                maBacSi, ngayLamViec, maKhungGio);

                if (isExist) {
                    continue; // Đã có lịch giờ này thì bỏ qua
                }

                // TÌM Entity TimeSlot từ DB
                TimeSlot timeSlot = timeSlotRepository.findById(maKhungGio)
                        .orElseThrow(() -> new RuntimeException("Không tìm thấy mã khung giờ: " + maKhungGio));

                // TẠO MỚI
                DoctorSchedule schedule = new DoctorSchedule();
                // Khuyên dùng UUID dài hơn chút để chống trùng lặp sếp nhé (Lấy 8 ký tự đầu)
                schedule.setMaLichLam("LLV-" + UUID.randomUUID().toString().substring(0, 8));
                schedule.setBacSi(doctorRepository.getReferenceById(maBacSi));
                schedule.setNgayLamViec(ngayLamViec);
                schedule.setKhungGioKham(timeSlot); // Set thẳng Object TimeSlot vào đây
                schedule.setLoaiHinhKham(ngayDTO.getLoaiHinhKham());
                schedule.setTrangThai(ngayDTO.getTrangThai());
                schedule.setMaPhongKham(ngayDTO.getMaPhongKham());
                // phòng khám nếu DTO có gửi lên
                schedulesToSave.add(schedule);
            }
        }

        if (schedulesToSave.isEmpty()) {
            throw new RuntimeException("Tất cả các khung giờ bạn chọn đều đã được tạo từ trước đó hoặc không hợp lệ!");
        }
        return doctorScheduleRepository.saveAll(schedulesToSave);
    }

    // 2. Lấy danh sách lịch khám việc
    public List<GroupedScheduleDTO> getGroupedWeeklySchedules(String maBacSi, LocalDate starDate, LocalDate endDate) {

        // 1. Lấy data từ DB
        List<DoctorSchedule> rawList = doctorScheduleRepository
                .findByBacSi_MaBacSiAndNgayLamViecBetweenOrderByNgayLamViecAscKhungGioKham_KhungGioBatDauAsc(maBacSi,
                        starDate, endDate);

        // 2. Gom nhóm thành Map
        Map<String, List<SimpleDoctorScheduleDTO>> groupedMap = rawList.stream()
                .map(schedule -> {
                    SimpleDoctorScheduleDTO dto = new SimpleDoctorScheduleDTO();
                    dto.setMaLichLam(schedule.getMaLichLam());
                    dto.setNgayLamViec(schedule.getNgayLamViec());
                    if (schedule.getKhungGioKham() != null) {
                        String gioHienThi = schedule.getKhungGioKham().getKhungGioBatDau() + " - " +
                                schedule.getKhungGioKham().getKhungGioKetThuc();
                        dto.setKhungGio(gioHienThi);
                        // dto.setMaKhungGio(schedule.getKhungGioKham().getMaKhungGio());
                    }
                    dto.setLoaiHinhKham(schedule.getLoaiHinhKham());
                    dto.setTrangThai(schedule.getTrangThai());
                    return dto;
                })
                .collect(Collectors.groupingBy(
                        // Chuyển đổi "2026-05-05" sang "Thứ 3"
                        (SimpleDoctorScheduleDTO dto) -> getDayOfWeekString(dto.getNgayLamViec()),
                        () -> new LinkedHashMap<String, List<SimpleDoctorScheduleDTO>>(),
                        Collectors.toList()));

        List<GroupedScheduleDTO> resultList = new ArrayList<>();

        // Duyệt qua từng nhóm trong Map (Entry gồm có Key là "Thứ 2", Value là danh
        // sách lịch)
        for (Map.Entry<String, List<SimpleDoctorScheduleDTO>> entry : groupedMap.entrySet()) {
            GroupedScheduleDTO groupedDTO = new GroupedScheduleDTO(entry.getKey(), entry.getValue());
            resultList.add(groupedDTO);
        }

        return resultList;
    }

    @Transactional
    public List<DoctorSchedule> updateSchedules(List<UpdateSchedulesDTO> requestList) {
        List<DoctorSchedule> updateSchedules = new ArrayList<>();
        for (UpdateSchedulesDTO dto : requestList) {
            DoctorSchedule schedule = doctorScheduleRepository.findById(dto.getMaLichLam())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy lịch làm với mã: " + dto.getMaLichLam()));
            schedule.setTrangThai(dto.getTrangThai());
            updateSchedules.add(schedule);
        }
        return doctorScheduleRepository.saveAll(updateSchedules);
    }

    private String getDayOfWeekString(LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        if (dayOfWeek == DayOfWeek.SUNDAY) {
            return "Chủ Nhật";
        } else {
            return "Thứ " + (dayOfWeek.getValue() + 1);
        }
    }
}