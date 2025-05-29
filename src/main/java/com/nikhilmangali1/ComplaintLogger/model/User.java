package com.nikhilmangali1.ComplaintLogger.model;

import com.nikhilmangali1.ComplaintLogger.model.enums.Role;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
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
    @Getter
    @Setter
    private List<String> complaintIDs = new ArrayList<>();
    private String phoneNumber;
    private String address;

    public User() {
    }

    public List<String> getComplaintIds() {
        if (complaintIDs == null) {
            complaintIDs = new ArrayList<>();
        }
        return complaintIDs;
    }

    public void setComplaintIds(List<String> complaintIDs) {
        if (complaintIDs == null) {
            this.complaintIDs = new ArrayList<>();
        } else {
            this.complaintIDs = complaintIDs;
        }
    }
}