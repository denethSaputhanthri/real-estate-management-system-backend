package com.realestate.system.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreateInquiryRequest {

    @NotNull(message = "Property ID is required")
    private Long propertyId;

    @NotNull(message = "Customer ID is required")
    private Long customerId;

    private Long agentId;

    @NotBlank(message = "Message is required")
    @Size(max = 2000, message = "Message must not exceed 2000 characters")
    private String message;
}
