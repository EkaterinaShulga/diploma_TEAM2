package ru.skypro.homework.dto;

import lombok.Data;

@Data
public class UserDto {
    String firstName;
    String lastName;
    String phone;
    String email;
    String regDate;
    String city;
    String image;
    Long id;
}
