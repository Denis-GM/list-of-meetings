package com.example.lom.controllers;

import com.example.lom.models.Tag;
import com.example.lom.services.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TagController {
    @Autowired
    private TagService tagService;

    @GetMapping("/api/tags/{tagName}")
    public Tag addTag(@PathVariable String tagName) {
        return tagService.saveTag(new Tag(tagName));
    }

    @GetMapping("/api/tags")
    public ResponseEntity<List<Tag>> getAllTags() {
        List<Tag> tags = tagService.getAllTags();
        return ResponseEntity.ok(tags);
    }
}
