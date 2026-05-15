package com.example.bookingclinic.doctor.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.bookingclinic.doctor.dto.Patient.PatientmanagerDTO;
import com.example.bookingclinic.doctor.entity.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, String> {
        // 1. Tìm kiếm nếu SĐT chứa từ khóa HOẶC Tên chứa từ khóa
        @Query("SELECT p FROM Patient p " +
                        "JOIN FETCH p.taiKhoan t " + // FETCH giúp lấy kèm dữ liệu tài khoản
                        "WHERE t.vaiTro = 'BenhNhan' " +
                        "AND (:keyword IS NULL OR t.soDienThoai LIKE %:keyword% OR t.hoVaTen LIKE %:keyword%)")
        Page<Patient> searchPatients(@Param("keyword") String keyword, Pageable pageable);

        // 2 Lấy danh sách bệnh nhân
        // 2 Lấy danh sách bệnh nhân
        @Query("SELECT DISTINCT new com.example.bookingclinic.doctor.dto.Patient.PatientmanagerDTO(" +
                        "bn.maBenhNhan, tk.hoVaTen, tk.ngaySinh, tk.gioiTinh, tk.soDienThoai, tk.diaChi, tk.anhDaiDien) "
                        +
                        "FROM Appointment lk " +
                        "JOIN lk.benhNhan bn " +
                        "JOIN bn.taiKhoan tk " +
                        "JOIN lk.lichLamViec llv " +
                        "WHERE lk.trangThai = :status " +
                        "AND llv.bacSi.maBacSi = :maBacSi " +
                        "AND llv.bacSi.maPhongKham = :maPhongKham " +
                        "AND (:keyword IS NULL OR tk.hoVaTen LIKE %:keyword% OR tk.soDienThoai LIKE %:keyword%)")
        Page<PatientmanagerDTO> findDetailedPatients(
                        @Param("maBacSi") String maBacSi,
                        @Param("maPhongKham") String maPhongKham,
                        @Param("keyword") String keyword,
                        @Param("status") String status,
                        Pageable pageable);

        // // 3. Thêm bệnh nhân
        // boolean existsByBenhNhan_MaBenhNhan(String maBenhNhan);

        // Thêm hàm này để lấy ra mã bệnh nhân lớn nhất (VD: "BN05")
        @Query("SELECT MAX(p.maBenhNhan) FROM Patient p")
        String findMaxMaBenhNhan();

        // // 4. Cập nhập thông tin bệnh nhân
        // Optional<Patient> findByBenhNhan_MaBenhNhan(String maBenhNhan);

}
