package ru.skypro.homework.mappers.User;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.skypro.homework.dto.Auth.Role;
import ru.skypro.homework.dto.User.UserDto;
import ru.skypro.homework.entity.User.User;

import java.util.Collection;
import java.util.Optional;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toDto(User entity);

    @Mapping(target = "password", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "username", ignore = true)
    User toEntity(UserDto dto);

    Collection<UserDto> toDtoCollection(Collection<User> entity);

}
