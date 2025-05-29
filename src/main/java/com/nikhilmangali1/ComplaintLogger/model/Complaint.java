package com.nikhilmangali1.ComplaintLogger.model;

import com.nikhilmangali1.ComplaintLogger.model.enums.ComplaintStatus;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;

@Data
@Document(collection = "complaints")
public class Complaint {

    @Id
    private String id;
    private String complaintTitle;
    private String complaintDescription;
    private ComplaintStatus status;
    private String userId;
    private LocalDate createdAt;
    private LocalDate updatedAt;
    private String category;
}