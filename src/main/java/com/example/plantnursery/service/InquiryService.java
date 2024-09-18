package com.example.plantnursery.service;

import com.example.plantnursery.DTOs.InquiryRequest;
import com.example.plantnursery.model.Inquiry;
import com.example.plantnursery.repository.InquiryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class InquiryService {

    private final InquiryRepository inquiryRepository;

    @Autowired
    public InquiryService(InquiryRepository inquiryRepository) {
        this.inquiryRepository = inquiryRepository;
    }

    public List<Inquiry> getAllInquiries() {
        return inquiryRepository.findAll();
    }

    public Inquiry createInquiry(InquiryRequest request) {
        Inquiry inquiry = new Inquiry();
        inquiry.setCustomerId(request.getCustomerId());
        inquiry.setSubject(request.getSubject());
        inquiry.setMessage(request.getMessage());
        inquiry.setInquiryDate(LocalDateTime.now());
        inquiry.setStatus(Inquiry.InquiryStatus.OPEN);
        return inquiryRepository.save(inquiry);
    }

    public Optional<Inquiry> getInquiryById(Long inquiryId) {
        return inquiryRepository.findById(inquiryId);
    }
}
