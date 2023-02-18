package ru.skypro.homework.dto;

import lombok.Data;

/**
 * CommentDto - dto, schema : <br>
 * author - id user <br>
 * pk - id CommentDto <br>
 * createdAt - time create comment <br>
 * text - comment text
 */
@Data
public class CommentDto {
    Integer author;
    String createdAt;
    Integer pk;
    String text;
}
