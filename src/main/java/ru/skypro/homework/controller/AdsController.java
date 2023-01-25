package ru.skypro.homework.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.dto.ads.AdsDto;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.repositories.CommentRepository;
import ru.skypro.homework.service.impl.AdsServiceImpl;
import ru.skypro.homework.service.impl.CommentServiceImpl;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collection;

import org.springframework.http.HttpStatus;
import ru.skypro.homework.dto.ads.FullAdsDto;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(value = "http://localhost:3000")
@RequestMapping("/ads")
public class AdsController {
    private Logger logger = LoggerFactory.getLogger(AdsController.class);

    private final AdsServiceImpl adsService;
    private final CommentServiceImpl commentService;
    private final CommentRepository commentRepository;

    public AdsController(AdsServiceImpl adsService, CommentServiceImpl commentService,
                         CommentRepository commentRepository) {
        this.adsService = adsService;
        this.commentService = commentService;
        this.commentRepository = commentRepository;

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

    @GetMapping("/{ad_pk}/comments") //возвращаем все комментраии по id объявления +
    public ResponseEntity<List<CommentDto>> getAllCommentsByAdsPk(@PathVariable("ad_pk") Long adPk) {
        List<CommentDto> allCommentsDto = commentService.getAllCommentsByAdsPk(adPk);
        if (!allCommentsDto.isEmpty()) {
            logger.info("get all ads by this adPk and pk");
            return ResponseEntity.ok(allCommentsDto);

        }
        return ResponseEntity.notFound().build();

    }

    @GetMapping("/me")
    public ResponseEntity<Collection<AdsDto>> getAdsMe() {
        logger.info("Processing getAdsMe Controller");
        Collection<AdsDto> ads = adsService.getAdsMe();
        return ResponseEntity.ok(ads);
    }

    @PostMapping("/{ad_pk}/comments")//создаем комментраии по id объявления +
    public ResponseEntity<CommentDto> addComments(@RequestBody CommentDto commentdto,
                                                  @PathVariable("ad_pk") Long adPk) {
        CommentDto comment2 = commentService.addComments(commentdto, adPk);
        if (comment2 == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(comment2);
    }

    @DeleteMapping("/{ad_pk}/comment/{id}")//удаляем комментраии по id объявления и  id комментария+
    public ResponseEntity<Comment> deleteComments(@PathVariable("ad_pk") Long adPk,
                                                  @PathVariable("id") Integer id) {
        CommentDto comment = commentService.getComments(adPk, id);
        if (comment == null) {
            logger.info("Not found ads by this adPk/id");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        } else {
            commentService.deleteComments(adPk, id);
            return ResponseEntity.ok().build();
        }

    }


    @GetMapping("/{ad_pk}/comments/{id}")//возвращаем комментраии по id объявления и  id комментрая+
    public ResponseEntity<CommentDto> getComments(@PathVariable("ad_pk") Long adPk,
                                                  @PathVariable("id") Integer id) {
        CommentDto comment = commentService.getComments(adPk, id);
        if (comment == null) {
            logger.warn("Not found comments by this adPk/pk");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(comment);
    }

    @PatchMapping("/{ad_pk}/comment/{id}")//изменяем комментраии по id объявления+
    public ResponseEntity<CommentDto> updateComments(@PathVariable("ad_pk") Long adPk,
                                                     @PathVariable("id") Integer id,
                                                     @RequestBody CommentDto commentUpdate) {
        Comment comment = commentRepository.findCommentsByAdsPkAndPk(adPk, id);
        if (comment == null) {
            logger.warn("Not found comments by this adPk/pk");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        commentService.updateComments(commentUpdate, adPk, id);
        return ResponseEntity.ok(commentUpdate);
    }

    @PatchMapping(value = "/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String imageAdsUpdate(@PathVariable("id") long adsId, @RequestPart MultipartFile image) throws IOException {
        String filePath = "";
        return String.format("{\"data\":{ \"image\": \"%s\"}}", filePath);
    }

}
