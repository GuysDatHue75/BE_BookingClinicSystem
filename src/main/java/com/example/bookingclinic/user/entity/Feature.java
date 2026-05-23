package com.example.bookingclinic.user.entity;

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
    private String maTinhNang;
    private String tenTinhNang;
}
