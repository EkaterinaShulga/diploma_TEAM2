package ru.skypro.homework.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "user", schema = "public")
@Data
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String avatar;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String phone;
    private String role = "USER";
}