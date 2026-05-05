package com.example.bookingclinic.adminclinic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.bookingclinic.adminclinic.entity.AccountEntity;


@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, String> {

    @Query(value = "SELECT MAX(CAST(SUBSTRING(ma_tai_khoan, 3, LEN(ma_tai_khoan)) AS INT)) FROM tai_khoan WHERE ma_tai_khoan LIKE 'TK%'", nativeQuery = true)
    Integer findMaxAccountIdNumber();

    boolean existsBySoDt(String soDt);
    
}
