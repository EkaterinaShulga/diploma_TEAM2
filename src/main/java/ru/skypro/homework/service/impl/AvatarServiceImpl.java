package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.entity.Avatar;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.exception.FileEmptyException;
import ru.skypro.homework.exception.SomeProblemWithFileException;
import ru.skypro.homework.repository.AvatarRepository;
import ru.skypro.homework.service.AvatarService;
import ru.skypro.homework.service.UserService;

import java.io.IOException;

@RequiredArgsConstructor
@Service
public class AvatarServiceImpl implements AvatarService {

    private final AvatarRepository avatarRepository;
    private final UserService userService;
    private final UserServiceImpl userServiceImpl;

    private final Logger logger = LoggerFactory.getLogger(AvatarServiceImpl.class);


    /**
     * creates an avatar and byte[]
     *
     * @param file   - file from which to take the information for the avatar
     * @param avatar - avatar
     * @throws SomeProblemWithFileException if the file is incorrect
     * @throws FileEmptyException           if file not found
     */

    public void getInformationFromFile(MultipartFile file, Avatar avatar) {

        logger.info("Processing avatarServiceImpl:getInformationFromFile()");
        byte[] photo;
        if (file.isEmpty()) {
            throw new FileEmptyException();
        }
        try {
            photo = file.getBytes();
        } catch (IOException e) {
            logger.warn("Some problem with file" + file.getName());
            throw new SomeProblemWithFileException();
        }
        avatar.setAvatar(photo);
        avatar.setMediaType(file.getContentType());
        avatar.setFileSize(file.getSize());
    }

    /**
     * get byte[] from avatar find by ads id
     * {@link AvatarServiceImpl#getAvatarForChange(long)} get avatar from database by user id
     *
     * @return byte[]
     */
    @Override
    public byte[] getAvatar(Long id) {
        logger.info("Processing AvatarServiceImpl:getAvatar()");
        return getAvatarForChange(id).getAvatar();

    }


    /**
     * updates the ad avatar<br>
     * find old avatar by user id, checks the access right, update avatar save to database<br>
     * {@link UserService #checkUserPermission(Boolean,String)} checks the access right for update avatar<br>
     *
     * @param file           - file from which to take the information for the new avatar
     * @param authentication -authentication
     * @return byte[]
     */

    @Override
    public byte[] updateAvatar(MultipartFile file, Authentication authentication) {
        logger.info("Processing AvatarServiceImpl:updateAvatar()");

        User user = userServiceImpl.getUserByLogin(authentication.getName());
        Avatar avatarForUpdate = avatarRepository.findAvatarByUserId(user.getId());
        if (avatarForUpdate == null) {
            logger.info("Avatar is here");
            avatarForUpdate = new Avatar();
            avatarForUpdate.setUser(user);
        }
        userService.checkUserPermission(authentication, authentication.getName());
        getInformationFromFile(file, avatarForUpdate);
        Avatar avatar = avatarRepository.save(avatarForUpdate);

        return avatar.getAvatar();
    }

    /**
     * get avatar from database by user id
     *
     * @return avatar
     * @throws FileEmptyException if the avatar was not found
     */
    public Avatar getAvatarForChange(long id) {
        return avatarRepository.findById(id).orElseThrow(FileEmptyException::new);

    }

}
