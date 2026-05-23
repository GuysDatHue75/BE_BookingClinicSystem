package com.example.bookingclinic.doctor.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.bookingclinic.doctor.entity.Advise;
import com.example.bookingclinic.doctor.repository.AdviseRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdviseService {

    private final AdviseRepository adviseRepository;

    // lấy danh sách câu hỏi
    public List<Advise> getListPending() {
        return adviseRepository.findByTrangThaiFalseOrderByThoiGianHoiAsc();
    }

    // Bác sĩ trả lời tư vấn
    @Transactional
    public Advise replyAdvise(String maTuVan, String maBacSi, String noiDungTraLoi) {
        // 1. Tìm bản ghi tư vấn
        Advise advise = adviseRepository.findById(maTuVan)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy mã tư vấn: " + maTuVan));

        // 2. Chặn lỗi Race Condition (2 bác sĩ cùng trả lời)
        if (Boolean.TRUE.equals(advise.getTrangThai())) {
            throw new RuntimeException("Câu hỏi này đã được một bác sĩ khác trả lời rồi!");
        }

        // 3. Cập nhật dữ liệu trả lời
        advise.setCauTraLoi(noiDungTraLoi);
        advise.setMaBacSi(maBacSi);
        advise.setThoiGianTraLoi(LocalDateTime.now());
        advise.setTrangThai(true); // Chuyển trạng thái sang Đã trả lời (1)

        // 4. Lưu lại vào Database
        return adviseRepository.save(advise);
    }
}