package ru.skypro.homework.dto.Ads;

import lombok.Data;

@Data
public class CommentDto {
    Integer author;
    String createdAt;
    Integer pk;
    String text;
}
