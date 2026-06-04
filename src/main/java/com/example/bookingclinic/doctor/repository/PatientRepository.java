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
                        "AND (:keyword IS NULL OR t.soDt LIKE %:keyword% OR t.hoVaTen LIKE %:keyword%)")
        Page<Patient> searchPatients(@Param("keyword") String keyword, Pageable pageable);

        // 2. Lấy danh sách bệnh nhân (Đã lọc trùng bằng DISTINCT)
        @Query("SELECT DISTINCT new com.example.bookingclinic.doctor.dto.Patient.PatientmanagerDTO(" +
                        "p.maBenhNhan, " +
                        "acc.hoVaTen, " +
                        "p.ngaySinh, " +
                        "p.gioiTinh, " +
                        "acc.soDt, " +
                        "p.diaChi, " +
                        "acc.anhDaiDien) " +
                        "FROM Appointment a " +
                        "JOIN a.benhNhan p " +
                        "JOIN p.taiKhoan acc " +
                        "JOIN a.lichLamViec ds " +
                        "WHERE ds.bacSi.maBacSi = :maBacSi " +
                        "AND ds.maPhongKham = :maPhongKham " +
                        "AND a.trangThai = :trangThai " +
                        "AND (:keyword IS NULL OR acc.hoVaTen LIKE %:keyword% OR p.maBenhNhan LIKE %:keyword%)")
        Page<PatientmanagerDTO> findDetailedPatients(
                        @Param("maBacSi") String maBacSi,
                        @Param("maPhongKham") String maPhongKham,
                        @Param("keyword") String keyword,
                        @Param("trangThai") String trangThai,
                        Pageable pageable);
        // // 3. Thêm bệnh nhân
        // boolean existsByBenhNhan_MaBenhNhan(String maBenhNhan);

        // Thêm hàm này để lấy ra mã bệnh nhân lớn nhất (VD: "BN05")
        @Query("SELECT MAX(p.maBenhNhan) FROM Patient p")
        String findMaxMaBenhNhan();

        // // 4. Cập nhập thông tin bệnh nhân
        // Optional<Patient> findByBenhNhan_MaBenhNhan(String maBenhNhan);

        // Tìm Patient thông qua khóa ngoại liên kết với bảng Account
        Patient findByTaiKhoan_MaTaiKhoan(String maTaiKhoan);

}
