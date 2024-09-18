package com.example.plantnursery.controller;
import com.example.plantnursery.DTOs.InquiryRequest;
import com.example.plantnursery.model.Inquiry;
import com.example.plantnursery.service.InquiryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/inquiries")
public class InquiryController {

    private final InquiryService inquiryService;

    @Autowired
    public InquiryController(InquiryService inquiryService) {
        this.inquiryService = inquiryService;
    }

    @GetMapping
    public ResponseEntity<List<Inquiry>> getAllInquiries() {
        List<Inquiry> inquiries = inquiryService.getAllInquiries();
        return ResponseEntity.ok(inquiries);
    }

    @PostMapping
    public ResponseEntity<Inquiry> createInquiry(@RequestBody InquiryRequest request) {
        Inquiry inquiry = inquiryService.createInquiry(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(inquiry);
    }

    @GetMapping("/{inquiryId}")
    public ResponseEntity<Inquiry> getInquiryById(@PathVariable Long inquiryId) {
        Optional<Inquiry> inquiry = inquiryService.getInquiryById(inquiryId);
        return inquiry.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}