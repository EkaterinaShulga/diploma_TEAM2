package ru.skypro.homework.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.dto.ResponseWrapperCommentDto;
import ru.skypro.homework.entity.Ads;
import ru.skypro.homework.entity.Comment;

import java.util.List;

/**
 * converts {@code Comment(Entity)} in {@code CommentDto(DTO)} and <br>
 * converts {@code CommentDto(DTO)} in {@code Comment(Entity)}  <br>
 *
 * @see Comment
 * @see CommentDto
 */

@Mapper(componentModel = "spring", uses = {AdsDtoMapper.class})
public interface CommentMapper {

    CommentDto toDto(Comment comment);

    List<CommentDto> toListDto(List<Comment> comments);

    @AfterMapping
    default void getAds(@MappingTarget Comment comment, Ads ads) {
        comment.setAds(ads);
    }

    @Mapping(target = "pk", ignore = true)
    Comment toComment(CommentDto commentDto, Ads ads);

    ResponseWrapperCommentDto toResponseWrapperCommentDto(List<CommentDto> results, int count);

}
