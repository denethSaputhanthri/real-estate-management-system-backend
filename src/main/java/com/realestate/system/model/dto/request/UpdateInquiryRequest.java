package com.realestate.system.model.dto.request;

import com.realestate.system.enums.InquiryStatus;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdateInquiryRequest {

    @Size(max = 2000, message = "Message must not exceed 2000 characters")
    private String message;

    private InquiryStatus status;

    private Long agentId;
}
