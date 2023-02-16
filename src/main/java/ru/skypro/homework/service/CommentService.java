package ru.skypro.homework.service;

import org.springframework.security.core.Authentication;
import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.dto.ResponseWrapperCommentDto;
import ru.skypro.homework.entity.Comment;


public interface CommentService {

    CommentDto addComment(CommentDto commentdto, Long adsPk);
    void deleteComment(Long adsPk, Integer pk, Authentication authentication);
    CommentDto getComments(Long adsPk, Integer pk);

    Comment getComment(Long adsPk, Integer pk); //возвращает entity для getComment - в контроллере удаляем entity

    ResponseWrapperCommentDto getCommentsByAdsPk(Long adsPk);

    CommentDto updateComments(Long adsPk, Integer pk, CommentDto commentDto, Authentication authentication);





}
