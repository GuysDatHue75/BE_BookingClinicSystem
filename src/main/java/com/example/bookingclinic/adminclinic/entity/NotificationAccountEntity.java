package com.example.bookingclinic.adminclinic.entity;

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
@Table(name = "thong_bao_nguoi_nhan")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationAccountEntity {
    @EmbeddedId
    private NotificationAccountId id;

    @ManyToOne
    @MapsId("maThongBao")
    @JoinColumn(name = "ma_thong_bao")
    private NotificationEntity notification;

    @ManyToOne
    @MapsId("maTaiKhoan")
    @JoinColumn(name = "ma_tai_khoan")
    private AccountEntity account;

    @Column(name = "is_read")
    private Boolean isRead;

}
