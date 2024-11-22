package com.repinsky.tasktrackerlogservice.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "logs")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LogEntry {
    @Id
    private String id;

    private String message;

    @Indexed
    private Date timeStamp;

    @Indexed
    private String level;
}
