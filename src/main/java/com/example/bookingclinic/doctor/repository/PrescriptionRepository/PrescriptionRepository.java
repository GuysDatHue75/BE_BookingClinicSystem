package com.example.bookingclinic.doctor.repository.PrescriptionRepository;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.bookingclinic.doctor.entity.Prescription.Prescription;
import java.time.LocalDateTime;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, String> {
    @Query(value = "SELECT MAX(CAST(SUBSTRING(ma_so_don_thuoc, 3, LEN(ma_so_don_thuoc)) AS INT)) FROM don_thuoc WHERE ma_so_don_thuoc LIKE 'DT%'", nativeQuery = true)
    Integer getMaxMaSoDonThuoc();

    Page<Prescription> findByNgayLapBetween(LocalDateTime start, LocalDateTime end, Pageable pageable);
}
