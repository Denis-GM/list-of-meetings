package com.example.lom.repositories;

import com.example.lom.models.Meeting;
import com.example.lom.models.Tag;
import com.example.lom.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TagRepository extends JpaRepository<Tag, UUID> {
    Tag findByName(String name);
}
