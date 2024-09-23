package com.example.plantnursery.service;

import com.example.plantnursery.model.Inquiry;
import com.example.plantnursery.model.Staff;
import com.example.plantnursery.repository.InquiryRepository;
import com.example.plantnursery.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class StaffService {

    private final StaffRepository staffRepository;
    private final InquiryRepository inquiryRepository;

    @Autowired
    public StaffService(StaffRepository staffRepository, InquiryRepository inquiryRepository) {
        this.staffRepository = staffRepository;
        this.inquiryRepository = inquiryRepository;
    }

    // CRUD operations for Staff
    public Staff createStaff(Staff staff) {
        return staffRepository.save(staff);
    }

    public List<Staff> getAllStaff() {
        return staffRepository.findAll();
    }

    public Optional<Staff> getStaffById(Long staffId) {
        return staffRepository.findById(staffId);
    }

    public Staff updateStaff(Long staffId, Staff updatedStaff) {
        Optional<Staff> existingStaff = staffRepository.findById(staffId);
        if (existingStaff.isPresent()) {
            Staff staff = existingStaff.get();
            staff.setName(updatedStaff.getName());
            staff.setNumber(updatedStaff.getNumber());
            staff.setEmail(updatedStaff.getEmail());
            return staffRepository.save(staff);
        }
        return null; // You can throw an exception here if needed
    }

    public void deleteStaff(Long staffId) {
        staffRepository.deleteById(staffId);
    }

    // Allow staff to respond to an inquiry
    public Inquiry respondToInquiry(Long inquiryId, String response) {
        Optional<Inquiry> inquiry = inquiryRepository.findById(inquiryId);

        if (inquiry.isPresent()) {
            Inquiry existingInquiry = inquiry.get();
            // Clean up the response string
            String cleanedResponse = response.trim().replaceAll("\\r\\n|\\r|\\n", " ");
            existingInquiry.setResponse(cleanedResponse);
            existingInquiry.setStatus(Inquiry.InquiryStatus.CLOSED);
            existingInquiry.setResponseDate(LocalDateTime.now()); // Add response date
            return inquiryRepository.save(existingInquiry);
        }
        return null; // You can throw exceptions here if necessary
    }

}
