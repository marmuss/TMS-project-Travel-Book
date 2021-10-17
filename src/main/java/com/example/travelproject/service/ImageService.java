package com.example.travelproject.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Component
public class ImageService {

    private static Logger log = LoggerFactory.getLogger(ImageService.class);

    public void uploadImage(String uploadDir, String fileName, MultipartFile multipartFile) {
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            log.info("create directory for photo");
            try {
                Files.createDirectories(uploadPath);
            } catch (IOException e) {
                log.error("failed to create photo directory" + e);
            }
        }
        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            log.info("image " + fileName + " upload");
        } catch (IOException e) {
                log.error("failed to save image " + fileName + e);
        }
    }
}
