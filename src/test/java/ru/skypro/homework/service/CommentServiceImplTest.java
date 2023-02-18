package ru.skypro.homework.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.dto.ResponseWrapperCommentDto;
import ru.skypro.homework.entity.Ads;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.mapper.CommentMapper;
import ru.skypro.homework.mapper.CommentMapperImpl;
import ru.skypro.homework.repository.AdsRepository;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.service.impl.CommentServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CommentServiceImplTest {

    @Mock
    private UserService userService;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private AdsRepository adsRepository;

    @Spy
    private CommentMapper commentMapper = new CommentMapperImpl();

    @InjectMocks
    private CommentServiceImpl commentService;

    private Ads ads;
    private User defaultUser;
    private Authentication authentication;
    private Comment comment;
    private CommentDto commentDto;


    @BeforeEach
    void init() {
        List<Comment> commentList = new ArrayList<>();
        defaultUser = new User();
        defaultUser.setUsername("username@gmail.com");
        defaultUser.setId(1L);

        authentication = new UsernamePasswordAuthenticationToken(defaultUser, null);

        comment = new Comment();
        comment.setText("Text");
        comment.setPk(2);
        comment.setUser(defaultUser);
        comment.setCreatedAt("today");

        commentList.add(comment);

        commentDto = commentMapper.toDto(comment);

        ads = new Ads();
        ads.setPrice(256);
        ads.setTitle("title");
        ads.setId(3L);
        ads.setComments(commentList);

    }

    @Test
    void addCommentTest() {
        when(adsRepository.findAdsById(anyLong())).thenReturn(ads);
        when(commentRepository.save(any())).thenReturn(comment);
        CommentDto result = commentService.addComment(commentDto, ads.getId());

        assertNotNull(result);
    }

    @Test
    void getCommentsByAdsPkTest() {
        List<Comment> commentList = new ArrayList<>();
        commentList.add(comment);
        when(commentRepository.getCommentsByAdsId(anyLong())).thenReturn(commentList);

        ResponseWrapperCommentDto result = commentService.getCommentsByAdsPk(ads.getId());
    }

    @Test
    void getCommentsTest() {
        when(commentRepository.findCommentByAdsIdAndId(anyLong(), anyInt())).thenReturn(Optional.of(comment));
        CommentDto result = commentService.getComments(ads.getId(), comment.getPk());

        assertNotNull(result);
    }

    @Test
    void getCommentTest() {
        when(commentRepository.findCommentByAdsIdAndId(anyLong(), anyInt())).thenReturn(Optional.of(comment));
        Comment result = commentService.getComment(ads.getId(), comment.getPk());

        assertNotNull(result);
    }

    @Test
    void updateComments() {
        when(commentRepository.getCommentByAdsIdAndId(anyLong(), anyInt())).thenReturn(comment);
        doNothing().when(userService).checkUserPermission(authentication, comment.getUser().getUsername());
        when(commentRepository.save(any())).thenReturn(comment);

        CommentDto result = commentService.updateComments(ads.getId(), comment.getPk(), commentDto, authentication);

        assertNotNull(result);
    }

    @Test
    void deleteCommentTest() {
        when(commentRepository.getCommentByAdsIdAndId(anyLong(), anyInt())).thenReturn(comment);
        doNothing().when(userService).checkUserPermission(authentication, defaultUser.getUsername());
        commentService.deleteComment(ads.getId(), comment.getPk(), authentication);

        verify(commentRepository, atMostOnce()).delete(comment);
    }


}
