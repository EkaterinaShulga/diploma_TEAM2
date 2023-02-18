package ru.skypro.homework.dto;

import lombok.Data;

/**
 * значение поля {@code author} соответствует значению поля id {@code User}<br>
 * поле {@code pk == id CommentDto}<br>
 */
@Data
public class CommentDto {
    Integer author;
    String createdAt;
    Integer pk;
    String text;
}
