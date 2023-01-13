package ru.skypro.homework.dto.ads;

import lombok.Data;

@Data
public class AdsDto {
    private long author;
    private String image;
    private long pk;
    private long price;
    private String title;
}
