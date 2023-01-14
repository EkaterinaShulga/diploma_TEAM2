package ru.skypro.homework.service;

import ru.skypro.homework.dto.Password;
import ru.skypro.homework.dto.UserDto;

import java.util.Collection;

public interface UserService {
    public UserDto editUser(UserDto user);
    public UserDto findUser(Long id);
    public Collection<UserDto> getAll();
    public UserDto changePassword(Password password);
}
