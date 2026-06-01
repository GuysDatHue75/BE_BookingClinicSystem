package com.example.bookingclinic.doctor.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.bookingclinic.user.entity.*;
import com.example.bookingclinic.doctor.repository.AdviseRepository;
import com.example.bookingclinic.user.repository.UDoctorReponsitory;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdviseService {

    private final AdviseRepository adviseRepository;
    private final UDoctorReponsitory uDoctorReponsitory;

    // lấy danh sách câu hỏi
    public List<Advisory> getListPending() {
        return adviseRepository.findByTrangThaiTraLoiFalseOrderByThoiGianHoiAsc();
    }
    
    // Bác sĩ trả lời tư vấn
    @Transactional
    public Advisory replyAdvise(String maTuVan, String maBacSi, String noiDungTraLoi) {
        // 1. Tìm bản ghi tư vấn
        Advisory advise = adviseRepository.findById(maTuVan)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy mã tư vấn: " + maTuVan));

        // 2. Chặn lỗi Race Condition (2 bác sĩ cùng trả lời)
        if (Boolean.TRUE.equals(advise.getTrangThaiTraLoi())) {
            throw new RuntimeException("Câu hỏi này đã được một bác sĩ khác trả lời rồi!");
        }
        UDoctor doctor = uDoctorReponsitory.findById(maBacSi).orElse(null);

        // 3. Cập nhật dữ liệu trả lời
        advise.setCauTraLoi(noiDungTraLoi);
        advise.setDoctor(doctor);
        advise.setThoiGianTraLoi(LocalDateTime.now());
        advise.setTrangThai(true); // Chuyển trạng thái sang Đã trả lời (1)

        // 4. Lưu lại vào Database
        return adviseRepository.save(advise);
    }
}