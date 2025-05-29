package com.nikhilmangali1.ComplaintLogger.model;

import com.nikhilmangali1.ComplaintLogger.model.enums.Role;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Collection;
import java.util.Set;


@Data
@Document(collection = "users")
public class User {

    @Id
    public String id;
    private String userName;
    private String email;
    private String password;
    private Set<Role> roles;
    private Collection<String> complaints;
    private String phoneNumber;
    private String address;
}