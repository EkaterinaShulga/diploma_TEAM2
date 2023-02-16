package ru.skypro.homework.service;

import org.springframework.security.core.Authentication;
import ru.skypro.homework.dto.PasswordDto;
import ru.skypro.homework.dto.UserDto;

import java.util.Collection;

public interface UserService {
    UserDto editUser(UserDto user, String userLogin);
    UserDto findUser(Long id);
    Collection<UserDto> getAll();
    UserDto changePassword(PasswordDto password, String userLogin);

    boolean checkUserPermission(Authentication authentication, String username);
}
