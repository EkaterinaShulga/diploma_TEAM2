package ru.skypro.homework.service;

import ru.skypro.homework.dto.ads.AdsDto;
import ru.skypro.homework.dto.ads.CreateAdsDto;
import ru.skypro.homework.dto.ads.FullAdsDto;
import ru.skypro.homework.dto.ads.ResponseWrapperAdsDto;
import ru.skypro.homework.entity.AdsEntity;

import java.util.Collection;

public interface AdsService {


    AdsDto createAds(String userLogin, CreateAdsDto createAdsDto, String image);

    AdsEntity getAdsById(long id);

    ResponseWrapperAdsDto getMyAds(String userLogin);

    ResponseWrapperAdsDto getAllAds();

    AdsDto updateAds(String userLogin, long adsId, CreateAdsDto updatedAdsDto);

    boolean removeAds(
            long adsId,
            String userLogin
    );

    FullAdsDto getAds(
            long adsId
    );
}
