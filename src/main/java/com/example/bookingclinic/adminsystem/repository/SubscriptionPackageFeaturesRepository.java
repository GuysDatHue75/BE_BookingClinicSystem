package com.example.bookingclinic.adminsystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.bookingclinic.adminsystem.entity.SubscriptionPackageFeaturesEntity;
import com.example.bookingclinic.adminsystem.entity.SubscriptionPackageFeaturesId;

@Repository
public interface SubscriptionPackageFeaturesRepository extends JpaRepository<SubscriptionPackageFeaturesEntity, SubscriptionPackageFeaturesId> {
    List<SubscriptionPackageFeaturesEntity> findBySubscriptionPackageMaGoi(String maGoi);

    List<SubscriptionPackageFeaturesEntity> findByFeaturesMaTinhNang(String maTinhNang);

    void deleteBySubscriptionPackageMaGoi(String maGoi);

}
