package com.example.lom.services;

import com.example.lom.models.Record;
import com.example.lom.models.User;
import com.example.lom.repositories.MeetingRepository;
import com.example.lom.repositories.RecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class RecordService {
    private final RecordRepository recordRepository;

    @Autowired
    public RecordService(RecordRepository recordRepository) {
        this.recordRepository = recordRepository;
    }

    public Optional<Record> getRecordById(String id) {
        return this.recordRepository.findById(UUID.fromString(id));
    }

    public Record postRecord(Record record) {
        this.recordRepository.save(record);
        return record;
    }

    public void deleteRecord(String id) {
        this.recordRepository.deleteById(UUID.fromString(id));
    }
}
