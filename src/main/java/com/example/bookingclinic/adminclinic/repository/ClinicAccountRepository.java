package com.example.bookingclinic.adminclinic.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.bookingclinic.adminclinic.entity.AccountEntity;


@Repository
public interface ClinicAccountRepository extends JpaRepository<AccountEntity, String> {

    boolean existsBySoDt(String soDt);

    @Query("""
        SELECT DISTINCT a
        FROM AccountEntity a
        LEFT JOIN DoctorEntity d ON d.account = a
        LEFT JOIN PatientsEntity p ON p.account = a
        LEFT JOIN AppointmentScheduleEntity lk ON lk.patient = p
        WHERE LOWER(a.vaiTro) = LOWER(:vaiTro)
            AND (
                d.clinic.maPhongKham = :maPhongKham
                OR lk.doctor.clinic.maPhongKham = :maPhongKham
            )
    """)
    List<AccountEntity> findByVaiTroIgnoreCaseAndMaPhongKham(@Param("vaiTro") String vaiTro,@Param("maPhongKham") String maPhongKham);
    
    Optional<AccountEntity> findBySoDt(String soDt);

    @Query("""
        SELECT DISTINCT bs.account 
        FROM DoctorEntity bs
        WHERE bs.clinic.maPhongKham = :maPhongKham AND bs.account.vaiTro = 'BacSi'
    """)
    List<AccountEntity> findBacSiByMaPhongKham(@Param("maPhongKham") String maPhongKham);

    @Query("""
        SELECT DISTINCT p.account 
        FROM PatientsEntity p 
        JOIN AppointmentScheduleEntity lk ON p = lk.patient 
        WHERE lk.doctor.clinic.maPhongKham = :maPhongKham 
        AND p.account.vaiTro = 'BenhNhan'
    """)
    List<AccountEntity> findBenhNhanByMaPhongKham(@Param("maPhongKham") String maPhongKham);

    @Query("""
        SELECT COUNT(a) > 0 
        FROM AccountEntity a
        LEFT JOIN ClinicEntity c ON c.account = a 
        LEFT JOIN PatientsEntity p ON a = p.account 
        LEFT JOIN AppointmentScheduleEntity lk ON p = lk.patient 
        WHERE a.maTaiKhoan = :maTaiKhoan 
            AND (c.maPhongKham = :maPhongKham OR lk.doctor.clinic.maPhongKham = :maPhongKham)
    """)
    boolean checkUserBelongsToClinic(@Param("maTaiKhoan") String maTaiKhoan, @Param("maPhongKham") String maPhongKham);
}
