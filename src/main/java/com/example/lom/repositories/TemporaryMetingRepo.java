package com.example.lom.repositories;

import com.example.lom.models.Meting;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.IntStream;

@Repository
public class TemporaryMetingRepo implements MetingRepository {
    private final List<Meting> meetings = Collections.synchronizedList(new LinkedList<>());

    public TemporaryMetingRepo(){}

    @Override
    public List<Meting> findAll() {
        return Collections.unmodifiableList(this.meetings);
    }


    @Override
    public Meting save(Meting meting) {
        meting.setId(this.meetings.stream()
                .max(Comparator.comparingInt(Meting::getId))
                .map(Meting::getId)
                .orElse(0) + 1);
        this.meetings.add(meting);
        return meting;
    }

    @Override
    public Optional<Meting> findById(Integer meetingId) {
        return this.meetings.stream()
                .filter(mt -> Objects.equals(meetingId, mt.getId()))
                .findFirst();
    }
    @Override
    public void deleteById(Integer id) {
        this.meetings.removeIf(product -> Objects.equals(id,product.getId()));
    }
}
