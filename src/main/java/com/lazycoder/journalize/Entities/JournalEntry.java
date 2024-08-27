package com.lazycoder.journalize.Entities;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
//import java.util.Date;

@Document(collection = "journalEntries")
@Data
public class JournalEntry {

    @Id
    private ObjectId id;

    private String title;

    private String content;

    private LocalDateTime Date;

    // All the boilerplate code ( like-Getter, setters, constructors) can be removed using lombok
}
