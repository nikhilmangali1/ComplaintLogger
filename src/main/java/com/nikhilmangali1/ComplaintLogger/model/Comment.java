package com.nikhilmangali1.ComplaintLogger.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "comments")
public class Comment {

    @Id
    private String id;
    private String complaintId;
    private String userId;
    private String content;
    private String createdAt;
}

