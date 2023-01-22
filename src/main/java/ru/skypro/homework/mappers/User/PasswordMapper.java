package ru.skypro.homework.mappers.User;

import org.mapstruct.Mapper;
import ru.skypro.homework.dto.User.PasswordDto;
import ru.skypro.homework.entity.User.Password;

@Mapper(componentModel = "spring")
public interface PasswordMapper {
    PasswordDto toDto(Password entity);

    Password toEntity(PasswordDto dto);
}
