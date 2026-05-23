package com.example.bookingclinic.doctor.repository.Chat;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.bookingclinic.doctor.entity.Chat.Chat;

import jakarta.transaction.Transactional;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Integer> {
    // Lấy toàn bộ tin nhắn của 1 phòng, sắp xếp theo thời gian cũ -> mới
    List<Chat> findByMaPhongChatOrderByThoiGianGuiAsc(String maPhongChat);

    Page<Chat> findByMaPhongChatOrderByThoiGianGuiDesc(String maPhongChat, Pageable pageable);

    Optional<Chat> findFirstByMaPhongChatOrderByThoiGianGuiDesc(String maPhongChat);

    long countByMaPhongChatAndMaNguoiNhanAndDaXem(String maPhongChat, String maNguoiNhan, Boolean daXem);

    @Modifying
    @Transactional
    @Query("UPDATE Chat c SET c.daXem = true " +
            "WHERE c.maPhongChat = :roomId " +
            "AND c.maNguoiNhan = :userId " +
            "AND c.daXem = false")
    void markMessagesAsRead(String roomId, String userId);
}