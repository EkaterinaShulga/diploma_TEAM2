package ru.skypro.homework.dto.User;

import lombok.Data;

@Data
public class Password {
    String currentPassword;
    String newPassword;
}
