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
@RequestMapping
public class InquiryController {

    private final InquiryService inquiryService;

    @Autowired
    public InquiryController(InquiryService inquiryService) {
        this.inquiryService = inquiryService;
    }

    @GetMapping("/api/staff/inquiries")
    public ResponseEntity<List<Inquiry>> getAllInquiries() {
        List<Inquiry> inquiries = inquiryService.getAllInquiries();
        return ResponseEntity.ok(inquiries);
    }

    @PostMapping("/api/customers/inquiries")
    public ResponseEntity<Inquiry> createInquiry(@RequestBody InquiryRequest request) {
        Inquiry inquiry = inquiryService.createInquiry(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(inquiry);
    }

    @GetMapping("api/customers/inquiries/{customerId}")
    public ResponseEntity<List<Inquiry>> getInquiriesByCustomerId(@PathVariable Long customerId) {
        List<Inquiry> inquiries = inquiryService.getInquiriesByCustomerId(customerId);
        if (inquiries.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(inquiries);
    }

}