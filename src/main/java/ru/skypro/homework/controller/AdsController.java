package ru.skypro.homework.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.skypro.homework.dto.ads.*;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.exceptions.ImageProcessException;
import ru.skypro.homework.repositories.CommentRepository;
import ru.skypro.homework.service.impl.AdsServiceImpl;
import ru.skypro.homework.service.impl.CommentServiceImpl;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import ru.skypro.homework.service.impl.FileService;

import javax.persistence.EntityNotFoundException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@CrossOrigin(value = "http://localhost:3000")
@RequestMapping("/ads")
@Tag(name = "Объявления", description = "Ads controller")
@ApiResponses(
        value = {
                @ApiResponse(responseCode = "401", content = @Content()),
                @ApiResponse(responseCode = "403", content = @Content())
        }
)
public class AdsController {
    private Logger logger = LoggerFactory.getLogger(AdsController.class);
    private static final String IMAGE_PATH = "/ads";

    private final AdsServiceImpl adsService;
    private final CommentServiceImpl commentService;
    private final FileService fileService;
    private final CommentRepository commentRepository;


    @GetMapping(value = "/{id}")
    @Operation(
            summary = "getFullAds",
            responses = {
                    @ApiResponse(responseCode = "200", useReturnTypeSchema = true),
                    @ApiResponse(responseCode = "404", content = @Content())
            }
    )
    public FullAdsDto getFullAd(@PathVariable("id") long adsId) {
        logger.info("Precessing getAds Controller");
        FullAdsDto fullAdsDto = adsService.getAds(adsId);

        if (null == fullAdsDto) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return fullAdsDto;
    }

    @DeleteMapping(value = "/{id}")
    @Operation(
            summary = "removeAds",
            responses = {
                    @ApiResponse(responseCode = "204", content = @Content())
            }
    )
    public Optional<HttpStatus> removeAds(@PathVariable("id") long adsId, Authentication authentication) {
        logger.info("Processing removeAds Controller");
        boolean isRemoveAds = adsService.removeAds(adsId, authentication.getName());

        if (!isRemoveAds) {
            return Optional.of(HttpStatus.NOT_FOUND);
        }

        return Optional.of(HttpStatus.NO_CONTENT);
    }

    @PatchMapping(value = "/{id}")
    @Operation(
            summary = "updateAds",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "ads"),
            responses = {
                    @ApiResponse(responseCode = "200", useReturnTypeSchema = true),
                    @ApiResponse(responseCode = "204", content = @Content()),
                    @ApiResponse(responseCode = "404", content = @Content())
            }
    )
    public AdsDto updateAds(@PathVariable("id") long adsId,
                            @RequestBody CreateAdsDto updatedAdsDto,
                            Authentication authentication) { // удалить
        logger.info("Processing updateAds Controller");
        AdsDto newAdsDto = adsService.updateAds(authentication.getName(), adsId, updatedAdsDto);
        if (newAdsDto == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return newAdsDto;
    }

    @PostMapping(
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(
            summary = "addAds",
            responses = {
                    @ApiResponse(responseCode = "200", useReturnTypeSchema = true),
                    @ApiResponse(responseCode = "201", content = @Content())
            }
    )
    public ResponseEntity<AdsDto> addAds(@RequestPart(value = "properties") CreateAdsDto createAdsDto,
                                         @RequestPart(value = "image") MultipartFile image,
                                         Authentication authentication) throws IOException {
        logger.info("Processing addAds Controller");
        String imagePath = fileService.saveLimitedUploadedFile(IMAGE_PATH, image);

        AdsDto adsDto = adsService.createAds(authentication.getName(), createAdsDto, imagePath);

        if (adsDto == null) {
            throw new ResponseStatusException(HttpStatus.CREATED);
        }

        return ResponseEntity.ok(adsDto);
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
    @Operation(
            summary = "getAdsMe",
            responses = {
                    @ApiResponse(responseCode = "200", useReturnTypeSchema = true),
                    @ApiResponse(responseCode = "404", content = @Content())
            }
    )
    public ResponseEntity<ResponseWrapperAdsDto> getAdsMe(Authentication authentication) {
        logger.info("Processing getAdsMe Controller");
        ResponseWrapperAdsDto myAds = adsService.getMyAds(authentication.getName());
        return ResponseEntity.ok(myAds);
    }

    @PostMapping("/{ad_pk}/comment")
    public CommentDto addComments(@RequestBody CommentDto commentDto,
                                  @PathVariable("ad_pk") String adPk) {
        logger.info("Create new AdsComment - addAdsComments");
        return new CommentDto();
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

    @GetMapping("/{ad_pk}/comments/{id}")
    public CommentDto getComments(@PathVariable("ad_pk") String adPk,
                                  @PathVariable("id") Integer id) {
        CommentDto commentDto = new CommentDto(); //из базы
        logger.info("return AdsComment - getComments");
        return commentDto;
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
    @Operation(
            summary = "updateAds",
            responses = {
                    @ApiResponse(responseCode = "200", useReturnTypeSchema = true),
                    @ApiResponse(responseCode = "204", content = @Content()),
                    @ApiResponse(responseCode = "404", content = @Content())
            }
    )
    public String imageAdsUpdate(@PathVariable("id") long adsId,
                                 @RequestPart MultipartFile image,
                                 Authentication authentication) throws IOException {
        String filePath = "";
        try {
            filePath = fileService.saveLimitedUploadedFile(IMAGE_PATH, image);

            boolean isSaved = adsService.updateAdsImagePath(adsId, authentication.getName(), filePath);

            if (!isSaved) {
                throw new ImageProcessException();
            }

            return String.format("{\"data\":{ \"image\": \"%s\"}}", filePath);
        } catch (EntityNotFoundException | ImageProcessException e) {
            fileService.removeFileByPath(filePath);
            return "";
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }


    @GetMapping()
    public ResponseEntity<ResponseWrapperAdsDto> getAll() {
            return ResponseEntity.ok(adsService.getAllAds());

        }


    }

