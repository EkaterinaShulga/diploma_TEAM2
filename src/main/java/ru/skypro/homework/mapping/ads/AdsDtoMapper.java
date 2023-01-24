package ru.skypro.homework.mapping.ads;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.skypro.homework.dto.Ads.AdsDto;
import ru.skypro.homework.entity.AdsEntity;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface AdsDtoMapper {
    @Mapping(source = "pk", target = "id")
    @Mapping(target = "description", ignore = true)
    @Mapping(target = "user", ignore = true)
    AdsEntity toModel(AdsDto dto);

    @Mapping(source = "id", target = "pk")
    @Mapping(source = "user.id", target = "author")
    AdsDto toDto(AdsEntity entity);

    List<AdsDto> toAdsDtoList(List<AdsEntity> entityList);

    List<AdsEntity> toAdsEntityList(List<AdsDto> dtoList);
}
