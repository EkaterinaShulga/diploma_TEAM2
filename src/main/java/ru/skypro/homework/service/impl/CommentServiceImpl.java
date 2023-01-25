package ru.skypro.homework.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.entity.Ads;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.mappers.CommentMapper;
import ru.skypro.homework.repositories.AdsRepository;
import ru.skypro.homework.repositories.CommentRepository;
import ru.skypro.homework.service.CommentService;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    private final Logger logger = LoggerFactory.getLogger(CommentServiceImpl.class);

    private final CommentRepository commentRepository;
    private final AdsRepository adsRepository;

    private final CommentMapper commentMapper;

    public CommentServiceImpl(CommentRepository commentRepository,
                              AdsRepository adsRepository, CommentMapper commentMapper) {
        this.commentRepository = commentRepository;
        this.adsRepository = adsRepository;
        this.commentMapper = commentMapper;

    }

    @Override
    public CommentDto addComments(CommentDto commentDto, Long adsPk) {
        Comment comment = new Comment();
        Ads ads = adsRepository.findAdsByPk(adsPk);
        if (ads != null) {
            comment = CommentMapper.INSTANCE.toComment(commentDto);

            comment.setAdsPk(ads.getPk());
            commentRepository.save(comment);
            logger.info("create comment");

        } else {
            logger.warn("Not found ads by this adPk");
        }
        return CommentMapper.INSTANCE.toDto(comment);

    }

    @Override
    public List<CommentDto> getAllCommentsByAdsPk(Long adsPk) {
        List<CommentDto> commentsDto = new ArrayList<>();
        List<Comment> allComments = commentRepository.findCommentsByAdsPk(adsPk);
        if (allComments.isEmpty()) {
            logger.info("Not found ads by this adPk");
        }
        for (Comment comment : allComments) {
            commentsDto.add(CommentMapper.INSTANCE.toDto(comment));
        }
        return commentsDto;
    }

    @Override
    public CommentDto getComments(Long adsPk, Integer pk) {
        Comment comment = commentRepository.findCommentsByAdsPkAndPk(adsPk, pk);
        if (comment != null) {
            logger.info("get all ads by this adPk");
        }
        return CommentMapper.INSTANCE.toDto(comment);

    }

    @Override
    public CommentDto updateComments(CommentDto commentUpdateDto, Long adsPk, Integer pk) {
        Comment comment = commentRepository.findCommentsByAdsPkAndPk(adsPk, pk);// получаем entity из базы
        Comment commentUpdate = CommentMapper.INSTANCE.toComment(commentUpdateDto);//новое dto преобразуем в entity
        if (comment != null) {
            comment.setAuthor(commentUpdate.getAuthor());
            comment.setCreatedAt(commentUpdate.getCreatedAt());
            comment.setPk(pk);
            comment.setText(commentUpdate.getText());
            comment.setAdsPk(adsPk);
            commentRepository.save(comment);
            logger.info("return new AdsComment - updateComments");
        }
        return CommentMapper.INSTANCE.toDto(comment);
    }


    @Override
    public void deleteComments(Long adsPk, Integer pk) {
        Comment comment = commentRepository.findCommentsByAdsPkAndPk(adsPk, pk);
        commentRepository.delete(comment);
        logger.info("delete AdsComment");
    }
}
