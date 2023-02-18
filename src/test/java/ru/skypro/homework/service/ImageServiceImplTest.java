package ru.skypro.homework.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.entity.Ads;
import ru.skypro.homework.entity.Image;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.exception.FileEmptyException;
import ru.skypro.homework.exception.SomeProblemWithFileException;
import ru.skypro.homework.repository.ImageRepository;
import ru.skypro.homework.service.impl.ImageServiceImpl;
import ru.skypro.homework.service.impl.UserServiceImpl;

import java.io.IOException;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class ImageServiceImplTest {
    @Mock
    private ImageRepository imageRepository;
    @Mock
    private UserServiceImpl userService;

    @InjectMocks
    private ImageServiceImpl imageService;



    private MultipartFile multipartFile;
    private Image image;
    private Authentication authentication;
    private User defaultUser;
    private Ads ads;


    @BeforeEach()
    void init() {
        defaultUser = new User();
        defaultUser.setUsername("username@gmail.com");
        defaultUser.setId(1L);

        ads = new Ads();
        ads.setId(3L);
        ads.setPrice(16000);
        ads.setTitle("ads title");
        ads.setUser(defaultUser);


        authentication = new UsernamePasswordAuthenticationToken(defaultUser, null);
        multipartFile = mock(MultipartFile.class);

        image = new Image();
        image.setId(1L);
        image.setMediaType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        image.setImage(new byte[1]);
        image.setAds(ads);



    }

    @Test
    void createImageTest() {
        when(imageRepository.save(any())).thenReturn(image);
        Image result = imageService.createImage(multipartFile, new Ads());

        assertNotNull(result);

    }

    @Test
    void getImageTest() {
        when(imageRepository.findById(anyLong())).thenReturn(Optional.of(image));
        byte[] result = imageService.getImage(image.getId());

        assertNotNull(result);
    }

    @Test
    void UpdateImageTest() throws IOException {

        when(imageRepository.findById(anyLong())).thenReturn(Optional.of(image));
        when(multipartFile.getBytes()).thenReturn(new byte[3]);
        doNothing().when(userService).checkUserPermission(authentication, image.getAds().getUser().getUsername());
        when(imageRepository.save(any())).thenReturn(image);

        byte[] result = imageService.updateImage(anyLong(), multipartFile, authentication);

        assertNotNull(result);

    }

    @Test
    void getInformationFromEmptyFileTest() {
        when(multipartFile.isEmpty()).thenReturn(true);

        assertThatExceptionOfType(FileEmptyException.class)
                .isThrownBy(() -> imageService.createImage(multipartFile, new Ads()));
    }

    @Test
    void getInformationFromProblemFileTest() throws IOException {
        when(multipartFile.getBytes()).thenThrow(IOException.class);

        assertThatExceptionOfType(SomeProblemWithFileException.class)
                .isThrownBy(() -> imageService.createImage(multipartFile, new Ads()));
    }
}

