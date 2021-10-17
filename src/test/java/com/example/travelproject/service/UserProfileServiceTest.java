package com.example.travelproject.service;

import com.example.travelproject.entity.Role;
import com.example.travelproject.entity.User;
import com.example.travelproject.entity.UserProfile;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureMockMvc
@SpringBootTest
class UserProfileServiceTest {
    @Autowired
    private UserProfileService userProfileService;

    User user = new User(3, "test", "test", "test@test.com", "12345678", Role.USER);

    @Test
    void save() {
        UserProfile save = userProfileService.save(user);
        Assert.assertNotNull(save);
        String photoPath = save.getPhoto();
        Assert.assertTrue(photoPath.equals("default.png"));
    }

    @Test
    void saveImage() throws IOException {
        MockMultipartFile file = new MockMultipartFile("file", "test.jpeg",
                "image/jpeg", "Spring Framework".getBytes());
        userProfileService.saveImage(file, user);
    }

    @Test
    void findUserProfileByUser() {
        UserProfile userProfile = userProfileService.findUserProfileByUser(user).get();
        String photoPath = userProfile.getPhoto();
        Assert.assertTrue(photoPath.equals("test.jpeg"));
    }

    @Test
    void findUserProfileByUserId() {
        userProfileService.findUserProfileByUserId(user.getId());
    }


    @Test
    void addCompanion() {
        UserProfile profile = userProfileService.findUserProfileByUserId(Long.valueOf(1)).get();
        userProfileService.addCompanion(6, profile);
    }
}