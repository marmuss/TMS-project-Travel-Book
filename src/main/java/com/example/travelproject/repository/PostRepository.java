package com.example.travelproject.repository;

import com.example.travelproject.entity.Post;
import com.example.travelproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByUser(User user);

    List<Post> findTop3ByOrderByDateTimeDesc();
}
