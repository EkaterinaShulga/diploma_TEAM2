package ru.skypro.homework.dto;

import lombok.Data;

import javax.persistence.Entity;

@Data
public class UserDto {
    String firstName;
    String lastName;
    String password;
    String phone;
    String email;
    String id;
}
