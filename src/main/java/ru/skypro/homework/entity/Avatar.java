package ru.skypro.homework.entity;

import lombok.Data;

import javax.persistence.*;


/**
 * Avatar for user - entity <br>
 * linked by foreign key with {@code User(Entity)}
 */
@Data
@Entity
@Table(name = "Avatar")
public class Avatar {
    private Long fileSize;
    private String mediaType;
    private byte[] avatar;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne()
    @JoinColumn(name = "user_id")
    User user;

}
