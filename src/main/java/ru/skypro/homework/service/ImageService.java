package ru.skypro.homework.service;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.entity.Ads;
import ru.skypro.homework.entity.Image;

import java.util.List;

public interface ImageService {

     byte[] getImage(long adsPk);
     Image createImage(MultipartFile file, Ads ads);
     void getInformationFromFile(MultipartFile file, Image image);

     byte[] updateImage(Long id, MultipartFile file);
}
