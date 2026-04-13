package com.example.bookingclinic.adminsystem.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.bookingclinic.adminsystem.entity.AccountEntity;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, String> {
    //basic
    Optional<AccountEntity> findBySoDt(String soDt);

    boolean existsBySoDt(String soDt);

}
