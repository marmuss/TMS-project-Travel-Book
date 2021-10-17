package com.example.travelproject.service;

import com.example.travelproject.entity.*;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureMockMvc
@SpringBootTest
class PostServiceTest {
    @Autowired
    private PostService postService;

    User user = new User(3, "test", "test", "test@test.com", "12345678", Role.USER);
    Post post = new Post(4, user, "test", new Country(1, "North America", "USA", 0), "test", "test", "test.png",
            null, null, 0);

    @Test
    void saveImage() throws IOException {
        MockMultipartFile file = new MockMultipartFile("file", "test.jpeg",
                "image/jpeg", "Spring Framework".getBytes());
        postService.saveImage(file, user);
    }

    @Test
    void savePost() {
        postService.savePost(post);
    }

    @Test
    void getPostById() {
        Post postById = postService.getPostById(1);
        Assert.assertNotNull(postById.getDateTime());
    }

    @Test
    void getAll() {
        List<Post> all = postService.getAll();
    }

    @Test
    void getAllByUser() {
        postService.getAllByUser(user);
    }

    @Test
    void getAllByUserId() {
        postService.getAllByUserId(3);
    }

    @Test
    void addComment() {
        postService.addComment(user, "comment text", Long.valueOf(1));
    }

    @Test
    void addLike() {
        int likes = post.getLikes();
        postService.addLike(post);
        int newLikes = post.getLikes();
        Assert.assertTrue((newLikes - likes) == 1);
    }
}