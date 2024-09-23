package com.example.plantnursery.controller;

import com.example.plantnursery.DTOs.InquiryRequest;
import com.example.plantnursery.model.Inquiry;
import com.example.plantnursery.model.Staff;
import com.example.plantnursery.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/staff")
public class StaffController {

    private final StaffService staffService;

    @Autowired
    public StaffController(StaffService staffService) {
        this.staffService = staffService;
    }

// @PostMapping
// public ResponseEntity<Staff> createStaff(@RequestBody Staff staff) {
// Staff createdStaff = staffService.createStaff(staff);
// return ResponseEntity.status(HttpStatus.CREATED).body(createdStaff);
// }

    @GetMapping
    public ResponseEntity<List<Staff>> getAllStaff() {
        List<Staff> staffList = staffService.getAllStaff();
        return ResponseEntity.ok(staffList);
    }

    @GetMapping("/{staffId}")
    public ResponseEntity<Staff> getStaffById(@PathVariable Long staffId) {
        Optional<Staff> staff = staffService.getStaffById(staffId);
        return staff.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PutMapping("/{staffId}")
    public ResponseEntity<Staff> updateStaff(@PathVariable Long staffId, @RequestBody Staff staff) {
        Staff updatedStaff = staffService.updateStaff(staffId, staff);
        if (updatedStaff != null) {
            return ResponseEntity.ok(updatedStaff);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping("/{staffId}")
    public ResponseEntity<Void> deleteStaff(@PathVariable Long staffId) {
        staffService.deleteStaff(staffId);
        return ResponseEntity.noContent().build();
    }

// @PutMapping("/{staffId}/inquiries/{inquiryId}/response")
// public ResponseEntity<Inquiry> respondToInquiry(
// @PathVariable Long staffId,
// @PathVariable Long inquiryId,
// @RequestBody String response) {
// Inquiry updatedInquiry = staffService.respondToInquiry(staffId, inquiryId, response);
// if (updatedInquiry != null) {
// return ResponseEntity.ok(updatedInquiry);
// }
// return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
// }

    @PutMapping("/response/{inquiryId}")
    public ResponseEntity<Inquiry> respondToInquiry(
            @PathVariable Long inquiryId,
            @RequestBody InquiryRequest inquiryResponseRequest) {
        Inquiry updatedInquiry = staffService.respondToInquiry(inquiryId, inquiryResponseRequest.getResponse());
        if (updatedInquiry != null) {
            return ResponseEntity.ok(updatedInquiry);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

}
