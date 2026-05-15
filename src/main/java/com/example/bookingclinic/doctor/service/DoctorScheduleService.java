package com.example.bookingclinic.doctor.service;

import com.example.bookingclinic.doctor.repository.DoctorRepository;
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
import com.example.bookingclinic.doctor.dto.schedule.UpdateSchedulesDTO;
import com.example.bookingclinic.doctor.dto.schedule.WeeklyScheduleRequestDTO;
import com.example.bookingclinic.doctor.entity.DoctorSchedule;
import com.example.bookingclinic.doctor.repository.DoctorScheduleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DoctorScheduleService {

    private final DoctorRepository doctorRepository;
    private final DoctorScheduleRepository doctorScheduleRepository;

    // BẮT BUỘC CÓ: Đảm bảo nếu lỗi ở giữa chừng (VD: rớt mạng) thì sẽ HỦY BỎ toàn
    // bộ, không lưu rác vào DB
    @Transactional
    // 1. Tạo Lịch làm việc
    public List<DoctorSchedule> createWeeklySchedule(WeeklyScheduleRequestDTO requestDTO) {

        List<DoctorSchedule> schedulesToSave = new ArrayList<>();
        String maBacSi = requestDTO.getMaBacSi();

        // Lấy thời gian thực tế
        LocalDate homNay = LocalDate.now();

        // VÒNG FOR 1: Duyệt qua từng NGÀY
        for (DailyScheduleDTO ngayDTO : requestDTO.getDanhSachNgayLamViec()) {
            LocalDate ngayLamViec = ngayDTO.getNgayLamViec();

            // KHÔNG LÊN LỊCH CHO NGÀY QUÁ KHỨ
            if (ngayLamViec.isBefore(homNay)) {
                throw new RuntimeException("Không thể lên lịch cho ngày trong quá khứ: " + ngayLamViec);
            }

            // KHÔNG LÊN LỊCH VÀO CHỦ NHẬT

            if (ngayLamViec.getDayOfWeek() == DayOfWeek.SUNDAY) {
                throw new RuntimeException("Hệ thống nghỉ Chủ Nhật. Không thể lên lịch ngày: " + ngayLamViec);
            }

            // VÒNG FOR 2: Duyệt qua từng KHUNG GIỜ trong 1 ngày
            for (String khungGio : ngayDTO.getKhungGio()) {
                // BƯỚC VALIDATION: KIỂM TRA TRÙNG LẶP
                boolean isExist = doctorScheduleRepository.existsByBacSi_MaBacSiAndNgayLamViecAndKhungGio(maBacSi,
                        ngayLamViec, khungGio);
                if (isExist) {
                    continue;
                }

                // Nếu CHƯA TỒN TẠI -> Tạo mới Entity bỏ vào danh sách
                DoctorSchedule schedule = new DoctorSchedule();
                // Cấp mã Lịch làm việc mới
                schedule.setMaLichLam("LLV-" + UUID.randomUUID().toString().substring(0, 3));
                schedule.setBacSi(doctorRepository.getReferenceById(maBacSi));
                schedule.setNgayLamViec(ngayLamViec);
                schedule.setKhungGio(khungGio);
                schedule.setLoaiHinhKham(ngayDTO.getLoaiHinhKham());
                schedule.setTrangThai(ngayDTO.getTrangThai());
                schedulesToSave.add(schedule);
            }
        }

        // Kiểm tra xem giỏ hàng có trống không
        if (schedulesToSave.isEmpty()) {
            throw new RuntimeException("Tất cả các khung giờ bạn chọn đều đã được tạo từ trước đó hoặc không hợp lệ!");
        }

        // Lưu toàn bộ danh sách hợp lệ xuống Database
        return doctorScheduleRepository.saveAll(schedulesToSave);
    }

    // 2. Lấy danh sách lịch khám việc
    public List<GroupedScheduleDTO> getGroupedWeeklySchedules(String maBacSi, LocalDate starDate, LocalDate endDate) {

        // 1. Lấy data từ DB
        List<DoctorSchedule> rawList = doctorScheduleRepository
                .findByBacSi_MaBacSiAndNgayLamViecBetweenOrderByNgayLamViecAscKhungGioAsc(
                        maBacSi, starDate, endDate);

        // 2. Gom nhóm thành Map
        Map<String, List<ScheduleResponseDTO>> groupedMap = rawList.stream()
                .map(schedule -> {
                    ScheduleResponseDTO dto = new ScheduleResponseDTO();
                    dto.setMaLichLam(schedule.getMaLichLam());
                    dto.setNgayLamViec(schedule.getNgayLamViec());
                    dto.setKhungGio(schedule.getKhungGio());
                    dto.setLoaiHinhKham(schedule.getLoaiHinhKham());
                    dto.setTrangThai(schedule.getTrangThai());
                    return dto;
                })
                .collect(Collectors.groupingBy(
                        // Chuyển đổi "2026-05-05" sang "Thứ 3"
                        (ScheduleResponseDTO dto) -> getDayOfWeekString(dto.getNgayLamViec()),
                        () -> new LinkedHashMap<String, List<ScheduleResponseDTO>>(),
                        Collectors.toList()));

        List<GroupedScheduleDTO> resultList = new ArrayList<>();

        // Duyệt qua từng nhóm trong Map (Entry gồm có Key là "Thứ 2", Value là danh
        // sách lịch)
        for (Map.Entry<String, List<ScheduleResponseDTO>> entry : groupedMap.entrySet()) {
            GroupedScheduleDTO groupedDTO = new GroupedScheduleDTO(entry.getKey(), entry.getValue());
            resultList.add(groupedDTO);
        }

        return resultList;
    }

    // 3. cập nhật lịch làm việc
    @Transactional
    public List<DoctorSchedule> updateSchedules(List<UpdateSchedulesDTO> requestList) {
        List<DoctorSchedule> updateSchedules = new ArrayList<>();
        for (UpdateSchedulesDTO dto : requestList) {
            DoctorSchedule schedule = doctorScheduleRepository.findById(dto.getMaLichLam())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy lịch làm với mã : " + dto.getMaLichLam()));
            schedule.setTrangThai(dto.getTrangThai());
            updateSchedules.add(schedule);
        }
        return doctorScheduleRepository.saveAll(updateSchedules);
    }

    // Hàm phụ: Dịch ngày cụ thể (VD: 2026-05-05) sang Thứ tương ứng
    private String getDayOfWeekString(LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        if (dayOfWeek == DayOfWeek.SUNDAY) {
            return "Chủ Nhật";
        } else {
            // Giá trị của MONDAY là 1, TUESDAY là 2...
            return "Thứ " + (dayOfWeek.getValue() + 1);
        }
    }



}