package com.nikhilmangali1.ComplaintLogger.service;

import com.nikhilmangali1.ComplaintLogger.model.Complaint;
import com.nikhilmangali1.ComplaintLogger.model.User;
import com.nikhilmangali1.ComplaintLogger.model.enums.ComplaintCategory;
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
        User user = userRepository.findById(complaint.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));


        complaint.setStatus(ComplaintStatus.PENDING);
        complaint.setCreatedAt(java.time.LocalDate.now());
        complaint.setUpdatedAt(java.time.LocalDate.now());

        Complaint savedComplaint = complaintRepository.save(complaint);

        List<String> complaintIds = user.getComplaintIds();
        if(!complaintIds.contains(savedComplaint.getId())){
            complaintIds.add(savedComplaint.getId());
            user.setComplaintIds(complaintIds);
            userRepository.save(user);
        }

        return complaintRepository.save(complaint);
    }

    public void deleteComplaintById(String complaintId) {
        Complaint complaint = complaintRepository.findById(complaintId)
                .orElseThrow(() -> new IllegalArgumentException("Complaint not found"));
        String userId = complaint.getUserId();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        user.getComplaintIds().remove(complaintId);
        userRepository.save(user);

        complaintRepository.deleteById(complaintId);
    }

    public Complaint updateComplaintById(String complaintId, Complaint updatedComplaint) {
        Optional<Complaint> existing = complaintRepository.findById(complaintId);
        if (existing.isEmpty()) {
            throw new IllegalArgumentException("Complaint not found");
        }

        if (updatedComplaint.getCategory() == null) {
            throw new IllegalArgumentException("Category is required");
        }

        Complaint complaint = existing.get();
        complaint.setComplaintTitle(updatedComplaint.getComplaintTitle());
        complaint.setComplaintDescription(updatedComplaint.getComplaintDescription());
        complaint.setCategory(updatedComplaint.getCategory());
        complaint.setUpdatedAt(LocalDate.now());
        return complaintRepository.save(complaint);
    }

    public Complaint withdrawComplaintById(String complaintId) {
        Optional<Complaint> existing = complaintRepository.findById(complaintId);
        if (existing.isEmpty()) {
            throw new IllegalArgumentException("Complaint not found");
        }
        Complaint complaint = existing.get();
        complaint.setStatus(ComplaintStatus.WITHDRAWN);
        complaint.setUpdatedAt(LocalDate.now());
        return complaintRepository.save(complaint);
    }

    public Complaint getComplaintById(String complaintId) {
        return complaintRepository.findById(complaintId)
                .orElseThrow(() -> new IllegalArgumentException("Complaint not found"));
    }

    public List<Complaint> getAllComplaints() {
        return complaintRepository.findAll();
    }

    public List<Complaint> getComplaintsByCategory(ComplaintCategory category) {
        return complaintRepository.findByCategory(category);
    }


}
