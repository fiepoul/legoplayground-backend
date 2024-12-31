package com.legoassistant.legoassibackend.service;

import com.legoassistant.legoassibackend.exception.ValidationException;
import org.springframework.web.multipart.MultipartFile;

public class FileValidator {

    public static void validate(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new ValidationException("Uploaded file cannot be null or empty.");
        }
    }
}

