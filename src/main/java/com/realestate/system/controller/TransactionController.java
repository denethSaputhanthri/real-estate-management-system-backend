package com.realestate.system.controller;

import com.realestate.system.model.dto.request.CreateTransactionRequest;
import com.realestate.system.model.dto.response.TransactionResponse;
import com.realestate.system.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<TransactionResponse> createTransaction(
            @Valid @RequestBody CreateTransactionRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(transactionService.createTransaction(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionResponse> getTransactionById(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(
                transactionService.getTransactionById(id)
        );
    }

    @GetMapping
    public ResponseEntity<List<TransactionResponse>> getAllTransactions() {
        return ResponseEntity.ok(
                transactionService.getAllTransactions()
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<TransactionResponse> updateTransaction(
            @PathVariable Long id,
            @Valid @RequestBody CreateTransactionRequest request
    ) {
        return ResponseEntity.ok(
                transactionService.updateTransaction(id, request)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(
            @PathVariable Long id
    ) {
        transactionService.deleteTransaction(id);

        return ResponseEntity.noContent().build();
    }
}
