package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.dto.ResponseWrapperCommentDto;
import ru.skypro.homework.entity.Ads;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.mapper.CommentMapper;
import ru.skypro.homework.repository.AdsRepository;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.service.CommentService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {
    private final Logger logger = LoggerFactory.getLogger(CommentServiceImpl.class);

    private final CommentRepository commentRepository;
    private final AdsRepository adsRepository;

    private final CommentMapper commentMapper;


    @Override
    public CommentDto addComments(CommentDto commentDto, Long adsPk) { //+
        logger.info("method CommentServiceImpl - addComments");
        Ads ads = adsRepository.findAdsByPk(adsPk);
        Comment commentEntity = commentMapper.toComment(commentDto);
        Comment comment = new Comment();
        if (ads == null) {
            logger.info("not found ads with apsPk " + adsPk);
            return null;
        } else {
            comment.setAds(ads);
            comment.setAuthor(commentEntity.getAuthor());
            comment.setPk(commentEntity.getPk());
            comment.setText(commentEntity.getText());
            comment.setCreatedAt((commentEntity.getCreatedAt()));
            commentRepository.save(comment);
            logger.info("create new comment");
        }

        return commentMapper.toDto(comment);

    }

    @Override
    public ResponseWrapperCommentDto getCommentsByAdsPk(Long adsPk) {//+
        logger.info("method CommentServiceImpl - getCommentsByAdsPks");
        List<Comment> allComments = commentRepository.findCommentsByAdsPk(adsPk);
        ResponseWrapperCommentDto wrapperCommentDto = new ResponseWrapperCommentDto();
        if (allComments.isEmpty()) {
            logger.info("Not found ads by this adPk");
            wrapperCommentDto.setCount(0);
            wrapperCommentDto.setResults(Collections.emptyList());
        } else {
            wrapperCommentDto.setCount(allComments.size());
            wrapperCommentDto.setResults(commentMapper.toListDto(allComments));
            logger.info("return all ads by this adPk");
        }

        return wrapperCommentDto;
    }

    @Override
    public CommentDto getComments(Long adsPk, Integer pk) {//+
        logger.info("method CommentServiceImpl - getComments");
        Optional<Comment> commentOptional = commentRepository.findCommentsByAdsPkAndPk(adsPk, pk);
        return commentOptional
                .map(commentMapper::toDto)
                .orElse(null);

    }

    @Override
    public CommentDto updateComments(Long adsPk, Integer pk, CommentDto commentUpdateDto) {//+
        logger.info("method CommentServiceImpl - updateComments");
        Ads ads = adsRepository.findAdsByPk(adsPk);
        Optional<Comment> commentOptional = commentRepository.findCommentsByAdsPkAndPk(adsPk, pk);// получаем entity из базы
        commentOptional.ifPresent(commentEntity -> {
            commentEntity.setAuthor(commentUpdateDto.getAuthor());
            commentEntity.setCreatedAt(commentUpdateDto.getCreatedAt());
            commentEntity.setText(commentUpdateDto.getText());
            commentEntity.setAds(ads);

            commentRepository.save(commentEntity);
            logger.info("return new AdsComment - updateComments");
        });
        return commentOptional
                .map(commentMapper::toDto)
                .orElse(null);
    }


    @Override
    public boolean deleteComments(Long adsPk, Integer pk) {//+
        logger.info("method CommentServiceImpl - deleteComments");
        Optional<Comment> commentOptional = commentRepository.findCommentsByAdsPkAndPk(adsPk, pk);
        commentOptional.ifPresent((commentRepository::delete));
        logger.info("delete AdsComment");
        logger.info("Not found comments by this adPk/pk");
        return commentOptional.isPresent();
    }

}


