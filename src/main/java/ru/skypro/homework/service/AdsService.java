package ru.skypro.homework.service;

import ru.skypro.homework.dto.ads.AdsDto;

import java.util.Collection;

public interface AdsService {
    public AdsDto getAds();

    public AdsDto addAds(AdsDto adsDto);

    public Collection<AdsDto> getAdsMe();
}
