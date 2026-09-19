package com.realestate.system.model.dto.response;

import com.realestate.system.enums.BookingStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class BookingResponse {

    private Long id;

    private Long inquiryId;

    private Long propertyId;

    private Long customerId;

    private Long agentId;

    private LocalDateTime visitDate;

    private BookingStatus status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
