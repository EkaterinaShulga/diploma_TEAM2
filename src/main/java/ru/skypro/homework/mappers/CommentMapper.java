package ru.skypro.homework.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.skypro.homework.dto.Ads.CommentDto;
import ru.skypro.homework.entity.Comment;


@Mapper(componentModel = "spring")

public interface CommentMapper {
    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);

    CommentDto toDto(Comment comment);
    @Mapping(target = "pk", ignore = true)
    Comment toComment(CommentDto commentDto);

}
