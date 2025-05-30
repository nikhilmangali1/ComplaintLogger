package com.nikhilmangali1.ComplaintLogger.dto;

import com.nikhilmangali1.ComplaintLogger.model.enums.Role;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class UserDTO {
    private String id;
    private String userName;
    private String email;
    private String phoneNumber;
    private Set<Role> roles;
    private List<String> complaintIds;


    public UserDTO() {
    }

}