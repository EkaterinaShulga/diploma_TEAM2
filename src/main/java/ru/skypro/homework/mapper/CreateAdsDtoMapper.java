package ru.skypro.homework.mapper;

import org.mapstruct.*;
import ru.skypro.homework.dto.CreateAdsDto;
import ru.skypro.homework.entity.Ads;
import ru.skypro.homework.entity.User;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface CreateAdsDtoMapper {


    @Mapping(target = "id", ignore = true)
    Ads toModel(CreateAdsDto createAdsDto, User user);

    @AfterMapping
    default void getUser(@MappingTarget Ads ads, User user) {
        ads.setUser(user);
    }


}

