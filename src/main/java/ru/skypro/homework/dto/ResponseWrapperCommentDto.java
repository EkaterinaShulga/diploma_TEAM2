package ru.skypro.homework.dto;

import lombok.Data;

import java.util.List;

/**
 * ResponseWrapperCommentDto - dto <br>
 * scheme: <br>
 * contains a list of comments and their count in list <br>
 */

@Data
public class ResponseWrapperCommentDto {
    private Integer count;
    private List<CommentDto> results;

}
