package ru.skypro.homework.mapper;

import org.mapstruct.*;
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.entity.Ads;
import ru.skypro.homework.entity.Image;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface AdsDtoMapper {

   @Mapping(source = "user.id", target = "author")
    @Mapping(source = "id", target = "pk")
    @Mapping(source = "image", target = "image", qualifiedByName = "getReferencesForImages")
    AdsDto toDto(Ads ads);

    List<AdsDto> toAdsDtoList(List<Ads> entityList);

    @Named("getReferencesForImages")
    default List<String> getReferencesForImages(List<Image> image) {
        if(image == null || image.isEmpty()){
            return null;
        }
        return image.stream().map(i -> "/image/" + i.getId()).collect(Collectors.toList());

    }
}
