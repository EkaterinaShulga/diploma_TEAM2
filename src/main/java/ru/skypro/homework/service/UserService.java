package ru.skypro.homework.service;

import org.springframework.security.core.Authentication;
import ru.skypro.homework.dto.PasswordDto;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.entity.User;

public interface UserService {
    UserDto editUser(UserDto user, String userLogin);
    UserDto findUser(Long id);
    UserDto getUser(String UserPassword);
    UserDto changePassword(PasswordDto password, String userLogin);

    User getUserByLogin(String userLogin);

     void checkUserPermission(Authentication authentication, String username);

}
