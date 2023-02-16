package ru.skypro.homework.service;

import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.dto.CreateAdsDto;
import ru.skypro.homework.dto.FullAdsDto;
import ru.skypro.homework.dto.ResponseWrapperAdsDto;
import ru.skypro.homework.entity.Ads;

import java.io.IOException;
import java.util.List;

public interface AdsService {


    AdsDto createAds(String userLogin, CreateAdsDto createAdsDto, MultipartFile image) throws IOException;//изменила


    Ads getAdsByPk(long id);

    ResponseWrapperAdsDto getMyAds(String userLogin);

    ResponseWrapperAdsDto getAllAds();

    AdsDto updateAds(String userLogin, long adsId, CreateAdsDto updatedAdsDto, Authentication authentication);

    void removeAds(long adsId, Authentication authentication);
    FullAdsDto getAds(long adsId);

    List<Ads> getAdsLike(String title);
}
