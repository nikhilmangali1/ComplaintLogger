package com.nikhilmangali1.ComplaintLogger.repository;

import com.nikhilmangali1.ComplaintLogger.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User,String> {

    User findByUserName(String userName);
}
