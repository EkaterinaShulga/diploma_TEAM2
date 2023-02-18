package ru.skypro.homework.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * Image for ads - entity <br>
 * linked by foreign key with {@code Ads(Entity)}
 */

@Data
@Entity
@Table(name = "Images")
public class Image {

    private Long fileSize;
    private String mediaType;
    private byte[] image;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "ads_id")
    Ads ads;
}
