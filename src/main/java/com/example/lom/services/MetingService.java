package com.example.lom.services;

import com.example.lom.models.Meting;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface MetingService {
    List<Meting> findAllMetings();

    Meting createMeting(String title, String details, Date date, String place, int totalNumberSeats, int availableSeats);


    Optional<Meting> findMeeting(int meetingId);

    void updateMeting(Integer id, String title, String details, Date date, String place, int totalNumberSeats, int availableSeats);

    void deleteMeeting(Integer id);

}
