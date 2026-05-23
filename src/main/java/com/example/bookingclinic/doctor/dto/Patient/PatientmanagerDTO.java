package com.example.bookingclinic.doctor.dto.Patient;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientmanagerDTO {
    private String maBenhNhan;
    private String hoVaTen;
    private LocalDate ngaySinh;
    private Boolean gioiTinh;
    private String soDienThoai;
    private String diaChi;
    private String avatar;

}
