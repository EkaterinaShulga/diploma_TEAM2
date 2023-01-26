package ru.skypro.homework.mappers.user;

import org.mapstruct.Mapper;
import ru.skypro.homework.dto.user.PasswordDto;
import ru.skypro.homework.entity.user.Password;

@Mapper(componentModel = "spring")
public interface PasswordMapper {
    PasswordDto toDto(Password entity);

    Password toEntity(PasswordDto dto);
}
