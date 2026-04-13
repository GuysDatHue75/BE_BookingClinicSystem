package com.example.bookingclinic.adminsystem.repository;

import com.example.bookingclinic.adminsystem.entity.DoctorEntity;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepository extends JpaRepository<DoctorEntity, String> {
    Optional<DoctorEntity> findByEmail(String email);

    Optional<DoctorEntity> findBySoDienThoai(String soDienThoai);

    boolean existexistsByEmail(String email);

    boolean existsBySoDienThoai(String soDienThoai);

    List<DoctorEntity> findByTenBacSiContainingIgnoreCase(String keyword);

    List<DoctorEntity> findByChuyenKhoaContainingIgnoreCase(String chuyenKhoa);

    List<DoctorEntity> findByChuyenKhoaAndGioiTinh(String chuyenKhoa, boolean gioiTinh);

    List<DoctorEntity> findByMaPhongKham(String maPhongKham);

    List<DoctorEntity> findByMaTaiKhoan(String maTaiKhoan);

    List<DoctorEntity> findByChucVu(String chucVu);

    List<DoctorEntity> findByHocHam(String hocHam);

    List<DoctorEntity> findByChuyenKhoaOrderByNgayCapDesc(String chuyenKhoa);

    List<DoctorEntity> findByAvtIsNotNull();

}
