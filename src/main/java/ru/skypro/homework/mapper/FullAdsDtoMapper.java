package ru.skypro.homework.mapper;

import org.mapstruct.*;
import ru.skypro.homework.dto.FullAdsDto;
import ru.skypro.homework.entity.Ads;
import ru.skypro.homework.entity.Image;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface FullAdsDtoMapper {

    @Mapping(source = "user.phone", target = "phone")
    @Mapping(source = "user.firstName", target = "authorFirstName")
    @Mapping(source = "user.lastName", target = "authorLastName")
    @Mapping(source = "user.email", target = "email")
    @Mapping(source = "image", target = "image", qualifiedByName = "getReferencesForImages")
    FullAdsDto toDto(Ads ads);

    @Named("getReferencesForImages")
    default List<String> getReferencesForImages(List<Image> image) {
        if (image == null || image.isEmpty()) {
            return null;
        }
        return image.stream().map(i -> "/image/" + i.getId()).collect(Collectors.toList());

    }

}
