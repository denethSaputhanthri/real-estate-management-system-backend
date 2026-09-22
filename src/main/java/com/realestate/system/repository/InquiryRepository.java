package com.realestate.system.repository;

import com.realestate.system.entity.Inquiry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InquiryRepository extends JpaRepository<Inquiry, Long> {

    List<Inquiry> findByCustomerId(Long customerId);

    List<Inquiry> findByAgentId(Long agentId);

    List<Inquiry> findByPropertyOwnerId(Long ownerId);
}
