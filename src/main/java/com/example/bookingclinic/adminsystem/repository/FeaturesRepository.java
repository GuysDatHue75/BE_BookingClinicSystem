package com.example.bookingclinic.adminsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.bookingclinic.adminsystem.entity.FeaturesEntity;

@Repository
public interface FeaturesRepository extends JpaRepository<FeaturesEntity, String> {

}
