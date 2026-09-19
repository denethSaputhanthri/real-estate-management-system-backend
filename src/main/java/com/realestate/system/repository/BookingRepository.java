package com.realestate.system.repository;

import com.realestate.system.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    Optional<Booking> findByInquiryId(Long inquiryId);

    boolean existsByInquiryId(Long inquiryId);
}
