package com.example.bookingclinic.user.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "tinh_nang")
public class Feature {
    @Id
    @Column(name = "ma_tinh_nang")
    private String maTinhNang;
    @Column(name = "ten_tinh_nang")
    private String tenTinhNang;
}
