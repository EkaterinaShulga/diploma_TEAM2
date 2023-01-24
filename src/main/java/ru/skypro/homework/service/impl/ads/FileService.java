package ru.skypro.homework.service.impl.ads;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.exceptions.FileSizeLimitException;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
public class FileService {
    @Value("${uploaded.dir}")
    private String uploadedDir;

    @Value("${uploaded.max_file_size}")
    private static int SIZE_LIMIT;

    public String saveLimitedUploadedFile(
            String filePrefix,
            MultipartFile uploadedFile
    ) throws IOException {
        if (!isCorrectFileSize(uploadedFile.getSize())) {
            throw new FileSizeLimitException();
        }

        return saveUploadedFile(
                filePrefix,
                uploadedFile
        );
    }

    public String saveUploadedFile(
            String filePrefix,
            MultipartFile uploadedFile
    ) throws IOException {

        Path filePath = getFilePath(
                filePrefix,
                uploadedFile
        );

        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);

        try (
                OutputStream toIS = Files.newOutputStream(
                        filePath,
                        CREATE_NEW
                )
        ) {
            uploadedFile.getInputStream().transferTo(toIS);
            return "/" + filePath;
        }
    }

    private Path getFilePath(
            String filePrefix,
            MultipartFile uploadedFile
    ) {
        String result = filePrefix + "/" +
                System.currentTimeMillis() / 100 +
                getExtension(Objects.requireNonNull(uploadedFile.getContentType()));

        return Path.of(
                uploadedDir,
                result
        );
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
        Path pathFile = Path.of(filePath);
        return Files.deleteIfExists(pathFile);
    }
}
