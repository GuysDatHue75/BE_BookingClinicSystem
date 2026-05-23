package com.example.bookingclinic.user.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.bookingclinic.user.entity.Package;

@Repository
public interface PackageRepository extends JpaRepository<Package, String>{

    List<Package> findByIsDeletedFalse();

}
