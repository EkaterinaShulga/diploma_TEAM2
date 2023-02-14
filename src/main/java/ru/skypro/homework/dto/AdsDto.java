package ru.skypro.homework.dto;

import lombok.Data;

import java.util.List;

@Data
public class AdsDto {
    private long author;
    private List<String> image;
    private long pk;
    private long price;
    private String title;
}
