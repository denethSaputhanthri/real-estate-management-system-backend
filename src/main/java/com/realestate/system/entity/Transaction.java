package com.realestate.system.entity;

import com.realestate.system.enums.PaymentStatus;
import com.realestate.system.enums.TransactionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_id", nullable = false)
    private Property property;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buyer_id", nullable = false)
    private User buyer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agent_id")
    private User agent;

    @Enumerated(EnumType.STRING)
    @Column(
            nullable = false,
            columnDefinition = "transaction_type"
    )
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    private TransactionType type;

    @Column(
            nullable = false,
            precision = 14,
            scale = 2
    )
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(
            nullable = false,
            columnDefinition = "payment_status_enum"
    )
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    private PaymentStatus paymentStatus;

    @Column(nullable = false)
    private LocalDate transactionDate;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {

        if (paymentStatus == null) {
            paymentStatus = PaymentStatus.PENDING;
        }

        if (transactionDate == null) {
            transactionDate = LocalDate.now();
        }

        createdAt = LocalDateTime.now();
    }
}
