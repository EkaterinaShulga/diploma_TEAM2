package ru.skypro.homework.service;

import org.springframework.security.core.userdetails.User;
import ru.skypro.homework.dto.PasswordDto;
import ru.skypro.homework.dto.UserDto;

import java.util.Collection;

public interface UserService {
    public UserDto editUser(UserDto user, String userLogin);
    public UserDto findUser(Long id);
    public Collection<UserDto> getAll();
    public UserDto changePassword(PasswordDto password, String userLogin);
}
