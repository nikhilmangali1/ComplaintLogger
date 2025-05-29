package com.nikhilmangali1.ComplaintLogger.repository;

import com.nikhilmangali1.ComplaintLogger.model.Complaint;
import com.nikhilmangali1.ComplaintLogger.model.enums.ComplaintCategory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ComplaintRepository extends MongoRepository<Complaint,String> {
    Optional<Complaint> findById(String complaintId);

    List<Complaint> findByCategory(ComplaintCategory category);

    List<Complaint> findByUserId(String userId);
}
