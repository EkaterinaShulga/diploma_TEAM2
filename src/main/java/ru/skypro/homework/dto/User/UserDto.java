package ru.skypro.homework.dto.User;

import lombok.Data;

import javax.persistence.Entity;

@Data
public class UserDto {
    String firstName;
    String lastName;
    String phone;
    String email;
    String regDate;
    String city;
    String image;
    String id;
}
