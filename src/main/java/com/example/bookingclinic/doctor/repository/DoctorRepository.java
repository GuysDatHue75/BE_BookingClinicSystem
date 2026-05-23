package com.example.bookingclinic.doctor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.bookingclinic.doctor.entity.Doctor;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, String> {
    // Tìm Doctor thông qua khóa ngoại liên kết với bảng Account
    Doctor findByTaiKhoan_MaTaiKhoan(String maTaiKhoan);

    Doctor findByMaBacSi(String id);
}
