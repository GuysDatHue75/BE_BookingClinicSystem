package com.example.bookingclinic.user.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "thong_bao_nguoi_nhan")
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
    private Account account;

    private Integer isRead;
}
