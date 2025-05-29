package com.nikhilmangali1.ComplaintLogger.service;

import com.nikhilmangali1.ComplaintLogger.model.Complaint;
import com.nikhilmangali1.ComplaintLogger.model.enums.ComplaintStatus;
import com.nikhilmangali1.ComplaintLogger.repository.ComplaintRepository;
import com.nikhilmangali1.ComplaintLogger.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ComplaintService {

    @Autowired
    private ComplaintRepository complaintRepository;
    @Autowired
    private UserRepository userRepository;


    public Complaint raiseComplaint(Complaint complaint) {
        if(userRepository.findById(complaint.getUserId()).isEmpty()){
            throw new IllegalArgumentException("User not found");
        }
        complaint.setStatus(ComplaintStatus.PENDING);
        complaint.setCreatedAt(java.time.LocalDate.now());
        complaint.setUpdatedAt(java.time.LocalDate.now());
        return complaintRepository.save(complaint);
    }

    public void deleteComplaint(String complaintId) {
        complaintRepository.deleteById(complaintId);
    }

    public Complaint updateComplaint(String complaintId,Complaint updatedComplaint) {
        Optional<Complaint> existing = complaintRepository.findById(complaintId);
        if (existing.isEmpty()) {
            throw new IllegalArgumentException("Complaint not found");
        }
        Complaint complaint = existing.get();
        complaint.setComplaintTitle(updatedComplaint.getComplaintTitle());
        complaint.setComplaintDescription(updatedComplaint.getComplaintDescription());
        complaint.setCategory(updatedComplaint.getCategory());
        complaint.setUpdatedAt(LocalDate.now());
        return complaintRepository.save(complaint);
    }

    public Complaint withdrawComplaint(String complaintId) {
        Optional<Complaint> existing = complaintRepository.findById(complaintId);
        if (existing.isEmpty()) {
            throw new IllegalArgumentException("Complaint not found");
        }
        Complaint complaint = existing.get();
        complaint.setStatus(ComplaintStatus.WITHDRAWN);
        complaint.setUpdatedAt(LocalDate.now());
        return complaintRepository.save(complaint);
    }

    public Complaint getComplaint(String complaintId) {
        return complaintRepository.findById(complaintId)
                .orElseThrow(() -> new IllegalArgumentException("Complaint not found"));
    }

    public List<Complaint> getAllComplaints() {
        return complaintRepository.findAll();
    }


}
