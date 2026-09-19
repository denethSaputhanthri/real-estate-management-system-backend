package com.realestate.system.service.impl;

import com.realestate.system.entity.Property;
import com.realestate.system.entity.Transaction;
import com.realestate.system.entity.User;
import com.realestate.system.model.dto.request.CreateTransactionRequest;
import com.realestate.system.model.dto.response.TransactionResponse;
import com.realestate.system.repository.PropertyRepository;
import com.realestate.system.repository.TransactionRepository;
import com.realestate.system.repository.UserRepository;
import com.realestate.system.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final PropertyRepository propertyRepository;
    private final UserRepository userRepository;

    @Override
    public TransactionResponse createTransaction(CreateTransactionRequest request) {

        Property property = propertyRepository.findById(request.getPropertyId())
                .orElseThrow(() -> new RuntimeException("Property not found"));

        User buyer = userRepository.findById(request.getBuyerId())
                .orElseThrow(() -> new RuntimeException("Buyer not found"));

        User agent = null;

        if (request.getAgentId() != null) {
            agent = userRepository.findById(request.getAgentId())
                    .orElseThrow(() -> new RuntimeException("Agent not found"));
        }
        Transaction transaction = new Transaction();

        transaction.setProperty(property);
        transaction.setBuyer(buyer);
        transaction.setAgent(agent);
        transaction.setType(request.getType());
        transaction.setAmount(request.getAmount());
        transaction.setTransactionDate(
                request.getTransactionDate()
        );

        Transaction savedTransaction =
                transactionRepository.save(transaction);

        return mapToResponse(savedTransaction);
    }

    @Override
    public TransactionResponse getTransactionById(Long id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found (SEARCH BY ID)"));

        return mapToResponse(transaction);
    }

    @Override
    public List<TransactionResponse> getAllTransactions() {
        return transactionRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public TransactionResponse updateTransaction(Long id, CreateTransactionRequest request) {

        Transaction existingTransaction = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        if (request.getPropertyId() != null) {

            Property property = propertyRepository.findById(
                    request.getPropertyId()
            ).orElseThrow(() ->
                    new RuntimeException("Property not found")
            );

            existingTransaction.setProperty(property);
        }

        if (request.getBuyerId() != null) {

            User buyer = userRepository.findById(
                    request.getBuyerId()
            ).orElseThrow(() ->
                    new RuntimeException("Buyer not found")
            );

            existingTransaction.setBuyer(buyer);
        }

        if (request.getAgentId() != null) {

            User agent = userRepository.findById(
                    request.getAgentId()
            ).orElseThrow(() ->
                    new RuntimeException("Agent not found")
            );

            existingTransaction.setAgent(agent);
        }
        if (request.getType() != null) {
            existingTransaction.setType(request.getType());
        }

        if (request.getAmount() != null) {
            existingTransaction.setAmount(request.getAmount());
        }

        if (request.getTransactionDate() != null) {
            existingTransaction.setTransactionDate(
                    request.getTransactionDate()
            );
        }

        Transaction updatedTransaction =
                transactionRepository.save(existingTransaction);

        return mapToResponse(updatedTransaction);
    }

    @Override
    public void deleteTransaction(Long id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));
        transactionRepository.delete(transaction);
    }

    private TransactionResponse mapToResponse(Transaction transaction) {

        TransactionResponse response = new TransactionResponse();
        response.setId(transaction.getId());

        if (transaction.getProperty() != null) {
            response.setPropertyId(
                    transaction.getProperty().getId()
            );
        }

        if (transaction.getBuyer() != null) {
            response.setBuyerId(
                    transaction.getBuyer().getId()
            );
        }

        if (transaction.getAgent() != null) {
            response.setAgentId(
                    transaction.getAgent().getId()
            );
        }

        response.setType(transaction.getType());
        response.setAmount(transaction.getAmount());
        response.setPaymentStatus(transaction.getPaymentStatus());
        response.setTransactionDate(transaction.getTransactionDate());
        response.setCreatedAt(transaction.getCreatedAt());

        return response;
    }
}
