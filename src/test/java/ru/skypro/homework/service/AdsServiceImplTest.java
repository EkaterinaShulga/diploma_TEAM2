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
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.dto.CreateAdsDto;
import ru.skypro.homework.dto.FullAdsDto;
import ru.skypro.homework.dto.ResponseWrapperAdsDto;
import ru.skypro.homework.entity.Ads;
import ru.skypro.homework.entity.Image;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.mapper.*;
import ru.skypro.homework.repository.AdsRepository;
import ru.skypro.homework.service.impl.AdsServiceImpl;


import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AdsServiceImplTest {

    @Mock
    private AdsRepository adsRepository;

    @Mock
    private ImageService imageService;

    @Mock
    private UserService userService;

    @Spy
    private AdsDtoMapper adsDtoMapper = new AdsDtoMapperImpl();

    @Spy
    private FullAdsDtoMapper fullAdsDtoMapper = new FullAdsDtoMapperImpl();

    @Spy
    private CreateAdsDtoMapper createAdsDtoMapper = new CreateAdsDtoMapperImpl();

    @InjectMocks
    private AdsServiceImpl adsService;

    private List<Ads> adsList;
    private User defaultUser;
    private Authentication authentication;
    private Image image;
    private Ads ads;
    private Ads ads2;
    private CreateAdsDto createAdsDto;

    @BeforeEach
    void init() {
        defaultUser = new User();
        defaultUser.setId(1L);
        defaultUser.setUsername("username@gmail.com");
        authentication = new UsernamePasswordAuthenticationToken(defaultUser, null);

        image = new Image();
        image.setId(2L);

        ads = new Ads();
        ads.setId(3L);
        ads.setPrice(16000);
        ads.setTitle("ads title");
        ads.setUser(defaultUser);

        ads2 = new Ads();
        ads2.setId(3L);
        ads2.setPrice(16000);
        ads2.setTitle("ads2 title");
        ads2.setUser(defaultUser);
        adsList = new ArrayList<>();


//        adsList = List.of(ads, ads2);

        adsList.add(ads);
        adsList.add(ads2);

        createAdsDto = new CreateAdsDto();
        createAdsDto.setDescription("description");
        createAdsDto.setTitle("Title");
        createAdsDto.setPrice(10000);
    }

    @Test
    void createAdsTest() {
        Ads adsMock = createAdsDtoMapper.toModel(createAdsDto);
//        when(userService.getUser(anyString())).thenReturn(defaultUser.getUsername());
        when(imageService.createImage(any(), any())).thenReturn(image);
        when(adsRepository.save(any(Ads.class))).thenReturn(adsMock);

        AdsDto result = adsService.createAds(defaultUser.getUsername(), createAdsDto, null);
        assertNotNull(result);
    }

    @Test
    void getAdsByPk() {
        when(adsRepository.findAdsById(anyLong())).thenReturn(ads);
        Ads result = adsService.getAdsByPk(ads.getId());

        assertNotNull(result);
    }

    @Test
    void getMyAdsTest() {
        when(adsRepository.findByUserEmail(anyString())).thenReturn(adsList);
        ResponseWrapperAdsDto result = adsService.getMyAds("username@gmail.com");

        assertNotNull(result);

    }

    @Test
    void getAllAdsTest() {
        when(adsRepository.findAll()).thenReturn(adsList);
        ResponseWrapperAdsDto result = adsService.getAllAds();

        assertNotNull(result);
    }

    @Test
    void updateAdsTest() {
        Ads adsMock = createAdsDtoMapper.toModel(createAdsDto);
        when(adsRepository.findByIdAndUserId(anyLong(), anyLong())).thenReturn(Optional.of(ads));
        when(adsRepository.save(any())).thenReturn(adsMock);

        AdsDto result = adsService.updateAds(defaultUser.getUsername(), ads.getUser().getId(), createAdsDto, authentication);

        assertNotNull(result);
    }

    @Test
    void removeAdsTest() {
        when(adsRepository.findAdsById(anyLong())).thenReturn(ads);

        adsService.removeAds(ads.getId(), authentication);

        verify(adsRepository, atMostOnce()).delete(ads);
    }


}
