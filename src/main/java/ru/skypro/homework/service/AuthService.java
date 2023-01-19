package ru.skypro.homework.service;

import ru.skypro.homework.dto.Auth.RegisterReq;
import ru.skypro.homework.dto.Auth.Role;

public interface AuthService {
    boolean login(String userName, String password);
    boolean register(RegisterReq registerReq, Role role);
}
