package ru.skypro.homework.service;

import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.dto.ResponseWrapperCommentDto;

import java.util.List;
import java.util.Optional;


public interface CommentService {

    CommentDto addComments(CommentDto commentdto, Long adsPk);


    CommentDto getComments(Long adsPk, Integer pk);

    ResponseWrapperCommentDto getCommentsByAdsPk(Long adsPk);

    CommentDto updateComments(Long adsPk, Integer pk, CommentDto commentDto);

    boolean deleteComments(Long adsPk, Integer pk);

}
