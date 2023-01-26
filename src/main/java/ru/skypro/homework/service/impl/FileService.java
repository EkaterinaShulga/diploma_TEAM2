package ru.skypro.homework.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.exceptions.FileSizeLimitException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;


@Service
public class FileService {
    @Value("${uploaded.dir}")
    private String uploadedDir;

    @Value("${uploaded.max_file_size}")
    private static int SIZE_LIMIT;

    public String saveLimitedUploadedFile(String filePrefix, MultipartFile uploadedFile) throws IOException {
        if (!isCorrectFileSize(uploadedFile.getSize())) {
            throw new FileSizeLimitException();
        }

        return saveUploadedFile(filePrefix, uploadedFile);
    }

    public String saveUploadedFile(String filePrefix, MultipartFile uploadedFile) throws IOException {

        Path filePath = getFilePath(filePrefix, uploadedFile);

        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);

        try {
            Files.write(filePath, uploadedFile.getBytes());
            return "/" + filePath;
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    private Path getFilePath(String filePrefix, MultipartFile uploadedFile) {
        String result = filePrefix + "/" +
                System.currentTimeMillis() / 100 +
                getExtension(Objects.requireNonNull(uploadedFile.getContentType()));

        return Paths.get(uploadedDir, result);
    }

    private String getExtension(String contentType) {
        switch (contentType) {
            case MediaType.IMAGE_GIF_VALUE:
                return ".gif";
            case MediaType.IMAGE_JPEG_VALUE:
                return ".jpeg";
            case MediaType.IMAGE_PNG_VALUE:
                return ".png";
        }

        return "";
    }

    public boolean isCorrectFileSize(long size) {
        return size > SIZE_LIMIT;
    }

    public boolean removeFileByPath(String filePath) throws IOException {
        if (null == filePath) {
            return false;
        }
        Path pathFile = Paths.get(filePath);
        return Files.deleteIfExists(pathFile);
    }
}
