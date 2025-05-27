package com.nikhilmangali1.ComplaintLogger.repository;

import com.nikhilmangali1.ComplaintLogger.model.Complaint;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComplaintRepository extends MongoRepository<Complaint,String> {
}
