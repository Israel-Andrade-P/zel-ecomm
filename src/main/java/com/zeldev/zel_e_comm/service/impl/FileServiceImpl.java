package com.zeldev.zel_e_comm.service.impl;

import com.zeldev.zel_e_comm.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {
    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {
        String originalFileName = file.getOriginalFilename();
        //Generate a unique file name
        String newFileName = UUID.randomUUID().toString().concat(originalFileName.substring(originalFileName.lastIndexOf(".")));
        String filePath = path + File.separator + newFileName;
        //Check if path exists else create
        File folder = new File(path);
        if (!folder.exists()) folder.mkdir();
        //Upload to server
        Files.copy(file.getInputStream(), Paths.get(filePath));
        return newFileName;
    }
}
