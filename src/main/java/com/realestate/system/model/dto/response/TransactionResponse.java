package com.realestate.system.model.dto.response;

import com.realestate.system.enums.PaymentStatus;
import com.realestate.system.enums.TransactionType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Setter
@Getter
public class TransactionResponse {

    private Long id;

    private Long propertyId;

    private Long buyerId;

    private Long agentId;

    private TransactionType type;

    private BigDecimal amount;

    private PaymentStatus paymentStatus;

    private LocalDate transactionDate;

    private LocalDateTime createdAt;
}
