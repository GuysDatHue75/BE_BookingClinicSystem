package com.example.bookingclinic.user.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.bookingclinic.user.entity.Advisory;

@Repository
public interface AdvisoryRepository extends JpaRepository<Advisory,String> {
    @Query("select tv from Advisory tv where tv.trangThaiTraLoi = 1")
    List<Advisory> findAnsweredAdvisories();

    Page<Advisory> findByTrangThaiTraLoi(int state, Pageable pageable);
}
