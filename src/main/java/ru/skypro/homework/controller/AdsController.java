package ru.skypro.homework.controller;


import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.repositories.CommentRepository;
import ru.skypro.homework.service.impl.AdsServiceImpl;
import ru.skypro.homework.service.impl.CommentServiceImpl;
import org.springframework.http.HttpStatus;

import java.util.List;

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
    private final CommentRepository commentRepository;


    @GetMapping("/{ad_pk}/comments") //возвращаем все комментраии по id объявления +
    public ResponseEntity<List<CommentDto>> getAllCommentsByAdsPk(@PathVariable("ad_pk") Long adPk) {
        List<CommentDto> allCommentsDto = commentService.getAllCommentsByAdsPk(adPk);
        if (!allCommentsDto.isEmpty()) {
            logger.info("get all ads by this adPk and pk");
            return ResponseEntity.ok(allCommentsDto);

        }
        return ResponseEntity.notFound().build();

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

}