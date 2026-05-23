package com.example.bookingclinic.doctor.repository.Chat;

import com.example.bookingclinic.doctor.entity.Chat.ChatRoom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, String> {
    // Tìm phòng chat giữa 2 người (bất kể ai là người tạo trước)
    Optional<ChatRoom> findByMaNguoi1AndMaNguoi2OrMaNguoi1AndMaNguoi2(
            String maNguoi1, String maNguoi2, String maNguoi2Reversed, String maNguoi1Reversed);

    // Tìm danh sách phòng chat của một người, sắp xếp theo thời gian cập nhật mới
    // nhất (phân trang)
    Page<ChatRoom> findByMaNguoi1OrMaNguoi2OrderByThoiGianCapNhatDesc(String ma1, String ma2, Pageable pageable);

}