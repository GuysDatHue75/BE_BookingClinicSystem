package com.example.bookingclinic.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.bookingclinic.user.entity.Account;

@Repository
public interface UAccountRepository extends JpaRepository<Account,String>{
    Account findByProviderId(String providerId);
    Boolean existsBySoDt(String phone);
    Optional<Account> findBySoDt(String phone);
    Account findByProviderAndProviderId(String provider, String providerId);
    Optional<Account> findByEmail(String email);

}
