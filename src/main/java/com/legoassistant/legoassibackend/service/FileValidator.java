package com.legoassistant.legoassibackend.service;

import com.legoassistant.legoassibackend.exception.FileProcessingException;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.io.IOException;

public class FileValidator {

    public static void validate(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new FileProcessingException("Uploaded file is invalid.");
        }
        validateImage(file);
    }

    private static void validateImage(MultipartFile file) {
        try {
            if (ImageIO.read(file.getInputStream()) == null) {
                throw new FileProcessingException("File is not a valid image.");
            }
        } catch (IOException e) {
            throw new FileProcessingException("Error while reading the file.", e);
        }
    }
}

