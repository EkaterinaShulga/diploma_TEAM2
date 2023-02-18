package ru.skypro.homework.service;

import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;


public interface AvatarService {

    byte[] getAvatar(Long id);

    byte[] updateAvatar(MultipartFile file, Authentication authentication);

}
