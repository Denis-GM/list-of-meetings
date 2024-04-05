package com.example.lom.repositories;

import com.example.lom.models.Record;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RecordRepository extends CrudRepository<Record, UUID> {
}
