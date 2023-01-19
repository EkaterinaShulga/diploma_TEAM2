package ru.skypro.homework.dto.Auth;

import lombok.Data;

@Data
public class LoginReq {
    private String password;
    private String username;

}
