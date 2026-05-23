package com.example.bookingclinic.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "anh_ho_so_kham")
public class FileImage {
    @Id
    private Integer maAnh;
    @ManyToOne
    @JoinColumn(name = "ma_ho_so")
    @JsonIgnore
    private File file;
    private String duongDanAnh;
    private String tenAnh;
}
