package ru.skypro.homework.mappers.user;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.skypro.homework.dto.user.UserDto;
import ru.skypro.homework.entity.user.User;

import java.util.Collection;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toDto(User entity);

    @Mapping(target = "password", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "username", ignore = true)
    User toEntity(UserDto dto);

    Collection<UserDto> toDtoCollection(Collection<User> entity);

}
