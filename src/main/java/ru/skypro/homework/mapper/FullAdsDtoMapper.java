package ru.skypro.homework.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.skypro.homework.dto.FullAdsDto;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.entity.Ads;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface FullAdsDtoMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "firstName", source = "authorFirstName")
    @Mapping(target = "lastName", source = "authorLastName")
    @Mapping(target = "regDate", ignore = true)
    @Mapping(target = "city", ignore = true)
    UserDto dtoToUserDto(FullAdsDto dto);

    @Mapping(source = "pk", target = "pk")
    @Mapping(target = "user.password", ignore = true)
    @Mapping(target = "author", source = "pk")
    Ads toModel(FullAdsDto dto);

    @Mapping(source = "pk", target = "pk")
    @Mapping(source = "user.firstName", target = "authorFirstName")
    @Mapping(source = "user.lastName", target = "authorLastName")
    @Mapping(source = "user.email", target = "email")
    @Mapping(source = "user.phone", target = "phone")
    FullAdsDto toDto(Ads entity);
}
