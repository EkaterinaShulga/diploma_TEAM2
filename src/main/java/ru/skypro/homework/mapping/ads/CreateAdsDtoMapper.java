package ru.skypro.homework.mapping.ads;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.skypro.homework.dto.Ads.CreateAdsDto;
import ru.skypro.homework.entity.Ads;
import ru.skypro.homework.entity.User.User;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface CreateAdsDtoMapper {
    @Mapping(target = "pk", ignore = true)
    @Mapping(target = "image", source = "image")
    Ads toModel(CreateAdsDto dto, User user, String image);

    CreateAdsDto toDto(Ads entity);
}

