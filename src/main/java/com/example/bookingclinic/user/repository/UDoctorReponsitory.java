package com.example.bookingclinic.user.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.bookingclinic.user.entity.Doctor;

@Repository
public interface UDoctorReponsitory extends JpaRepository<Doctor,String>{
    List<Doctor> findByTaiKhoan_HoVaTenContainingIgnoreCaseAndTaiKhoan_VaiTroAndPhongKham_TinhThanhPhoContainingIgnoreCase(String name,String bs, String tp);
    List<Doctor> findByPhongKham_MaPhongKham(String id);
    List<Doctor> findByPhongKham_TinhThanhPhoContainingIgnoreCase(String tp);
    List<Doctor> findByHocHamAndPhongKham_TinhThanhPhoContainingIgnoreCase(String hh, String tp);
    List<Doctor> findBySpecialty_TenChuyenKhoaContainingIgnoreCaseAndPhongKham_TinhThanhPhoContainingIgnoreCase(String name, String tp);
    List<Doctor> findBySpecialty_MaChuyenKhoaAndPhongKham_TinhThanhPhoContainingIgnoreCase(String ck, String tp);
    List<Doctor> findByHocHamAndSpecialty_MaChuyenKhoaAndPhongKham_TinhThanhPhoContainingIgnoreCase(String hh,String ck, String tp);
    Doctor findByTaiKhoan_MaTaiKhoan(String id);
}
