package com.example.bookingclinic.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.bookingclinic.user.entity.UAccount;

@Repository
public interface UAccountRepository extends JpaRepository<UAccount,String>{
    UAccount findByProviderId(String providerId);
    Boolean existsBySoDt(String phone);
    Optional<UAccount> findBySoDt(String phone);
    UAccount findByProviderAndProviderId(String provider, String providerId);
    Optional<UAccount> findByEmail(String email);

}
