package com.realestate.system.controller;



import com.realestate.system.model.dto.request.CreateInquiryRequest;
import com.realestate.system.model.dto.request.UpdateInquiryRequest;
import com.realestate.system.model.dto.response.InquiryResponse;
import com.realestate.system.service.InquiryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inquiries")
@RequiredArgsConstructor
public class InquiryController {

    private final InquiryService inquiryService;

    @PostMapping
    public ResponseEntity<InquiryResponse> createInquiry(
            @Valid @RequestBody CreateInquiryRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED).
                body(inquiryService.createInquiry(request));
    }

    @GetMapping
    public ResponseEntity<List<InquiryResponse>> getAllInquiry() {
        return ResponseEntity
                .ok(inquiryService.getAllInquiries());
    }

    @GetMapping("/{id}")
    public ResponseEntity<InquiryResponse> getInquiryById(
            @PathVariable Long id) {
       return ResponseEntity
               .ok(inquiryService.getInquiryById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<InquiryResponse> updateInquiry(
            @PathVariable Long id,
            @Valid @RequestBody UpdateInquiryRequest request) {
        return ResponseEntity
                .ok(inquiryService.updateInquiry(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<InquiryResponse> deleteInquiry(
            @PathVariable Long id) {
        inquiryService.deleteInquiry(id);
        return ResponseEntity.noContent().build();
    }
}
