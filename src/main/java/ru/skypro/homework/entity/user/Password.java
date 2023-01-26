package ru.skypro.homework.entity.user;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Password {

    @Id
    @GeneratedValue
    private Long id;
    private String currentPassword;
    private String newPassword;
}
