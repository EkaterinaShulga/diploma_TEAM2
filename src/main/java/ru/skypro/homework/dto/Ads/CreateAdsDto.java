package ru.skypro.homework.dto.Ads;

import lombok.Data;

@Data
public class CreateAdsDto {
    private String description;
    private long price;
    private String title;
    private String image;
}
