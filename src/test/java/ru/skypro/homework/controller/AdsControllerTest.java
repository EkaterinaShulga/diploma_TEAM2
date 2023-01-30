package ru.skypro.homework.controller;


import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.skypro.homework.repository.AdsRepository;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.service.impl.AdsServiceImpl;
import ru.skypro.homework.service.impl.CommentServiceImpl;
import ru.skypro.homework.service.impl.FileService;

@WebMvcTest(controllers = AdsController.class)
public class AdsControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @MockBean
    private CommentRepository commentRepository;

    @MockBean
    private AdsRepository adsRepository;

    @SpyBean
    private FileService fileService;

    @SpyBean
    private CommentServiceImpl commentService;

    @SpyBean
    private AdsServiceImpl adsService;

    @InjectMocks
    private AdsController adsController;


    @Test
    public void addAdsTest() throws Exception {

    }

    @Test
    void getFullAd() {
    }

    @Test
    void removeAds() {
    }

    @Test
    void updateAds() {
    }

    @Test
    void addAds() {
    }

    @Test
    void getAllCommentsByAdsPk() {
    }

    @Test
    void getAdsMe() {
    }

    @Test
    void addComments() {
    }

    @Test
    void deleteComments() {
    }

    @Test
    void getComments() {
    }

    @Test
    void updateComments() {
    }

    @Test
    void imageAdsUpdate() {
    }

    @Test
    void getAll() {
    }
}
