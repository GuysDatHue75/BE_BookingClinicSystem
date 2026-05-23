package com.example.bookingclinic.user.entity;
import jakarta.persistence.Column;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "thong_bao_nguoi_nhan")
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class AccountNotification {
    @EmbeddedId
    private NotificationAccountId id;

    @ManyToOne
    @MapsId("maThongBao")
    @JoinColumn(name = "ma_thong_bao")
    private Notification notification;

    @ManyToOne
    @MapsId("maTaiKhoan")
    @JoinColumn(name = "ma_tai_khoan")
    private UAccount account;

    @Column(name = "is_read")
    @Builder.Default
    private Boolean isRead = false;

}
