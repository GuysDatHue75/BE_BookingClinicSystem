package com.example.bookingclinic.user.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.bookingclinic.user.entity.Doctor;

@Repository
public interface UDoctorReponsitory extends JpaRepository<Doctor,String>{
    List<Doctor> findByTaiKhoan_HoVaTenContainingIgnoreCaseAndTaiKhoan_VaiTroAndPhongKham_TinhThanhPhoContainingIgnoreCase(String name,String bs, String tp);


    List<Doctor> findByPhongKham_MaPhongKham(String id);

    Page<Doctor> findByPhongKham_TinhThanhPhoContainingIgnoreCase(String tp, Pageable pageable);

    Page<Doctor> findByHocHamAndPhongKham_TinhThanhPhoContainingIgnoreCase(String hh, String tp, Pageable pageable);

    List<Doctor> findBySpecialty_TenChuyenKhoaContainingIgnoreCaseAndPhongKham_TinhThanhPhoContainingIgnoreCase(
            String name, String tp);

    Page<Doctor> findBySpecialty_MaChuyenKhoaAndPhongKham_TinhThanhPhoContainingIgnoreCase(String ck, String tp,
            Pageable pageable);

    Page<Doctor> findByHocHamAndSpecialty_MaChuyenKhoaAndPhongKham_TinhThanhPhoContainingIgnoreCase(String hh,
            String ck, String tp, Pageable pageable);

    Doctor findByTaiKhoan_MaTaiKhoan(String id);

    @Query(value = "select top 10 bs.* " +
            "from bac_si bs " +
            "join phong_kham pk on bs.ma_phong_kham = pk.ma_phong_kham " +
            "where pk.tinh_thanh_pho like %:tp% " +
            "order BY pk.so_sao DESC ", nativeQuery = true)
    List<Doctor> getDoctorsTop8(@Param("tp") String city);
}
