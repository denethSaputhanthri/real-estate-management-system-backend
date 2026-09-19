package com.realestate.system.service;

import com.realestate.system.model.dto.request.CreateTransactionRequest;
import com.realestate.system.model.dto.response.TransactionResponse;

import java.util.List;

public interface TransactionService {

    TransactionResponse createTransaction(
            CreateTransactionRequest request
    );

    TransactionResponse getTransactionById(Long id);

    List<TransactionResponse> getAllTransactions();

    TransactionResponse updateTransaction(
            Long id,
            CreateTransactionRequest request
    );

    void deleteTransaction(Long id);
}
