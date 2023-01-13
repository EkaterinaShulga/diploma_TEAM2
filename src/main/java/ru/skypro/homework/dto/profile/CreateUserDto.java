package ru.skypro.homework.dto.profile;

import lombok.Data;

@Data
public class CreateUserDto {
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private String phone;
}
