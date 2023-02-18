package ru.skypro.homework.dto;

import lombok.Data;

@Data
public class PasswordDto {
    String currentPassword;
    String newPassword;
}
