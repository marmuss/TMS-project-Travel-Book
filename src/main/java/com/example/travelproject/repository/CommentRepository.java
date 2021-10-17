package com.example.travelproject.repository;

import com.example.travelproject.entity.Comment;
import com.example.travelproject.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<List<Comment>> findAllByPost(Post post);
}
