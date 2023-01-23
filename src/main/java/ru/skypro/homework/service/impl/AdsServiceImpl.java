package ru.skypro.homework.service.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.ads.AdsDto;
import ru.skypro.homework.repositories.AdsRepository;
import ru.skypro.homework.service.AdsService;

import java.util.Collection;


@Service
public class AdsServiceImpl implements AdsService {
    private final Logger logger = LoggerFactory.getLogger(AdsServiceImpl.class);

    private  final AdsRepository adsRepository;

    public AdsServiceImpl(AdsRepository adsRepository){
        this.adsRepository=adsRepository;
    }

    @Override
    public AdsDto getAds() {
        logger.info("Was invoked method for get ads");
        return null;
    }

    @Override
    public AdsDto addAds(AdsDto adsDto) {
        logger.info("Was invoked method for add ads");
        return null;
    }

    @Override
    public Collection<AdsDto> getAdsMe() {
        return null;
    }
}
