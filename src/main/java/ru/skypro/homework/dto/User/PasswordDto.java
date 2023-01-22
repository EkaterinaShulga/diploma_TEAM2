package ru.skypro.homework.dto.User;

import lombok.Data;

@Data
public class PasswordDto {
    String currentPassword;
    String newPassword;
}
