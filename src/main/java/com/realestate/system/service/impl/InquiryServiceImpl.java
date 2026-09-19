package com.realestate.system.service.impl;

import com.realestate.system.entity.Inquiry;
import com.realestate.system.entity.Property;
import com.realestate.system.entity.User;
import com.realestate.system.model.dto.request.CreateInquiryRequest;
import com.realestate.system.model.dto.request.UpdateInquiryRequest;
import com.realestate.system.model.dto.response.InquiryResponse;
import com.realestate.system.repository.InquiryRepository;
import com.realestate.system.repository.PropertyRepository;
import com.realestate.system.repository.UserRepository;
import com.realestate.system.service.InquiryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InquiryServiceImpl implements InquiryService {

    private  final InquiryRepository inquiryRepository;
    private final PropertyRepository propertyRepository;
    private final UserRepository userRepository;

    @Override
    public InquiryResponse createInquiry(CreateInquiryRequest request) {

        Property property = propertyRepository.findById(request.getPropertyId())
                .orElseThrow(() -> new RuntimeException("Property not found"));

        User customer = userRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        User agent = null;

        if (request.getAgentId() != null) {
            agent = userRepository.findById(request.getAgentId())
                    .orElseThrow(() -> new RuntimeException("Agent not found"));
        }

        Inquiry inquiry = new Inquiry();
        inquiry.setProperty(property);
        inquiry.setCustomer(customer);
        inquiry.setAgent(agent);
        inquiry.setMessage(request.getMessage());

        Inquiry savedInquiry = inquiryRepository.save(inquiry);
        return mapToResponse(savedInquiry);
    }

    @Override
    public InquiryResponse getInquiryById(Long id) {
        Inquiry inquiry = inquiryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inquiry not found"));

        return mapToResponse(inquiry);
    }

    @Override
    public List<InquiryResponse> getAllInquiries() {
        return inquiryRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public InquiryResponse updateInquiry(Long id, UpdateInquiryRequest request) {
        Inquiry existingInquiry = inquiryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inquiry not found"));

        if (request.getMessage() != null) {
            existingInquiry.setMessage(request.getMessage());
        }

        if(request.getStatus() != null) {
            existingInquiry.setStatus(request.getStatus());
        }

        if (request.getAgentId() != null) {
            User agent = userRepository.findById(request.getAgentId())
                    .orElseThrow(() -> new RuntimeException("Agent not found"));

            existingInquiry.setAgent(agent);
        }

        Inquiry savedInquiry = inquiryRepository.save(existingInquiry);
        return mapToResponse(savedInquiry);
    }

    @Override
    public void deleteInquiry(Long id) {
        Inquiry inquiry = inquiryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inquiry not found"));

        inquiryRepository.delete(inquiry);
    }

    private InquiryResponse mapToResponse(Inquiry inquiry) {

        InquiryResponse response = new InquiryResponse();

        response.setId(inquiry.getId());

        if (inquiry.getProperty() != null) {
            response.setPropertyId(
                    inquiry.getProperty().getId()
            );
        }

        if (inquiry.getCustomer() != null) {
            response.setCustomerId(
                    inquiry.getCustomer().getId()
            );
        }

        if (inquiry.getAgent() != null) {
            response.setAgentId(
                    inquiry.getAgent().getId()
            );
        }

        response.setMessage(inquiry.getMessage());
        response.setStatus(inquiry.getStatus());
        response.setCreatedAt(inquiry.getCreatedAt());
        response.setUpdatedAt(inquiry.getUpdatedAt());

        return response;
    }
}
