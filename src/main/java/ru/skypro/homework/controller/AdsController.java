package ru.skypro.homework.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.service.impl.AdsServiceImpl;
import ru.skypro.homework.service.impl.CommentServiceImpl;

import java.util.Collection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.AdsCommentDto;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(value = "http://localhost:3000")
@RequestMapping("/ads")
public class AdsController {
    private final AdsServiceImpl adsService;
    private final CommentServiceImpl commentService;

    private Logger logger = LoggerFactory.getLogger(AdsController.class);

    public AdsController(AdsServiceImpl adsService, CommentServiceImpl commentService) {
        this.adsService = adsService;
        this.commentService = commentService;
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
    public ResponseEntity<CommentDto> getComments(@PathVariable("ad_pk") Integer ad_pk) {
        logger.info("Processing getComments Controller");
        CommentDto commentDto = commentService.getComments(ad_pk);
        return ResponseEntity.ok(commentDto);
    }

    @GetMapping("/me")
    public ResponseEntity<Collection<AdsDto>> getAdsMe() {
        logger.info("Processing getAdsMe Controller");
        Collection<AdsDto> ads = adsService.getAdsMe();
        return ResponseEntity.ok(ads);
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
