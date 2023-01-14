package ru.skypro.homework.controller;

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

    private final Logger logger = LoggerFactory.getLogger(AdsController.class);

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
