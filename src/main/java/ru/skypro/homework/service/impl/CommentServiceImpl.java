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

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {
    private final Logger logger = LoggerFactory.getLogger(CommentServiceImpl.class);

    private final CommentRepository commentRepository;
    private final AdsRepository adsRepository;
    private final CommentMapper commentMapper;


    /**
     * {@link AdsRepository #findAdsByPk(Long)} возвращает {@code Ads} из БД .<br>
     * {@link CommentMapper #toComment(Object)}, преобразует {@code CommentDto(DTO)} в {@code Comment(Entity)}. <br>
     * {@link CommentRepository#save(Object)}сохраняет в БД {@code Comment}. <br>
     * {@link CommentMapper #toDto(Object)} преобразует  {@code Comment} в  {@code CommentDto} <br>
     *
     * @see CommentDto
     * @see Comment
     * @see CommentMapper
     */
    @Override
    public CommentDto addComment(CommentDto commentDto, Long adsPk) {
        logger.info("method CommentServiceImpl - addComments");
        Ads ads = adsRepository.findAdsById(adsPk);
        Comment comment = commentMapper.toComment(commentDto, ads);
        commentRepository.save(comment);
        logger.info("create new comment");
        return commentMapper.toDto(comment);
    }


    /**
     * {@link CommentRepository #getCommentsByAdsPk(Long)} возвращает все {@code Comments} из БД <br>
     * в {@code List<Comment>}<br>
     * {@link CommentMapper #toListDto(Object)} преобразует {@code List<Comment>} в {@code List<CommentDto>}<br>
     *
     * @see ResponseWrapperCommentDto
     * @see CommentMapper
     */
    @Override
    public ResponseWrapperCommentDto getCommentsByAdsPk(Long adsPk) {
        logger.info("method CommentServiceImpl - getCommentsByAdsPk");
        List<Comment> allComments = commentRepository.getCommentsByAdsId(adsPk);
        logger.info("return all ads by this adPk");
        return commentMapper.toResponseWrapperCommentDto(commentMapper.toListDto(allComments), allComments.size());

    }

    /**
     * {@link CommentRepository #findCommentByAdsPkAndPk(Long, Integer)} возвращает {@code Comment} из БД <br>
     * {@link CommentMapper #toDto(Object)} преобразует {@code comment(Entity)} в {@code commentDto(DTO)}   <br>
     *
     * @see CommentMapper
     * @see CommentDto
     * @see Comment
     */
    @Override
    public CommentDto getComments(Long adsPk, Integer pk) {
        logger.info("method CommentServiceImpl - getComments");
        Optional<Comment> commentOptional = commentRepository.findCommentByAdsIdAndId(adsPk, pk);
        return commentOptional
                .map(commentMapper::toDto)
                .orElse(null);

    }

    /**
     * {@link CommentRepository #findCommentByAdsPkAndPk(Long, Integer)} возвращает {@code Comment} из БД <br>
     *
     * @see Comment
     */
    @Override
    public Comment getComment(Long adsPk, Integer pk) {
        logger.info("method CommentServiceImpl - getComment");
        return commentRepository.findCommentByAdsIdAndId(adsPk, pk).orElse(null);
    }

    /**
     * {@link CommentRepository #findCommentByAdsPkAndPk(Long, Integer)} возвращает {@code Comment} из БД <br>
     * {@link CommentRepository#save(Object)} сохраняет в БД измененный {@code Comment}. <br>
     * {@link CommentMapper #toDto(Object)} преобразует  {@code Comment} в  {@code CommentDto} <br>
     *
     * @see CommentDto
     * @see Comment
     * @see CommentMapper
     */
    @Override
    public CommentDto updateComments(Long adsPk, Integer pk, CommentDto commentUpdateDto) {
        logger.info("method CommentServiceImpl - updateComments");
        Comment commentUpdate = commentRepository.getCommentByAdsIdAndId(adsPk, pk);
        commentUpdate.setText(commentUpdateDto.getText());
        commentRepository.save(commentUpdate);
        return commentMapper.toDto(commentUpdate);

    }


    /**
     * {@link CommentRepository #getCommentByAdsPkAndPk(Long, Integer)} возвращает из БД {@code Comment} <br>
     * {@link CommentRepository #delete(Object)} удаляет {@code Comment} из БД  <br>,
     *
     * @see CommentDto
     * @see Comment
     */
    @Override
    public void deleteComment(Long adsPk, Integer pk) {
        logger.info("method CommentServiceImpl - deleteComments");
        Comment comment = commentRepository.getCommentByAdsIdAndId(adsPk, pk);
        commentRepository.delete(comment);

    }

}


