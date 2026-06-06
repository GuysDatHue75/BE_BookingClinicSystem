package com.example.bookingclinic.adminsystem.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.bookingclinic.adminclinic.entity.AccountEntity;
import com.example.bookingclinic.adminsystem.repository.custom.AccountRepositoryCustom;

@Repository
public interface SystemAccountRepository extends JpaRepository<AccountEntity, String>, AccountRepositoryCustom {
    
    List<AccountEntity> findByVaiTroIgnoreCase(String vaiTro);
    
    Optional<AccountEntity> findBySoDt(String soDt);

    boolean existsBySoDt(String soDt);

    boolean existsByEmail(String email);

    boolean existsBySoDtAndMaTaiKhoanNot(String soDt, String maTaiKhoan);
    boolean existsByEmailAndMaTaiKhoanNot(String email, String maTaiKhoan);

}
