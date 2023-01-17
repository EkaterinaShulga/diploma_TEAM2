package ru.skypro.homework.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.dto.ads.AdsDto;
import ru.skypro.homework.service.impl.AdsServiceImpl;
import ru.skypro.homework.service.impl.CommentServiceImpl;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collection;

import org.springframework.http.HttpStatus;
import ru.skypro.homework.dto.ads.FullAdsDto;

import java.util.Optional;

@RestController
@CrossOrigin(value = "http://localhost:3000")
@RequestMapping("/ads")
public class AdsController {
    private Logger logger = LoggerFactory.getLogger(AdsController.class);

    private final AdsServiceImpl adsService;
    private final CommentServiceImpl commentService;

    public AdsController(AdsServiceImpl adsService, CommentServiceImpl commentService) {
        this.adsService = adsService;
        this.commentService = commentService;
    }

    @GetMapping(value = "/{id}")
    public FullAdsDto getFullAd(@PathVariable("id") long adsId) {
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
    public AdsDto updateAds(@PathVariable("id") long adsId,
                            @RequestBody AdsDto updatedAdsDto) {
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

    @GetMapping
    public ResponseEntity<AdsDto> getAds() {
        logger.info("Processing getAds Controller");
        return ResponseEntity.ok(adsService.getAds());
    }

    @PostMapping
    public ResponseEntity<AdsDto> addAds(@RequestBody AdsDto adsDto) {
        logger.info("Processing addAds Controller");
        AdsDto ads = adsService.addAds(adsDto);
        return ResponseEntity.ok(ads);
    }

    @GetMapping("/{ad_pk}/comments")
    public ResponseEntity<CommentDto> getComments(@PathVariable("ad_pk") Integer adPk) {
        logger.info("Processing getComments Controller");
        CommentDto commentDto = commentService.getComments(adPk);
        return ResponseEntity.ok(commentDto);
    }

    @GetMapping("/me")
    public ResponseEntity<Collection<AdsDto>> getAdsMe() {
        logger.info("Processing getAdsMe Controller");
        Collection<AdsDto> ads = adsService.getAdsMe();
        return ResponseEntity.ok(ads);
    }

    @PostMapping("/{ad_pk}/comment")
    public CommentDto addComments(@RequestBody CommentDto commentDto,
                                  @PathVariable("ad_pk") String adPk) {
        logger.info("Create new AdsComment - addAdsComments");
        return new CommentDto();
    }

    @DeleteMapping("/{ad_pk}/comment/{id}")
    public ResponseEntity<CommentDto> deleteComments(@PathVariable("ad_pk") String adPk,
                                                     @PathVariable("id") Integer id) {

        CommentDto comment = new CommentDto(); //находим комментарий в БД
        if (comment == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } else {
            logger.info("delete AdsComment - deleteAdsComment");
            return ResponseEntity.ok().build();
        }
    }

    @GetMapping("/{ad_pk}/comments/{id}")
    public CommentDto getComments(@PathVariable("ad_pk") String adPk,
                                  @PathVariable("id") Integer id) {
        CommentDto commentDto = new CommentDto(); //из базы
        logger.info("return AdsComment - getComments");
        return commentDto;
    }

    @PatchMapping("/{ad_pk}/comment/{id}")
    public CommentDto updateComments(@PathVariable("ad_pk") String adPk,
                                       @PathVariable("id") Integer pkAdsComment,
                                       @RequestBody CommentDto comment) {
        CommentDto commentDto = new CommentDto();
        logger.info("return new AdsComment - updateComments");
        return comment;
    }

    @PatchMapping(value = "/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String imageAdsUpdate(@PathVariable("id") long adsId, @RequestPart MultipartFile image) throws IOException {
        String filePath = "";
        return String.format("{\"data\":{ \"image\": \"%s\"}}", filePath);
    }

}
