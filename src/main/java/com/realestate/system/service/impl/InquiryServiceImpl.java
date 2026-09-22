package com.realestate.system.service.impl;

import com.realestate.system.entity.Inquiry;
import com.realestate.system.entity.Property;
import com.realestate.system.entity.User;
import com.realestate.system.enums.UserRole;
import com.realestate.system.exception.BadRequestException;
import com.realestate.system.exception.ResourceNotFoundException;
import com.realestate.system.model.dto.request.CreateInquiryRequest;
import com.realestate.system.model.dto.request.UpdateInquiryRequest;
import com.realestate.system.model.dto.response.InquiryResponse;
import com.realestate.system.repository.InquiryRepository;
import com.realestate.system.repository.PropertyRepository;
import com.realestate.system.repository.UserRepository;
import com.realestate.system.service.InquiryService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Property not found with id: " + request.getPropertyId()
                        ));

        User authenticatedUser = getAuthenticatedUser();

        User customer;

        // Customer creates inquiry for himself
        if (authenticatedUser.getRole() == UserRole.CUSTOMER) {

            customer = authenticatedUser;

            // Do not allow customer to create an inquiry for another customer
            if (request.getCustomerId() != null &&
                    !request.getCustomerId().equals(authenticatedUser.getId())) {

                throw new BadRequestException(
                        "You can only create an inquiry for yourself"
                );
            }

        } else {

            // Admin can create an inquiry on behalf of a customer
            if (request.getCustomerId() == null) {
                throw new BadRequestException(
                        "Customer ID is required"
                );
            }

            customer = userRepository.findById(request.getCustomerId())
                    .orElseThrow(() ->
                            new ResourceNotFoundException(
                                    "Customer not found with id: "
                                            + request.getCustomerId()
                            ));
        }
        User agent = null;

        if (request.getAgentId() != null) {
            agent = userRepository.findById(request.getAgentId())
                    .orElseThrow(() -> new ResourceNotFoundException("Agent not found with id " + request.getAgentId()));
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
                .orElseThrow(() -> new ResourceNotFoundException("Inquiry not found with id: " + id));

        return mapToResponse(inquiry);
    }

    @Override
    public List<InquiryResponse> getAllInquiries() {
        User authenticatedUser = getAuthenticatedUser();

        List<Inquiry> inquiries;

        switch (authenticatedUser.getRole()) {

            case ADMIN:
                inquiries = inquiryRepository.findAll();
                break;

            case CUSTOMER:
                inquiries = inquiryRepository.findByCustomerId(
                        authenticatedUser.getId()
                );
                break;

            case AGENT:
                inquiries = inquiryRepository.findByAgentId(
                        authenticatedUser.getId()
                );
                break;

            case SELLER:
                inquiries = inquiryRepository.findByPropertyOwnerId(
                        authenticatedUser.getId()
                );
                break;

            default:
                throw new BadRequestException("Unsupported user role");
        }

        return inquiries.stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public InquiryResponse updateInquiry(Long id, UpdateInquiryRequest request) {
        Inquiry existingInquiry = inquiryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inquiry not found with id: " + id));

        if (request.getMessage() != null) {
            existingInquiry.setMessage(request.getMessage());
        }

        if(request.getStatus() != null) {
            existingInquiry.setStatus(request.getStatus());
        }

        if (request.getAgentId() != null) {
            User agent = userRepository.findById(request.getAgentId())
                    .orElseThrow(() -> new ResourceNotFoundException("Agent not found id: " + request.getAgentId()));

            existingInquiry.setAgent(agent);
        }

        Inquiry savedInquiry = inquiryRepository.save(existingInquiry);
        return mapToResponse(savedInquiry);
    }

    @Override
    public void deleteInquiry(Long id) {
        Inquiry inquiry = inquiryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inquiry not found with id: " + id));

        inquiryRepository.delete(inquiry);
    }

    private User getAuthenticatedUser() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Authenticated user not found"
                        ));
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
