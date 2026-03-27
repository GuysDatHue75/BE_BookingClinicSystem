package com.example.bookingclinic.user.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.bookingclinic.user.entity.UClinic;
@Repository
public interface UClinicRepository extends JpaRepository<UClinic, String> {
    List<UClinic> findByTenPhongKhamContainingIgnoreCaseAndTinhThanhPhoContainingIgnoreCase(String name,String tp);
    List<UClinic> findByTinhThanhPhoContainingIgnoreCase(String tp);  
    @Query("Select c from Clinic c " +
            "Join c.specicaltys s " +
            "where c.tinhThanhPho like %:tp% " +
            "and s.maChuyenKhoa = :maChuyenKhoa")
    List<UClinic> findClinicByCityAndSpecialty(@Param("tp") String tp, @Param("maChuyenKhoa") String id);

    List<UClinic> findBySpecicaltys_TenChuyenKhoaContainingIgnoreCaseAndTinhThanhPhoContainingIgnoreCase(String name, String tp);
    UClinic findByAccount_MaTaiKhoan(String id);
}
