package com.example.bookingclinic.adminsystem.entity;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationAccountId implements Serializable{
    private String maThongBao;
    private String maTaiKhoan;
}
