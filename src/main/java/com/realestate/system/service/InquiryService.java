package com.realestate.system.service;

import com.realestate.system.model.dto.request.CreateInquiryRequest;
import com.realestate.system.model.dto.request.UpdateInquiryRequest;
import com.realestate.system.model.dto.response.InquiryResponse;

import java.util.List;

public interface InquiryService {
    InquiryResponse createInquiry(CreateInquiryRequest request);

    InquiryResponse getInquiryById(Long id);

    List<InquiryResponse> getAllInquiries();

    InquiryResponse updateInquiry(Long id, UpdateInquiryRequest request);

    void deleteInquiry(Long id);
}
