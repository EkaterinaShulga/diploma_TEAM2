package ru.skypro.homework.mapping.ads;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.skypro.homework.dto.Ads.FullAdsDto;
import ru.skypro.homework.dto.User.UserDto;
import ru.skypro.homework.entity.AdsEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface FullAdsDtoMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "firstName", source = "authorFirstName")
    @Mapping(target = "lastName", source = "authorLastName")
    UserDto dtoToUserDto(FullAdsDto dto);

    @Mapping(source = "dto.pk", target = "id")
    @Mapping(target = "user.password", ignore = true)
    AdsEntity toModel(FullAdsDto dto, UserDto user);

    @Mapping(source = "id", target = "pk")
    @Mapping(source = "user.firstName", target = "authorFirstName")
    @Mapping(source = "user.lastName", target = "authorLastName")
    @Mapping(source = "user.email", target = "email")
    @Mapping(source = "user.phone", target = "phone")
    FullAdsDto toDto(AdsEntity entity);
}
