package ru.skypro.homework.service;

import ru.skypro.homework.dto.ads.*;
import ru.skypro.homework.entity.Ads;

public interface AdsService {


    AdsDto createAds(String userLogin, CreateAdsDto createAdsDto, String image);


    Ads getAdsByPk(long id);

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
