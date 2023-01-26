package ru.skypro.homework.dto.ads;

import lombok.Data;

@Data
public class CommentDto {
    Integer author;
    String createdAt;
    Integer pk;
    String text;
}
