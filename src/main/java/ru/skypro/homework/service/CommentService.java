package ru.skypro.homework.service;

import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.dto.ResponseWrapperCommentDto;
import ru.skypro.homework.entity.Comment;

import java.util.List;
import java.util.Optional;


public interface CommentService {

    CommentDto addComment(CommentDto commentdto, Long adsPk);
    void deleteComment(Long adsPk, Integer pk);
    CommentDto getComments(Long adsPk, Integer pk);

    Comment getComment(Long adsPk, Integer pk); //возвращает entity для getComment - в контроллере удаляем entity

    ResponseWrapperCommentDto getCommentsByAdsPk(Long adsPk);

    CommentDto updateComments(Long adsPk, Integer pk, CommentDto commentDto);





}
