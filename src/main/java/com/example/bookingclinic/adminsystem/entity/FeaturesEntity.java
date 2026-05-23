package com.example.bookingclinic.adminsystem.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "tinh_nang")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FeaturesEntity {
    @Id
    @Column(name = "ma_tinh_nang", length = 10, nullable = false)
    private String maTinhNang;

    @Column(name = "ten_tinh_nang", length = 255, nullable = false)
    private String tenTinhNang;
}
