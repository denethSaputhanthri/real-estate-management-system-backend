package com.realestate.system.model.dto.request;

import com.realestate.system.enums.TransactionType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Setter
@Getter

public class CreateTransactionRequest {

    @NotNull(message = "Property ID is required")
    private Long propertyId;

    @NotNull(message = "Buyer ID is required")
    private Long buyerId;

    private Long agentId;

    @NotNull(message = "Transaction type is required")
    private TransactionType type;

    @NotNull(message = "Amount is required")
    @DecimalMin(
            value = "0.00",
            message = "Amount must be greater than or equal to 0"
    )
    private BigDecimal amount;

    private LocalDate transactionDate;
}
