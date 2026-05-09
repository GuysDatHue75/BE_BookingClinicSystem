package com.example.bookingclinic.user.entity;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationAccountId implements Serializable{
    private String maThongBao;
    private String maTaiKhoan;
}
