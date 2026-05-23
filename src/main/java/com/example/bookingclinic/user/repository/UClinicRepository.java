package com.example.bookingclinic.user.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.bookingclinic.user.entity.UClinic;
@Repository
public interface UClinicRepository extends JpaRepository<UClinic, String> {
List<UClinic> findByTenPhongKhamContainingIgnoreCaseAndTinhThanhPhoContainingIgnoreCase(String name,String tp);
    Page<UClinic> findByTinhThanhPhoContainingIgnoreCase(String tp, Pageable pageable);  
    @Query("Select c from UClinic c " +
            "Join c.specicaltys s " +
            "where c.tinhThanhPho like %:tp% " +
            "and s.maChuyenKhoa = :maChuyenKhoa")
     Page<UClinic> findClinicByCityAndSpecialty(@Param("tp") String tp, @Param("maChuyenKhoa") String id, Pageable pageable);

    List<UClinic> findBySpecicaltys_TenChuyenKhoaContainingIgnoreCaseAndTinhThanhPhoContainingIgnoreCase(String name, String tp);
    UClinic findByAccount_MaTaiKhoan(String id);

    @Query(value = "select top 8 * " + 
           "from phong_kham c " +
           "where c.tinh_thanh_pho like %:tp% " +
           "order by so_sao desc",nativeQuery = true)
    List<UClinic> getClinicsTop8(@Param("tp") String tp);    
}
