package ru.skypro.homework.dto;

import lombok.Data;

/**
 * dto  - комментарий рекламы/объявления <br>
 * время создания комментария - "createdAt": "2000-01-23T04:56:07.000+00:00" <br>
 * автор объявления - "author": 6 <br>
 * id - "pk": 1 <br>
 * текст комментария -"text": "text" <br>
 */

@Data
public class AdsCommentDto {
    private String createdAt;
    private Integer author;
    private Integer pk;
    private String text;
}


