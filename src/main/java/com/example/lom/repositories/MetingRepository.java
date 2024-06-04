package com.example.lom.repositories;

import com.example.lom.models.Meeting;
import com.example.lom.models.Meting;

import java.util.List;
import java.util.Optional;

public interface MetingRepository {
    List<Meting> findAll();

    Meting save(Meting meting);

    Optional<Meting> findById(Integer meetingId);

    void deleteById(Integer id);

}
