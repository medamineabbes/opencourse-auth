package com.opencourse.authusermanagement.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "sequence")
public class Sequence {
    @Id
    private String id;
    private Long seq;
}
