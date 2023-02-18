package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.dto.ResponseWrapperCommentDto;
import ru.skypro.homework.entity.Ads;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.mapper.CommentMapper;
import ru.skypro.homework.repository.AdsRepository;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.service.CommentService;
import ru.skypro.homework.service.UserService;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {
    private final Logger logger = LoggerFactory.getLogger(CommentServiceImpl.class);

    private final CommentRepository commentRepository;
    private final AdsRepository adsRepository;
    private final CommentMapper commentMapper;
    private final UserService userService;


    /**
     * creating new comment for ad <br>
     * saves the  {@code Comment} to the database  <br>
     *
     * @param commentDto - created comment
     * @param adPk       - id ad
     * @return commentDto
     */
    @Override
    public CommentDto addComment(CommentDto commentDto, Long adPk) {
        logger.info("method CommentServiceImpl - addComments");
        Ads ads = adsRepository.findAdsById(adPk);
        Comment comment = commentMapper.toComment(commentDto, ads);
        commentRepository.save(comment);
        logger.info("create new comment");
        return commentMapper.toDto(comment);
    }


    /**
     * gets comments by id ad <br>
     * gets a comments from the database and converts it to ResponseWrapperCommentDto <br>
     *
     * @param adPk - id ad
     * @return ResponseWrapperCommentDto
     */
    @Override
    public ResponseWrapperCommentDto getCommentsByAdsPk(Long adPk) {
        logger.info("method CommentServiceImpl - getCommentsByAdsPk");
        List<Comment> allComments = commentRepository.getCommentsByAdsId(adPk);
        logger.info("return all ads by this adPk");
        return commentMapper.toResponseWrapperCommentDto(commentMapper.toListDto(allComments), allComments.size());

    }

    /**
     * gets commentDto by ad id and comment id
     *
     * @param adPk - id ad
     * @param pk   - id comment
     * @return commentDto
     */
    @Override
    public CommentDto getComments(Long adPk, Integer pk) {
        logger.info("method CommentServiceImpl - getComments");
        Optional<Comment> commentOptional = commentRepository.findCommentByAdsIdAndId(adPk, pk);
        return commentOptional
                .map(commentMapper::toDto)
                .orElse(null);

    }

    /**
     * gets comment by ad id and comment id
     *
     * @param adPk - id ad
     * @param pk   - id comment
     * @return comment
     */
    @Override
    public Comment getComment(Long adPk, Integer pk) {
        logger.info("method CommentServiceImpl - getComment");
        return commentRepository.findCommentByAdsIdAndId(adPk, pk).orElse(null);
    }

    /**
     * update comment <br>
     * Receive old comment by id, update and save
     * {@link UserService #checkUserPermission(Boolean,String)} checks the access right for update comment<br>
     *
     * @param adPk             - id ad
     * @param pk               - id comment
     * @param commentUpdateDto - comment with changes
     * @param authentication   -  authentication
     * @return commentDto
     */
    @Override
    public CommentDto updateComments(Long adPk, Integer pk, CommentDto commentUpdateDto, Authentication authentication) {
        logger.info("method CommentServiceImpl - updateComments");
        Comment commentUpdate = commentRepository.getCommentByAdsIdAndId(adPk, pk);
        userService.checkUserPermission(authentication, commentUpdate.getUser().getUsername());
        commentUpdate.setText(commentUpdateDto.getText());
        commentRepository.save(commentUpdate);
        return commentMapper.toDto(commentUpdate);

    }

    /**
     * delete comment<br>
     * gets comment by ad id and comment id, delete comment from database
     * {@link UserService #checkUserPermission(Boolean,String)} checks the access right for delete comment<br>
     *
     * @param adPk           - id ad
     * @param pk             - id comment
     * @param authentication -  authentication
     */
    @Override
    public void deleteComment(Long adPk, Integer pk, Authentication authentication) {
        logger.info("method CommentServiceImpl - deleteComments");
        Comment comment = commentRepository.getCommentByAdsIdAndId(adPk, pk);
        userService.checkUserPermission(authentication, comment.getUser().getUsername());
        commentRepository.delete(comment);
    }

}


