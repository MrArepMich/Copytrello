package com.repinsky.tasktrackerlogservice.repository;

import com.repinsky.tasktrackerlogservice.model.LogEntry;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LogEntryRepository extends MongoRepository<LogEntry, String> {
}
