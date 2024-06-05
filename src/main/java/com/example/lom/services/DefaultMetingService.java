package com.example.lom.services;

import com.example.lom.models.Meting;
import com.example.lom.repositories.MetingRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DefaultMetingService implements MetingService{
    private final MetingRepository meetingRepository;

    @Override
    public List<Meting> findAllMetings() {
        return this.meetingRepository.findAll();
    }


    @Override
    public Meting createMeting(String title, String details, Date date, String place, int totalNumberSeats, int availableSeats) {
        return this.meetingRepository.save(new Meting(null, title, details, date  , place,totalNumberSeats, availableSeats));
    }

    @Override
    public Optional<Meting> findMeeting(int meetingId) {
        return this.meetingRepository.findById(meetingId);
    }

    @Override
    public void updateMeting(Integer id, String title, String details, Date date, String place, int totalNumberSeats, int availableSeats) {
        this.meetingRepository.findById(id)
                .ifPresentOrElse(meeting -> {
                            meeting.setDetails(details);
                            meeting.setTitle(title);
                            meeting.setDate(date);
                            meeting.setPlace(place);
                            meeting.setTotalNumberSeats(totalNumberSeats);
/*                            meeting.setAvailableSeats(availableSeats);*/
                        },
                        ()->{throw new NoSuchElementException();}
                );

    }

    @Override
    public void deleteMeeting(Integer id) {
        this.meetingRepository.deleteById(id);
    }

/*    @Override
    public void updateMeeting(Integer id, String title, String details) {
        this.meetingRepository.findById(id)
                .ifPresentOrElse(product -> {
                            product.setDetails(details);
                            product.setTitle(title);
                        },
                        ()->{throw new NoSuchElementException();}
                );

    }*/

/*    @Override
    public void deleteProduct(Integer id) {
        this.meetingRepository.deleteById(id);
    }*/
}
