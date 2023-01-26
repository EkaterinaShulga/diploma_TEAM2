package ru.skypro.homework.mappers.ads;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.skypro.homework.dto.ads.AdsDto;
import ru.skypro.homework.entity.Ads;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface AdsDtoMapper {
    @Mapping(source = "pk", target = "pk")
    @Mapping(target = "description", ignore = true)
    @Mapping(target = "user", ignore = true)
    Ads toModel(AdsDto dto);

    @Mapping(source = "pk", target = "pk")
    @Mapping(source = "user.id", target = "author")
    AdsDto toDto(Ads entity);

    List<AdsDto> toAdsDtoList(List<Ads> entityList);

    List<Ads> toAdsEntityList(List<AdsDto> dtoList);
}
