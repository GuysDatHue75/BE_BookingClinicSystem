package com.example.bookingclinic.adminclinic.reponsitory;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.bookingclinic.adminclinic.entity.AccountEntity;


@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, String> {
    
}
