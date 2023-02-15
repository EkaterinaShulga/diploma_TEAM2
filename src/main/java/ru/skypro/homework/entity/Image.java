package ru.skypro.homework.entity;

import lombok.Data;

import javax.persistence.*;

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
