package ru.skypro.homework.service.impl;


import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.controller.ImageController;
import ru.skypro.homework.entity.Ads;
import ru.skypro.homework.entity.Image;
import ru.skypro.homework.repository.ImageRepository;
import ru.skypro.homework.service.ImageService;

import java.io.IOException;

@RequiredArgsConstructor
@Service

public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;
    private final Logger logger = LoggerFactory.getLogger(ImageController.class);


    @Override
    public void getInformationFromFile(MultipartFile file, Image image) {
        logger.info("Processing ImageServiceImpl:getInformationFromFile()");
        byte[] photo;
        if (file.isEmpty()) {
            logger.info("File is empty");
        }

        try {
            photo = file.getBytes();
        } catch (IOException e) {
            logger.warn("Some problem with file" + file.getName());
            throw new RuntimeException("Some problem with file");
        }
        image.setImage(photo);
        image.setMediaType(file.getContentType());
        image.setFileSize(file.getSize());
    }

    @Override
    public Image createImage(MultipartFile file, Ads ads) {
        logger.info("Processing ImageServiceImpl:createImage()");
        Image image = new Image();
        image.setAds(ads);
        getInformationFromFile(file, image);
        return imageRepository.save(image);

    }

    @Override
    public byte[] getImage(long adsPk) {
        logger.info("Processing ImageServiceImpl:getImage()");
        Image image = imageRepository.getImageById(adsPk);
        if (image == null) {
            logger.warn("Not found image");
        }
        return image.getImage();
    }

    @Override
    public byte[] updateImage(Long id, MultipartFile file) {
        logger.info("Processing ImageServiceImpl:updateImage()");
        Image image = imageRepository.getImageById(id);
        getInformationFromFile(file, image);
        Image imageUpdate = imageRepository.save(image);
        return imageUpdate.getImage();
    }


}
