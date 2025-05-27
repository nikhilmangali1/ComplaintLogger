package com.nikhilmangali1.ComplaintLogger.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;
import java.util.List;

@Data
@Getter
@Setter
@Document(collection = "complaints")
public class Complaint {

    @Id
    private String id;
    private String complaintTitle;
    private String complaintDescription;
    private String status;
    private String userId;
    private LocalDate createdAt;
    private LocalDate updatedAt;
    private String category;
    private List<String> commentIds;
}