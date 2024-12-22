package com.legoassistant.legoassibackend.service;

import org.springframework.web.multipart.MultipartFile;

public class FileValidator {

    public static void validate(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Uploaded file is empty or null.");
        }
    }
}

