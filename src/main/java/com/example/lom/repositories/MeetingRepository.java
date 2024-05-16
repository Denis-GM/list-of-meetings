package com.example.lom.repositories;

import com.example.lom.models.Meeting;
import com.example.lom.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MeetingRepository extends CrudRepository<Meeting, UUID> {
    List<Meeting> findByCreator(User creator);
}
