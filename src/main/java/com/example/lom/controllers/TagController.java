package com.example.lom.controllers;

import com.example.lom.models.Tag;
import com.example.lom.services.TagService;
import com.example.lom.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TagController {

    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping("/api/tags/{tagName}/{fullName}")
    public Tag addTag(@PathVariable String tagName, @PathVariable String fullName) {
        return this.tagService.saveTag(new Tag(tagName, fullName));
    }

    @GetMapping("/api/tags")
    public ResponseEntity<List<Tag>> getAllTags() {
        List<Tag> tags = tagService.getAllTags();
        return ResponseEntity.ok(tags);
    }
}
