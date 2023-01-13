package ru.skypro.homework.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.skypro.homework.dto.ads.AdsDto;
import ru.skypro.homework.dto.ads.FullAdsDto;

import java.util.Optional;


@RestController
@CrossOrigin(value = "http://localhost:3000")
@RequestMapping("/ads")
public class AdsController {
    private Logger logger = LoggerFactory.getLogger(AdsController.class);

    @GetMapping(value = "/{id}")
    public FullAdsDto getAds(@PathVariable("id") long adsId) {
        logger.info("Precessing getAds Controller");
        FullAdsDto fullAdsDto = new FullAdsDto(); // Service getMap

//        if (null == fullAdsDto) {
//            throw new ResponseStatusException(
//                    HttpStatus.NOT_FOUND
//            );
//        }
        return fullAdsDto;
    }

    @DeleteMapping(value = "/{id}")
    public Optional<HttpStatus> removeAds(@PathVariable("id") long adsId) {
        logger.info("Processing removeAds Controller");
        boolean isRemoveAds = false; // метод Service remove(adsId)

        if (!isRemoveAds) {
            return Optional.empty();
        }

        return Optional.of(HttpStatus.NO_CONTENT);
    }

    @PatchMapping(value = "/{id}")
    public AdsDto updateAds(@PathVariable("id") long adsId, @RequestBody AdsDto updatedAdsDto) {
        logger.info("Processing updateAds Controller");
        AdsDto adsDto = new AdsDto();// Находим объявление Service findById
//        if (null == adsDto) {
//            throw new ResponseStatusException(
//                    HttpStatus.NOT_FOUND
//            );
//        }
        //Service обновления объявления в БД, возвращаем обновленный объект
        return updatedAdsDto;
    }
}
