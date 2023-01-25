package ru.skypro.homework.service;

import ru.skypro.homework.dto.Ads.CommentDto;

import java.util.List;


public interface CommentService {

    CommentDto addComments(CommentDto commentdto, Long adsPk);

    List<CommentDto> getAllCommentsByAdsPk(Long adsPk);

    CommentDto getComments(Long adsPk, Integer pk);

    CommentDto updateComments(CommentDto commentDto, Long adsPk, Integer pk);

    void deleteComments(Long adsPk, Integer pk);

}
