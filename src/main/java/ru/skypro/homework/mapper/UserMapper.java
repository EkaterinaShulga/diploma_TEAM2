package ru.skypro.homework.mapper;

import org.mapstruct.*;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.entity.Avatar;
import ru.skypro.homework.entity.User;



@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface UserMapper {


    @Mapping(target = "image", source = "avatar", qualifiedByName = "getReferenceForAvatar")
    UserDto toDto(User entity);

    @Mapping(target = "password", ignore = true)
    @Mapping(target = "role", ignore = true)
    User toEntity(UserDto dto);


    @Named("getReferenceForAvatar")
    default String getReferenceForAvatar(Avatar avatar) {
        if(avatar == null){
            return null;
        }
       return "/users/avatar/" + avatar.getId();

    }
}
