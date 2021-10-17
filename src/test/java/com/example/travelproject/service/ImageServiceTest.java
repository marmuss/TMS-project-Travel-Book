package com.example.travelproject.service;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.MulticastChannel;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureMockMvc
@SpringBootTest
class ImageServiceTest {
    @Autowired
    private ImageService imageService;

    @Autowired
    private MockMvc mvc;

    @Test
    void uploadImage() {
        String uploadDir = "/photo/";
        String filename = "test.txt";
        MockMultipartFile file = new MockMultipartFile("file", "test.jpeg",
                "image/jpeg", "Spring Framework".getBytes());
        String fileName = file.getOriginalFilename();
        imageService.uploadImage(uploadDir, fileName, file);
    }
}