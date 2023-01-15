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
}
