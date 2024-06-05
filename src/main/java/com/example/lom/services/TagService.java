package com.example.lom.services;

import com.example.lom.models.Meeting;
import com.example.lom.models.Tag;
import com.example.lom.models.User;
import com.example.lom.repositories.MeetingRepository;
import com.example.lom.repositories.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class TagService {

    private final TagRepository tagRepository;

    private final MeetingService meetingService;

    @Autowired
    public TagService(MeetingService meetingService, TagRepository tagRepository) {
        this.meetingService = meetingService;
        this.tagRepository = tagRepository;

//        this.tagRepository.saveAll(List.of(
//                new Tag("all", "Все мероприятия"),
//                new Tag("games", "Игры"),
//                new Tag("sports", "Спорт"),
//                new Tag("big-team", "Большая компания"),
//                new Tag("little-team", "Маленькая компания"),
//                new Tag("outside", "На открытом воздухе")
//        ));
    }

    public Tag saveTag(Tag tag) {
        return tagRepository.save(tag);
    }

    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }

    public void assignTagToMeeting(String idMeeting, String tagName) {
        Meeting meeting = meetingService.getMeetingById(idMeeting).stream().findFirst().orElse(null);
        Tag tag = tagRepository.findByName(tagName);
        if(meeting != null && tag != null) {
            Set<Tag> tags = new HashSet<Tag>(meeting.getTags());
            tags.add(tag);
            meeting.setTags(tags);
        }
    }
}
