package com.zeldev.zel_e_comm.unittests.fileservice;

import com.zeldev.zel_e_comm.service.impl.FileServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.mock.web.MockMultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class UploadImageTest {
    private final FileServiceImpl fileService = new FileServiceImpl();

    @TempDir
    Path tempDir;

    @Test
    @DisplayName(
            """
                    GIVEN: a path and a file
                    WHEN: uploadImage is called
                    THEN: file is uploaded and new file name is returned
                    """
    )
    void greenPath() throws IOException {
        MockMultipartFile mockFile = new MockMultipartFile(
                "file",
                "image.png",
                "images/png",
                "test-image-content".getBytes()
        );
        String path = tempDir.toString();

        String fileName = fileService.uploadImage(path, mockFile);

        assertNotNull(fileName);
        assertTrue(fileName.endsWith(".png"));

        File uploadedFile = new File(path + File.separator + fileName);

        assertTrue(uploadedFile.exists());
        assertTrue(uploadedFile.length() > 0);
    }

    @Test
    @DisplayName(
            """
                    GIVEN: a path and a file
                    WHEN: uploadImage is called
                    THEN: file with no extension
                    AND: throws exception
                    """
    )
    void redPath() {
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "image", // no extension
                "image/png",
                "data".getBytes()
        );

        assertThrows(Exception.class, () -> fileService.uploadImage(tempDir.toString(), file));
    }
}
