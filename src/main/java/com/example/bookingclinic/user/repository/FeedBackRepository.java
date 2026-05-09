package com.example.bookingclinic.user.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.bookingclinic.user.entity.FeedBack;
@Repository
public interface FeedBackRepository extends JpaRepository<FeedBack,String> {
    List<FeedBack> findByMaPhongKham(String id);
    
}
