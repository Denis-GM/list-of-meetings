package com.example.lom.services;

import com.example.lom.models.Tag;
import com.example.lom.repositories.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TagService {
    @Autowired
    private TagRepository tagRepository;

    public Tag saveTag(Tag tag) {
        return tagRepository.save(tag);
    }

    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }
}
