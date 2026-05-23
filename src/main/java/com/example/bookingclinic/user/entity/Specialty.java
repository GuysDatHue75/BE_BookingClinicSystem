package com.example.bookingclinic.user.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@Table(name = "chuyen_khoa")
@AllArgsConstructor
@NoArgsConstructor
public class Specialty {
    @Id
    @Column(length = 10)
    private String maChuyenKhoa;
    private String tenChuyenKhoa;
    private String moTa;
    private Boolean trangThai;
    private LocalDateTime ngayTao;
    @ManyToMany(mappedBy = "specicaltys")
    @JsonIgnore
    private List<UClinic> clinics;


    @Builder.Default
    private Boolean isDeleted = false;

}
