package com.example.bookingclinic.adminsystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.bookingclinic.adminsystem.entity.SubscriptionPackageEntity;
import com.example.bookingclinic.adminsystem.repository.custom.SubscriptionPackageRepositoryCustom;

@Repository
public interface SubscriptionPackageRepository extends JpaRepository<SubscriptionPackageEntity, String>, SubscriptionPackageRepositoryCustom {
    List<SubscriptionPackageEntity> findByTrangThai(String trangThai);

}
