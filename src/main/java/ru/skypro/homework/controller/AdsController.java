package ru.skypro.homework.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.entity.Ads;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.service.CommentService;
import ru.skypro.homework.service.ImageService;
import ru.skypro.homework.service.impl.AdsServiceImpl;

import java.io.IOException;


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
    private final Logger logger = LoggerFactory.getLogger(AdsController.class);

    private static final String IMAGE_PATH = "/ads";

    private final AdsServiceImpl adsService;
    private final CommentService commentService;
    private final ImageService imageService;


    @GetMapping(value = "/{id}")
    @Operation(
            summary = "getFullAds",
            responses = {
                    @ApiResponse(responseCode = "200", useReturnTypeSchema = true),
                    @ApiResponse(responseCode = "404", content = @Content())
            }
    )
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<FullAdsDto> getFullAd(@PathVariable("id") long adsId) {
        logger.info("Precessing getAds Controller");
        FullAdsDto fullAdsDto = adsService.getAds(adsId);

        if (null == fullAdsDto) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(fullAdsDto);
    }


    @DeleteMapping(value = "/{id}")
    @Operation(
            summary = "removeAds",
            responses = {
                    @ApiResponse(responseCode = "204", content = @Content()),
                    @ApiResponse(responseCode = "401", content = @Content()),
                    @ApiResponse(responseCode = "403", content = @Content())
            }
    )
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<HttpStatus> removeAds(@PathVariable("id") long adsId, Authentication authentication) {
        logger.info("Processing removeAds Controller");
        Ads adsForDelete = adsService.getAdsByPk(adsId);
        if (adsForDelete == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        adsService.removeAds(adsId, authentication);
        return ResponseEntity.ok(HttpStatus.OK);
    }


    @PatchMapping(value = "/{id}")
    @Operation(
            summary = "updateAds",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "ads"),
            responses = {
                    @ApiResponse(responseCode = "200", useReturnTypeSchema = true),
                    @ApiResponse(responseCode = "401", content = @Content()),
                    @ApiResponse(responseCode = "403", content = @Content()),
                    @ApiResponse(responseCode = "404", content = @Content())
            }
    )
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<AdsDto> updateAds(@PathVariable("id") long adsId,
                                            @RequestBody CreateAdsDto updatedAdsDto,
                                            Authentication authentication) {
        logger.info("Processing updateAds Controller");
        AdsDto newAdsDto = adsService.updateAds(authentication.getName(), adsId, updatedAdsDto, authentication);
        if (newAdsDto == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(newAdsDto);
    }


    @PostMapping(
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(
            summary = "addAds",
            responses = {
                    @ApiResponse(responseCode = "200", useReturnTypeSchema = true),
                    @ApiResponse(responseCode = "401", content = @Content()),
                    @ApiResponse(responseCode = "403", content = @Content()),
                    @ApiResponse(responseCode = "404", content = @Content())
            }
    )
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<AdsDto> addAds(@RequestPart(value = "properties") CreateAdsDto createAdsDto,
                                         @RequestPart(value = "image") MultipartFile image,
                                         Authentication authentication) throws IOException {
        logger.info("Processing addAds Controller");
        AdsDto adsDto = adsService.createAds(authentication.getName(), createAdsDto, image);
        if (adsDto == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(adsDto);
    }

    @GetMapping("/me")
    @Operation(
            summary = "getAdsMe",
            responses = {
                    @ApiResponse(responseCode = "200", useReturnTypeSchema = true),
                    @ApiResponse(responseCode = "401", content = @Content()),
                    @ApiResponse(responseCode = "403", content = @Content()),
                    @ApiResponse(responseCode = "404", content = @Content())
            }
    )
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ResponseWrapperAdsDto> getAdsMe(Authentication authentication) {
        logger.info("Processing getAdsMe Controller");
        ResponseWrapperAdsDto myAds = adsService.getMyAds(authentication.getName());


        return ResponseEntity.ok(myAds);
    }


    @PostMapping("/{ad_pk}/comments")
    @Operation(
            summary = "addComments",
            responses = {
                    @ApiResponse(responseCode = "200", useReturnTypeSchema = true),
                    @ApiResponse(responseCode = "401", content = @Content()),
                    @ApiResponse(responseCode = "403", content = @Content()),
                    @ApiResponse(responseCode = "404", content = @Content())
            }
    )
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<CommentDto> addComments(@RequestBody CommentDto commentDto,
                                                  @PathVariable("ad_pk") Long adPk) {
        logger.info("method AdsController - addComments");
        return ResponseEntity.ok(commentService.addComment(commentDto, adPk));
    }

    @Operation(
            summary = "deleteComments",
            responses = {
                    @ApiResponse(responseCode = "200", useReturnTypeSchema = true),
                    @ApiResponse(responseCode = "401", content = @Content()),
                    @ApiResponse(responseCode = "403", content = @Content()),
                    @ApiResponse(responseCode = "404", content = @Content())
            }
    )
    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{ad_pk}/comments/{id}")
    public ResponseEntity<HttpStatus> deleteComments(@PathVariable("ad_pk") Long adPk,
                                                     @PathVariable("id") Integer commentPk, Authentication authentication) {
        logger.info("method AdsController - deleteComments");
        Comment comment = commentService.getComment(adPk, commentPk);
        if (comment == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        commentService.deleteComment(adPk, commentPk, authentication);

        return ResponseEntity.status(HttpStatus.OK).build();


    }

    @Operation(
            summary = "getAllCommentsByAdsPk",
            responses = {
                    @ApiResponse(responseCode = "200", useReturnTypeSchema = true),
                    @ApiResponse(responseCode = "404", content = @Content())
            }
    )
    @GetMapping("/{ad_pk}/comments")
    public ResponseEntity<ResponseWrapperCommentDto> getAllCommentsByAdsPk(@PathVariable("ad_pk") Long adPk) {
        logger.info("method AdsController - getAllCommentsByAdsPk");
        ResponseWrapperCommentDto allCommentsDto = commentService.getCommentsByAdsPk(adPk);
        return ResponseEntity.ok(allCommentsDto);
    }

    @Operation(
            summary = "getComments",
            responses = {
                    @ApiResponse(responseCode = "200", useReturnTypeSchema = true),
                    @ApiResponse(responseCode = "404", content = @Content())
            }
    )
    @GetMapping("/{ad_pk}/comments/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<CommentDto> getComments(@PathVariable("ad_pk") Long adPk,
                                                  @PathVariable("id") Integer commentPk) {
        logger.info("method AdsController - getComments");
        CommentDto commentDto = commentService.getComments(adPk, commentPk);
        if (commentDto == null) {
            logger.info("Not found comments by this " + adPk + " and " + commentPk);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        logger.info("return comment by this " + adPk + " and " + commentPk);
        return ResponseEntity.ok(commentDto);
    }

    @Operation(
            summary = "updateComments",
            responses = {
                    @ApiResponse(responseCode = "200", useReturnTypeSchema = true),
                    @ApiResponse(responseCode = "401", content = @Content()),
                    @ApiResponse(responseCode = "403", content = @Content()),
                    @ApiResponse(responseCode = "404", content = @Content())
            }
    )
    @PreAuthorize("isAuthenticated()")
    @PatchMapping(value = "/{ad_pk}/comments/{id}")
    public ResponseEntity<CommentDto> updateComments(@PathVariable("ad_pk") Long adPk,
                                                     @PathVariable("id") Integer commentPk,
                                                     @RequestBody CommentDto commentUpdate, Authentication authentication) {
        logger.info("method AdsController - updateComments");
        Comment comment = commentService.getComment(adPk, commentPk);
        if (comment == null) {
            logger.info("Not found comments by this " + adPk + " and " + commentPk);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            CommentDto commentDto = commentService.updateComments(adPk, commentPk, commentUpdate, authentication);
            return ResponseEntity.ok(commentDto);
        }
    }

    @Operation(
            responses = {
                    @ApiResponse(responseCode = "200", useReturnTypeSchema = true)
            }
    )
    @GetMapping()
    public ResponseEntity<ResponseWrapperAdsDto> getAllAds() {
        logger.info("method AdsController - getAllAds");
        ResponseWrapperAdsDto allAds = adsService.getAllAds();
        return ResponseEntity.ok(allAds);
    }


    @Operation(
            summary = "updateAdsImage",
            responses = {
                    @ApiResponse(responseCode = "200", useReturnTypeSchema = true),
                    @ApiResponse(responseCode = "404", content = @Content())
            }
    )
    @PatchMapping(value = "/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<byte[]> updateAdsImage(@PathVariable("id") Long id,
                                              @RequestParam MultipartFile image,
                                              Authentication authentication) {
        logger.info("method AdsController - updateImage");
        byte[] photo = imageService.getImage(id);
        if(photo == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        byte[] imageUpdate = imageService.updateImage(id, image, authentication);
        return ResponseEntity.ok(imageUpdate);
    }

}

