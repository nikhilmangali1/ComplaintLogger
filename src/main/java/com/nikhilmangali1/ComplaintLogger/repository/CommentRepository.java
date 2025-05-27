package com.nikhilmangali1.ComplaintLogger.repository;

import com.nikhilmangali1.ComplaintLogger.model.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends MongoRepository<Comment,String> {

}
