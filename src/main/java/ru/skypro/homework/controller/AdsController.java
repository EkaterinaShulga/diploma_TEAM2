package ru.skypro.homework.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.skypro.homework.dto.ads.AdsDto;
import ru.skypro.homework.dto.ads.FullAdsDto;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.AdsCommentDto;

import java.util.ArrayList;
import java.util.List;


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

    @PostMapping("/{ad_pk}/comment")
    public AdsCommentDto addAdsComments(@RequestBody AdsCommentDto adsCommentDto,
                                        @PathVariable("ad_pk") Integer ad_pk) {
        logger.info("Create new AdsComment - addAdsComments");
        return new AdsCommentDto();
    }

    @DeleteMapping("/{ad_pk}/comment/{id}")
    public ResponseEntity<AdsCommentDto> deleteAdsComment(@PathVariable("ad_pk") Integer ad_pk,
                                                          @PathVariable("id") Integer pkAdsComment) {

        AdsCommentDto adsComment = new AdsCommentDto(); //находим комментарий в БД
        if (adsComment == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } else {
            logger.info("delete AdsComment - deleteAdsComment");
            return ResponseEntity.ok().build();
        }
    }

    @GetMapping("/{ad_pk}/comment/{id}")
    public List<AdsCommentDto> getAdsComments(@PathVariable("ad_pk") Integer ad_pk,
                                             @PathVariable("id") Integer pkAdsComment) {
        List<AdsCommentDto> adsComments = new ArrayList<>();//возвращаем
        AdsCommentDto adsComment = new AdsCommentDto(); //из базы
        adsComments.add(adsComment);//все найденные комментарии
        logger.info("return AdsComment - getAdsComment");
        return adsComments;
    }

    @PatchMapping("/{ad_pk}/comment/{id}")
    public AdsCommentDto updateAdsComment(@PathVariable("ad_pk") Integer ad_pk,
                                          @PathVariable("id") Integer pkAdsComment,
                                          @RequestBody AdsCommentDto updateAdsComment) {
        AdsCommentDto commentDto = new AdsCommentDto();
        logger.info("return new AdsComment - updateAdsComment");
        return updateAdsComment;
    }


}
