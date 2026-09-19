package com.realestate.system.model.dto.response;

import com.realestate.system.enums.InquiryStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class InquiryResponse {

    private Long id;

    private Long propertyId;

    private Long customerId;

    private Long agentId;

    private String message;

    private InquiryStatus status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
