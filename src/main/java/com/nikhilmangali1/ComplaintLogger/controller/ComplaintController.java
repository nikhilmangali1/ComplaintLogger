package com.nikhilmangali1.ComplaintLogger.controller;

import com.nikhilmangali1.ComplaintLogger.model.Complaint;
import com.nikhilmangali1.ComplaintLogger.model.enums.ComplaintCategory;
import com.nikhilmangali1.ComplaintLogger.repository.ComplaintRepository;
import com.nikhilmangali1.ComplaintLogger.service.ComplaintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/complaints")
public class ComplaintController {

    @Autowired
    private ComplaintService complaintService;
    @Autowired
    private ComplaintRepository complaintRepository;

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/raiseComplaint")
    public Complaint raiseComplaint(@RequestBody Complaint complaint){
        return complaintService.raiseComplaint(complaint);
    }

    @DeleteMapping("/deleteComplaint/{complaintId}")
    public void deleteComplaint(@PathVariable String complaintId){
        complaintService.deleteComplaint(complaintId);
    }

    @PutMapping("/updateComplaint/{complaintId}")
    public Complaint updateComplaint(@PathVariable String complaintId,@RequestBody Complaint updatedComplaint) {
        return complaintService.updateComplaint(complaintId, updatedComplaint);
    }

    @PutMapping("/withdrawComplaint/{complaintId}")
    public Complaint withdrawComplaint(@PathVariable String complaintId){
        return complaintService.withdrawComplaint(complaintId);
    }

    @GetMapping("/getComplaint/{complaintId}")
    public Complaint getComplaint(@PathVariable String complaintId) {
        return complaintService.getComplaint(complaintId);
    }


    @GetMapping("/all")
    public List<Complaint> getAllComplaints() {
        return complaintService.getAllComplaints();
    }


    // In ComplaintController.java
    @GetMapping("/category/{category}")
    public List<Complaint> getComplaintsByCategory(@PathVariable ComplaintCategory category) {
        return complaintService.getComplaintsByCategory(category);
    }

}
